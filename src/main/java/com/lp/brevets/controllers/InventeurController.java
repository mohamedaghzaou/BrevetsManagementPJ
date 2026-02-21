package com.lp.brevets.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierEntreprise;
import com.lp.brevets.metier.MetierInventeur;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.util.Constants;

@WebServlet("/inventeurs")
public class InventeurController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;
	private static final Map<String, String> INVENTEUR_FIELD_ALIASES = buildInventeurFieldAliases();

	private IMetier<Inventeur> metier;

	private static Map<String, String> buildInventeurFieldAliases() {
		Map<String, String> aliases = new HashMap<>();
		aliases.put("nom", "nom");
		aliases.put("prenom", "prenom");
		aliases.put("adresse", "adresse");
		aliases.put("email", "email");
		aliases.put("date_nais", "datenaiss");
		aliases.put("entreprise", "entreprise");
		return aliases;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("destination", Constants.INVENTEUR);
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/inventeur/list.jsp";

		try {
			switch (command) {
				case "list":
					loadInventeurListPage(request);
					break;
				case "adding":
					page = "/WEB-INF/views/inventeur/add.jsp";
					if (request.getParameter("op") != null) {
						add(request);
					} else {
						loadFormData(request);
					}
					break;
				case "updating":
					page = "/WEB-INF/views/inventeur/update.jsp";
					if (request.getParameter("op") != null) {
						update(request);
					} else {
						int id = parsePositiveInt(request.getParameter("id"), -1);
						if (id <= 0) {
							throw new IllegalArgumentException("Identifiant d'inventeur invalide.");
						}
						metier = MetierInventeur.INSTANCE;
						request.setAttribute(Constants.INVENTEUR, metier.getOne(id));
						loadFormData(request);
					}
					break;
				case "delete":
					delete(request);
					loadInventeurListPage(request);
					break;
				default:
					loadInventeurListPage(request);
					break;
			}

			request.setAttribute("page", page);
			request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
		} catch (Exception ex) {
			handleControllerFailure(request, response, ex, Constants.INVENTEUR);
		}
	}

	private Inventeur constructInventeur(HttpServletRequest request, Map<String, String> fieldErrors) {
		Inventeur inventeur = new Inventeur();
		inventeur.setNom(requiredText(request, fieldErrors, "nom", "nom", "Le nom est obligatoire."));
		inventeur.setPrenom(requiredText(request, fieldErrors, "prenom", "prenom", "Le prenom est obligatoire."));
		inventeur.setAdresse(requiredText(request, fieldErrors, "adresse", "adresse", "L'adresse est obligatoire."));
		inventeur.setEmail(requiredText(request, fieldErrors, "email", "email", "L'email est obligatoire."));

		LocalDate dateNaiss = requiredDate(request, fieldErrors, "datenaiss", "datenaiss",
				"La date de naissance est invalide.");
		inventeur.setDate_nais(dateNaiss);

		Integer entrepriseId = requiredPositiveInt(request, fieldErrors, "entreprise", "entreprise",
				"Veuillez selectionner une entreprise.");
		if (entrepriseId != null) {
			inventeur.setEntreprise(new Entreprise(entrepriseId));
		}

		validateBean(inventeur, fieldErrors, INVENTEUR_FIELD_ALIASES);
		return inventeur;
	}

	private void update(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Inventeur inventeur = constructInventeur(request, fieldErrors);
		Integer id = requiredPositiveInt(request, fieldErrors, "id", "id", "Identifiant d'inventeur invalide.");
		if (id != null) {
			inventeur.setNum(id);
		}

		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.INVENTEUR, inventeur);
			publishFieldErrors(request, fieldErrors);
			loadFormData(request);
			return;
		}

		try {
			metier = MetierInventeur.INSTANCE;
			metier.update(inventeur);
			request.setAttribute(Constants.INVENTEUR, metier.getOne(id));
			request.setAttribute("status", "updated");
		} catch (Exception ex) {
			request.setAttribute("status", "notUpdated");
			request.setAttribute(Constants.INVENTEUR, inventeur);
			request.setAttribute("globalError", "Impossible de mettre a jour l'inventeur.");
			log("Update inventeur failed", ex);
		}
		loadFormData(request);
	}

	private void delete(HttpServletRequest request) {
		int id = parsePositiveInt(request.getParameter("id"), -1);
		if (id <= 0) {
			request.setAttribute("globalError", "Identifiant d'inventeur invalide.");
			return;
		}
		metier = MetierInventeur.INSTANCE;
		metier.delete(new Inventeur(id));
		request.setAttribute("status", "deleted");
	}

	private void add(HttpServletRequest request) {
		Map<String, String> fieldErrors = newFieldErrors();
		Inventeur inventeur = constructInventeur(request, fieldErrors);

		if (hasErrors(fieldErrors)) {
			request.setAttribute("status", "validationError");
			request.setAttribute(Constants.INVENTEUR, inventeur);
			publishFieldErrors(request, fieldErrors);
			loadFormData(request);
			return;
		}

		try {
			metier = MetierInventeur.INSTANCE;
			metier.save(inventeur);
			request.setAttribute("status", "added");
		} catch (Exception ex) {
			request.setAttribute("status", "Notadde");
			request.setAttribute("globalError", "Impossible d'ajouter l'inventeur.");
			log("Add inventeur failed", ex);
		}
		loadFormData(request);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void loadInventeurListPage(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);
		metier = MetierInventeur.INSTANCE;
		long totalInventeurs = metier.count();
		int totalPages = (int) Math.ceil(totalInventeurs / (double) PAGE_SIZE);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(requestedPage, totalPages);

		List<Inventeur> pageData = metier.getPage(currentPage, PAGE_SIZE);
		request.setAttribute(Constants.INVENTEURS, pageData);
		request.getSession().setAttribute(Constants.INVENTEURS, pageData);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("pageSize", PAGE_SIZE);
		request.setAttribute("totalResults", totalInventeurs);
		request.setAttribute("hasPagination", totalInventeurs > PAGE_SIZE);
	}

	private void loadFormData(HttpServletRequest request) {
		request.setAttribute(Constants.ENTREPRISES, MetierEntreprise.INSTANCE.getAll());
	}
}
