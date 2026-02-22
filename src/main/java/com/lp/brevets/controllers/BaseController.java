package com.lp.brevets.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import com.lp.brevets.config.ApplicationServices;
import com.lp.brevets.services.dto.PageResult;

public abstract class BaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ValidatorFactory VALIDATOR_FACTORY = Validation.byDefaultProvider()
			.configure()
			.messageInterpolator(new ParameterMessageInterpolator())
			.buildValidatorFactory();
	private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

	protected Map<String, String> newFieldErrors() {
		return new LinkedHashMap<>();
	}

	protected void publishFieldErrors(HttpServletRequest request, Map<String, String> fieldErrors) {
		if (fieldErrors != null && !fieldErrors.isEmpty()) {
			request.setAttribute("fieldErrors", fieldErrors);
		}
	}

	protected boolean hasErrors(Map<String, String> fieldErrors) {
		return fieldErrors != null && !fieldErrors.isEmpty();
	}

	protected void addFieldError(Map<String, String> fieldErrors, String field, String message) {
		if (fieldErrors == null || field == null || message == null) {
			return;
		}
		fieldErrors.putIfAbsent(field, message);
	}

	protected String requiredText(HttpServletRequest request, Map<String, String> fieldErrors, String parameterName,
			String fieldName, String requiredMessage) {
		String value = request.getParameter(parameterName);
		if (value == null || value.isBlank()) {
			addFieldError(fieldErrors, fieldName, requiredMessage);
			return null;
		}
		return value.trim();
	}

	protected Integer requiredPositiveInt(HttpServletRequest request, Map<String, String> fieldErrors, String parameterName,
			String fieldName, String message) {
		String value = request.getParameter(parameterName);
		if (value == null || value.isBlank()) {
			addFieldError(fieldErrors, fieldName, message);
			return null;
		}
		try {
			int parsed = Integer.parseInt(value);
			if (parsed <= 0) {
				addFieldError(fieldErrors, fieldName, message);
				return null;
			}
			return parsed;
		} catch (NumberFormatException ex) {
			addFieldError(fieldErrors, fieldName, message);
			return null;
		}
	}

	protected Double requiredDecimal(HttpServletRequest request, Map<String, String> fieldErrors, String parameterName,
			String fieldName, String message) {
		String value = request.getParameter(parameterName);
		if (value == null || value.isBlank()) {
			addFieldError(fieldErrors, fieldName, message);
			return null;
		}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			addFieldError(fieldErrors, fieldName, message);
			return null;
		}
	}

	protected LocalDate requiredDate(HttpServletRequest request, Map<String, String> fieldErrors, String parameterName,
			String fieldName, String message) {
		String value = request.getParameter(parameterName);
		if (value == null || value.isBlank()) {
			addFieldError(fieldErrors, fieldName, message);
			return null;
		}
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException ex) {
			addFieldError(fieldErrors, fieldName, message);
			return null;
		}
	}

	protected Integer parseOptionalPositiveInt(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			int id = Integer.parseInt(value);
			return id > 0 ? id : null;
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	protected LocalDate parseOptionalDate(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException ex) {
			return null;
		}
	}

	protected int parsePositiveInt(String value, int defaultValue) {
		if (value == null || value.isBlank()) {
			return defaultValue;
		}
		try {
			int parsed = Integer.parseInt(value);
			return parsed > 0 ? parsed : defaultValue;
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	protected <T> void validateBean(T bean, Map<String, String> fieldErrors) {
		validateBean(bean, fieldErrors, null);
	}

	protected <T> void validateBean(T bean, Map<String, String> fieldErrors, Map<String, String> fieldAliases) {
		if (bean == null || fieldErrors == null) {
			return;
		}
		for (ConstraintViolation<T> violation : VALIDATOR.validate(bean)) {
			String property = violation.getPropertyPath().toString();
			if (fieldAliases != null) {
				property = fieldAliases.getOrDefault(property, property);
			}
			addFieldError(fieldErrors, property, violation.getMessage());
		}
	}

	protected ApplicationServices appServices() {
		return ApplicationServices.from(getServletContext());
	}

	protected <T> void applyPageResult(HttpServletRequest request, String attributeName, PageResult<T> pageResult) {
		request.setAttribute(attributeName, pageResult.getItems());
		request.setAttribute("currentPage", pageResult.getCurrentPage());
		request.setAttribute("totalPages", pageResult.getTotalPages());
		request.setAttribute("pageSize", pageResult.getPageSize());
		request.setAttribute("totalResults", pageResult.getTotalResults());
		request.setAttribute("hasPagination", pageResult.isHasPagination());
	}

	protected void handleControllerFailure(HttpServletRequest request, HttpServletResponse response, Exception ex,
			String destination) throws ServletException, IOException {
		log("Controller failure", ex);
		request.setAttribute("destination", destination);
		request.setAttribute("errorTitle", "Une erreur inattendue est survenue");
		request.setAttribute("errorMessage", ex.getMessage() == null ? "Veuillez reessayer plus tard." : ex.getMessage());
		request.setAttribute("page", "/WEB-INF/views/error.jsp");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}
}
