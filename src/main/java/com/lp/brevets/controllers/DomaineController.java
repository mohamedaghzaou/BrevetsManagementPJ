package com.lp.brevets.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.models.Domaine;
import com.lp.brevets.services.DomaineService;
import com.lp.brevets.util.Constants;

@WebServlet("/domaines")
public class DomaineController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;

	private transient DomaineService domaineService;

	@Override
	public void init() throws ServletException {
		domaineService = appServices().getDomaineService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("destination", Constants.DOMAINE);
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/domaine/list.jsp";

		try {
			switch (command) {
				case "list":
					loadDomaineListPage(request);
					break;
				case "adding":
					page = "/WEB-INF/views/domaine/add.jsp";
					if (request.getParameter("op") != null) {
						add(request);
					}
					break;
				case "updating":
					page = "/WEB-INF/views/domaine/update.jsp";
					if (request.getParameter("op") != null) {
						update(request);
					} else {
						int id = parsePositiveInt(request.getParameter("id"), -1);
						if (id <= 0) {
							throw new IllegalArgumentException("Identifiant de domaine invalide.");
						}
						request.setAttribute(Constants.DOMAINE, domaineService.getOne(id));
					}
					break;
				case "delete":
					delete(request);
					loadDomaineListPage(request);
					break;
				default:
					loadDomaineListPage(request);
					break;
			}

			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (Exception ex) {
			handleControllerFailure(request, response, ex, Constants.DOMAINE);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void loadDomaineListPage(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);
		applyPageResult(request, Constants.DOMAINES, domaineService.loadPage(requestedPage, PAGE_SIZE));
	}

	private void add(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Domaine domaine = constructDomaine(request, fieldErrors);
		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.DOMAINE, domaine);
			publishFieldErrors(request, fieldErrors);
			return;
		}
		try {
			domaineService.save(domaine);
			request.setAttribute("status", "added");
		} catch (Exception ex) {
			request.setAttribute("status", "notAdded");
			request.setAttribute(Constants.DOMAINE, domaine);
			request.setAttribute("globalError", "Impossible d'ajouter le domaine.");
			log("Add domaine failed", ex);
		}
	}

	private void update(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Domaine domaine = constructDomaine(request, fieldErrors);
		Integer id = requiredPositiveInt(request, fieldErrors, "num", "num", "Identifiant de domaine invalide.");
		if (id != null) {
			domaine.setNum(id);
		}
		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.DOMAINE, domaine);
			publishFieldErrors(request, fieldErrors);
			return;
		}
		try {
			domaineService.update(domaine);
			request.setAttribute(Constants.DOMAINE, domaineService.getOne(id));
			request.setAttribute("status", "updated");
		} catch (Exception ex) {
			request.setAttribute("status", "notUpdated");
			request.setAttribute(Constants.DOMAINE, domaine);
			request.setAttribute("globalError", "Impossible de mettre a jour le domaine.");
			log("Update domaine failed", ex);
		}
	}

	private void delete(HttpServletRequest request) {
		int id = parsePositiveInt(request.getParameter("id"), -1);
		if (id <= 0) {
			request.setAttribute("globalError", "Identifiant de domaine invalide.");
			return;
		}
		if (domaineService.getOne(id) == null) {
			request.setAttribute("globalError", "Domaine introuvable.");
			return;
		}
		domaineService.delete(id);
		request.setAttribute("status", "deleted");
	}

	private Domaine constructDomaine(HttpServletRequest request, Map<String, String> fieldErrors) {
		Domaine domaine = new Domaine();
		domaine.setNom(requiredText(request, fieldErrors, "nom", "nom", "Le nom du domaine est obligatoire."));
		validateBean(domaine, fieldErrors);
		return domaine;
	}
}
