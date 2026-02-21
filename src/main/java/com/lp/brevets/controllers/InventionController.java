package com.lp.brevets.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierDomaine;
import com.lp.brevets.metier.MetierInvention;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.models.Invention;
import com.lp.brevets.util.Constants;

@WebServlet("/inventions")
public class InventionController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;
	private static final Map<String, String> INVENTION_FIELD_ALIASES = buildInventionFieldAliases();

	private IMetier<Invention> metier;

	private static Map<String, String> buildInventionFieldAliases() {
		Map<String, String> aliases = new HashMap<>();
		aliases.put("descriptif", "description");
		aliases.put("resume", "resume");
		aliases.put("domaine", "domaine");
		return aliases;
	}

	public InventionController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/invention/list.jsp";
		request.setAttribute("destination", Constants.INVENTIONS);

		try {
			switch (command) {
				case "list":
					loadInventionListPage(request);
					break;
				case "adding":
					page = "/WEB-INF/views/invention/add.jsp";
					if (request.getParameter("op") != null) {
						add(request);
					} else {
						loadFormData(request);
					}
					break;
				case "updating":
					page = "/WEB-INF/views/invention/update.jsp";
					if (request.getParameter("op") != null) {
						update(request);
					} else {
						int id = parsePositiveInt(request.getParameter("id"), -1);
						if (id <= 0) {
							throw new IllegalArgumentException("Identifiant d'invention invalide.");
						}
						metier = MetierInvention.INSTANCE;
						request.setAttribute(Constants.INVENTION, metier.getOne(id));
						loadFormData(request);
					}
					break;
				case "delete":
					delete(request);
					loadInventionListPage(request);
					break;
				default:
					loadInventionListPage(request);
					break;
			}

			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (Exception ex) {
			handleControllerFailure(request, response, ex, Constants.INVENTIONS);
		}
	}

	private void update(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Invention invention = constructInvention(request, fieldErrors);
		Integer id = requiredPositiveInt(request, fieldErrors, "id", "id", "Identifiant d'invention invalide.");
		if (id != null) {
			invention.setNum(id);
		}

		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.INVENTION, invention);
			publishFieldErrors(request, fieldErrors);
			loadFormData(request);
			return;
		}

		try {
			metier = MetierInvention.INSTANCE;
			metier.update(invention);
			request.setAttribute(Constants.INVENTION, metier.getOne(id));
			request.setAttribute("status", "updated");
		} catch (Exception ex) {
			request.setAttribute("status", "notUpdated");
			request.setAttribute(Constants.INVENTION, invention);
			request.setAttribute("globalError", "Impossible de mettre a jour l'invention.");
			log("Update invention failed", ex);
		}
		loadFormData(request);
	}

	private void add(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Invention invention = constructInvention(request, fieldErrors);

		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.INVENTION, invention);
			publishFieldErrors(request, fieldErrors);
			loadFormData(request);
			return;
		}

		try {
			metier = MetierInvention.INSTANCE;
			metier.save(invention);
			request.setAttribute("status", "added");
		} catch (Exception ex) {
			request.setAttribute("status", "Notadded");
			request.setAttribute("globalError", "Impossible d'ajouter l'invention.");
			log("Add invention failed", ex);
		}
		loadFormData(request);
	}

	private void delete(HttpServletRequest request) {
		int id = parsePositiveInt(request.getParameter("id"), -1);
		if (id <= 0) {
			request.setAttribute("globalError", "Identifiant d'invention invalide.");
			return;
		}
		metier = MetierInvention.INSTANCE;
		metier.delete(new Invention(id));
		request.setAttribute("status", "deleted");
	}

	private Invention constructInvention(HttpServletRequest request, Map<String, String> fieldErrors) {
		Invention invention = new Invention();
		invention.setDescriptif(
				requiredText(request, fieldErrors, "description", "description", "La description est obligatoire."));
		invention.setResume(requiredText(request, fieldErrors, "resume", "resume", "Le resume est obligatoire."));

		Integer domaineId = requiredPositiveInt(request, fieldErrors, "domaine", "domaine",
				"Veuillez selectionner un domaine.");
		if (domaineId != null) {
			invention.setDomaine(new Domaine(domaineId));
		}

		validateBean(invention, fieldErrors, INVENTION_FIELD_ALIASES);
		return invention;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void loadInventionListPage(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);
		metier = MetierInvention.INSTANCE;
		long totalInventions = metier.count();
		int totalPages = (int) Math.ceil(totalInventions / (double) PAGE_SIZE);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(requestedPage, totalPages);

		List<Invention> pageData = metier.getPage(currentPage, PAGE_SIZE);
		request.setAttribute(Constants.INVENTIONS, pageData);
		request.getSession().setAttribute(Constants.INVENTIONS, pageData);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("pageSize", PAGE_SIZE);
		request.setAttribute("totalResults", totalInventions);
		request.setAttribute("hasPagination", totalInventions > PAGE_SIZE);
	}

	private void loadFormData(HttpServletRequest request) {
		request.setAttribute(Constants.DOMAINES, MetierDomaine.INSTANCE.getAll());
	}
}
