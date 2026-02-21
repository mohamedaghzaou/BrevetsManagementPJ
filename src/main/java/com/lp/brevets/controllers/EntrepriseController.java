package com.lp.brevets.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.models.Entreprise;
import com.lp.brevets.services.EntrepriseService;
import com.lp.brevets.util.Constants;

@WebServlet("/enterprises")
public class EntrepriseController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;

	private transient EntrepriseService entrepriseService;

	@Override
	public void init() throws ServletException {
		entrepriseService = appServices().getEntrepriseService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/etrp/list.jsp";
		request.setAttribute("destination", Constants.ENTREPRISE);

		try {
			switch (command) {
				case "list":
					loadEntrepriseListPage(request);
					break;
				case "adding":
					page = "/WEB-INF/views/etrp/add.jsp";
					if (request.getParameter("op") != null) {
						add(request);
					}
					break;
				case "updating":
					page = "/WEB-INF/views/etrp/update.jsp";
					if (request.getParameter("op") != null) {
						update(request);
					} else {
						int id = parsePositiveInt(request.getParameter("id"), -1);
						if (id <= 0) {
							throw new IllegalArgumentException("Identifiant d'entreprise invalide.");
						}
						request.setAttribute(Constants.ENTREPRISE, entrepriseService.getOne(id));
					}
					break;
				case "delete":
					delete(request);
					loadEntrepriseListPage(request);
					break;
				default:
					loadEntrepriseListPage(request);
					break;
			}

			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (Exception ex) {
			handleControllerFailure(request, response, ex, Constants.ENTREPRISE);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void loadEntrepriseListPage(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);
		applyPageResult(request, Constants.ENTREPRISES, entrepriseService.loadPage(requestedPage, PAGE_SIZE));
	}

	private void add(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Entreprise entreprise = constructEntreprise(request, fieldErrors);
		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.ENTREPRISE, entreprise);
			publishFieldErrors(request, fieldErrors);
			return;
		}
		try {
			entrepriseService.save(entreprise);
			request.setAttribute("status", "added");
		} catch (Exception ex) {
			request.setAttribute("status", "Notadded");
			request.setAttribute("globalError", "Impossible d'ajouter l'entreprise.");
			log("Add entreprise failed", ex);
		}
	}

	private void update(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Entreprise entreprise = constructEntreprise(request, fieldErrors);
		Integer id = requiredPositiveInt(request, fieldErrors, "num", "num", "Identifiant d'entreprise invalide.");
		if (id != null) {
			entreprise.setNum(id);
		}
		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.ENTREPRISE, entreprise);
			publishFieldErrors(request, fieldErrors);
			return;
		}
		try {
			entrepriseService.update(entreprise);
			request.setAttribute(Constants.ENTREPRISE, entrepriseService.getOne(id));
			request.setAttribute("status", "updated");
		} catch (Exception ex) {
			request.setAttribute("status", "notupdated");
			request.setAttribute(Constants.ENTREPRISE, entreprise);
			request.setAttribute("globalError", "Impossible de mettre a jour l'entreprise.");
			log("Update entreprise failed", ex);
		}
	}

	private void delete(HttpServletRequest request) {
		int id = parsePositiveInt(request.getParameter("id"), -1);
		if (id <= 0) {
			request.setAttribute("globalError", "Identifiant d'entreprise invalide.");
			return;
		}
		entrepriseService.delete(id);
		request.setAttribute("status", "deleted");
	}

	private Entreprise constructEntreprise(HttpServletRequest request, Map<String, String> fieldErrors) {
		Entreprise entreprise = new Entreprise();
		entreprise.setNom(requiredText(request, fieldErrors, "nom", "nom", "Le nom est obligatoire."));
		entreprise
				.setActivite(requiredText(request, fieldErrors, "activite", "activite", "L'activite est obligatoire."));
		entreprise.setVille(requiredText(request, fieldErrors, "ville", "ville", "La ville est obligatoire."));
		Double ca = requiredDecimal(request, fieldErrors, "ca", "ca", "Le chiffre d'affaires est invalide.");
		if (ca != null) {
			entreprise.setCa(ca);
		}
		validateBean(entreprise, fieldErrors);
		return entreprise;
	}
}
