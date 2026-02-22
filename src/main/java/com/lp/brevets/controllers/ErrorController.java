package com.lp.brevets.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.util.Constants;

@WebServlet("/error")
public class ErrorController extends BaseController {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("destination", Constants.HOME);
		request.setAttribute("errorTitle", resolveTitle(request));
		request.setAttribute("errorMessage", resolveMessage(request));
		request.setAttribute("page", "/WEB-INF/views/error.jsp");
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private String resolveTitle(HttpServletRequest request) {
		Object statusCode = request.getAttribute("javax.servlet.error.status_code");
		Integer status = parseStatusCode(statusCode);
		if (status == null) {
			return "Une erreur est survenue";
		}
		if (status == HttpServletResponse.SC_NOT_FOUND) {
			return "Page introuvable";
		}
		if (status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
			return "Erreur interne du serveur";
		}
		return "Erreur " + status;
	}

	private Integer parseStatusCode(Object statusCode) {
		if (statusCode == null) {
			return null;
		}
		if (statusCode instanceof Number) {
			return ((Number) statusCode).intValue();
		}
		try {
			return Integer.parseInt(statusCode.toString());
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	private String resolveMessage(HttpServletRequest request) {
		Object throwable = request.getAttribute("javax.servlet.error.exception");
		if (throwable instanceof Throwable) {
			Throwable ex = (Throwable) throwable;
			if (ex.getMessage() != null && !ex.getMessage().isBlank()) {
				return ex.getMessage();
			}
		}

		Object servletMessage = request.getAttribute("javax.servlet.error.message");
		if (servletMessage != null && !servletMessage.toString().isBlank()) {
			return servletMessage.toString();
		}
		return "Veuillez verifier votre saisie ou reessayer plus tard.";
	}
}
