package com.lp.brevets.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.models.Brevet;
import com.lp.brevets.models.Invention;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.services.BrevetService;
import com.lp.brevets.services.dto.BrevetListViewData;
import com.lp.brevets.services.dto.BrevetSearchCriteria;
import com.lp.brevets.util.Constants;

@WebServlet("/brevets")
public class BrevetsController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 9;
	private static final Map<String, String> BREVET_FIELD_ALIASES = buildBrevetFieldAliases();

	private transient BrevetService brevetService;

	private static Map<String, String> buildBrevetFieldAliases() {
		Map<String, String> aliases = new HashMap<>();
		aliases.put("description", "description");
		aliases.put("dateDepot", "datedepot");
		aliases.put("dateValidation", "datevalidation");
		aliases.put("invention", "invention");
		aliases.put("inventeur", "inventeur");
		return aliases;
	}

	@Override
	public void init() throws ServletException {
		brevetService = appServices().getBrevetService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("destination", Constants.BREVETS);
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/brevets/list.jsp";

		try {
			switch (command) {
				case "list":
					loadBrevetSearchPageData(request);
					break;
				case "adding":
					page = "/WEB-INF/views/brevets/add.jsp";
					if (request.getParameter("op") != null) {
						add(request);
					} else {
						loadBrevetFormData(request);
					}
					break;
				case "updating":
					page = "/WEB-INF/views/brevets/update.jsp";
					if (request.getParameter("op") != null) {
						update(request);
					} else {
						int id = parsePositiveInt(request.getParameter("id"), -1);
						if (id <= 0) {
							throw new IllegalArgumentException("Identifiant de brevet invalide.");
						}
						request.setAttribute(Constants.BREVET, brevetService.getOne(id));
						loadBrevetFormData(request);
					}
					break;
				case "delete":
					delete(request);
					loadBrevetSearchPageData(request);
					break;
				default:
					loadBrevetSearchPageData(request);
					break;
			}
			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (Exception ex) {
			handleControllerFailure(request, response, ex, Constants.BREVETS);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void loadBrevetSearchPageData(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);
		BrevetSearchCriteria criteria = new BrevetSearchCriteria(request.getParameter("keyword"),
				parseOptionalPositiveInt(request.getParameter("inventeurId")),
				parseOptionalPositiveInt(request.getParameter("entrepriseId")),
				parseOptionalPositiveInt(request.getParameter("domaineId")), parseOptionalDate(request.getParameter("dateDepotFrom")),
				parseOptionalDate(request.getParameter("dateDepotTo")),
				parseOptionalDate(request.getParameter("dateValidationFrom")),
				parseOptionalDate(request.getParameter("dateValidationTo")), request.getParameter("sortBy"),
				request.getParameter("sortDir"));

		BrevetListViewData viewData = brevetService.loadList(criteria, requestedPage, PAGE_SIZE);
		applyPageResult(request, Constants.BREVETS, viewData.getPage());
		request.setAttribute(Constants.INVENTEURS, viewData.getInventeurs());
		request.setAttribute(Constants.ENTREPRISES, viewData.getEntreprises());
		request.setAttribute(Constants.DOMAINES, viewData.getDomaines());
		request.setAttribute("activeFilters", viewData.getActiveFilters());
		request.setAttribute("hasActiveFilters", !viewData.getActiveFilters().isEmpty());
	}

	private void loadBrevetFormData(HttpServletRequest request) {
		request.setAttribute(Constants.INVENTEURS, brevetService.getInventeurOptions());
		request.setAttribute(Constants.INVENTIONS, brevetService.getInventionOptions());
	}

	private void add(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Brevet brevet = constructBrevet(request, fieldErrors);

		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.BREVET, brevet);
			publishFieldErrors(request, fieldErrors);
			loadBrevetFormData(request);
			return;
		}

		try {
			brevetService.save(brevet);
			request.setAttribute("status", "added");
		} catch (Exception ex) {
			request.setAttribute("status", "Notadde");
			request.setAttribute("globalError", "Impossible d'ajouter le brevet pour le moment.");
			log("Add brevet failed", ex);
		}
		loadBrevetFormData(request);
	}

	private void update(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Brevet brevet = constructBrevet(request, fieldErrors);
		Integer id = requiredPositiveInt(request, fieldErrors, "num", "num", "Identifiant de brevet invalide.");
		if (id != null) {
			brevet.setNum(id);
		}

		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.BREVET, brevet);
			publishFieldErrors(request, fieldErrors);
			loadBrevetFormData(request);
			return;
		}

		try {
			brevetService.update(brevet);
			request.setAttribute(Constants.BREVET, brevetService.getOne(id));
			request.setAttribute("status", "updated");
		} catch (Exception ex) {
			request.setAttribute("status", "notUpdated");
			request.setAttribute(Constants.BREVET, brevet);
			request.setAttribute("globalError", "Impossible de mettre a jour le brevet pour le moment.");
			log("Update brevet failed", ex);
		}
		loadBrevetFormData(request);
	}

	private void delete(HttpServletRequest request) {
		int id = parsePositiveInt(request.getParameter("id"), -1);
		if (id <= 0) {
			request.setAttribute("globalError", "Identifiant de brevet invalide.");
			return;
		}
		brevetService.delete(id);
		request.setAttribute("status", "deleted");
	}

	private Brevet constructBrevet(HttpServletRequest request, Map<String, String> fieldErrors) {
		Brevet brevet = new Brevet();
		brevet.setDescription(requiredText(request, fieldErrors, "description", "description", "La description est obligatoire."));
		brevet.setDateDepot(requiredDate(request, fieldErrors, "datedepot", "datedepot", "La date de depot est invalide."));
		brevet.setDateValidation(
				requiredDate(request, fieldErrors, "datevalidation", "datevalidation", "La date de validation est invalide."));

		Integer inventionId = requiredPositiveInt(request, fieldErrors, "invention", "invention",
				"Veuillez selectionner une invention.");
		if (inventionId != null) {
			brevet.setInvention(new Invention(inventionId));
		}

		Integer inventeurId = requiredPositiveInt(request, fieldErrors, "inventeur", "inventeur",
				"Veuillez selectionner un inventeur.");
		if (inventeurId != null) {
			brevet.setInventeur(new Inventeur(inventeurId));
		}

		validateBean(brevet, fieldErrors, BREVET_FIELD_ALIASES);
		LocalDate dateDepot = brevet.getDateDepot();
		LocalDate dateValidation = brevet.getDateValidation();
		if (dateDepot != null && dateValidation != null && dateDepot.isAfter(dateValidation)) {
			addFieldError(fieldErrors, "datevalidation",
					"La date de validation doit etre superieure ou egale a la date de depot.");
		}
		return brevet;
	}
}
