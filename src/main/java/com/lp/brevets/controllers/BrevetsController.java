package com.lp.brevets.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierBrevet;
import com.lp.brevets.metier.MetierDomaine;
import com.lp.brevets.metier.MetierEntreprise;
import com.lp.brevets.metier.MetierInventeur;
import com.lp.brevets.metier.MetierInvention;
import com.lp.brevets.models.Brevet;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.models.Invention;
import com.lp.brevets.util.Constants;

@WebServlet("/brevets")
public class BrevetsController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 9;
	private static final Map<String, String> BREVET_FIELD_ALIASES = buildBrevetFieldAliases();

	private IMetier<Brevet> metier;

	private static Map<String, String> buildBrevetFieldAliases() {
		Map<String, String> aliases = new HashMap<>();
		aliases.put("description", "description");
		aliases.put("dateDepot", "datedepot");
		aliases.put("dateValidation", "datevalidation");
		aliases.put("invention", "invention");
		aliases.put("inventeur", "inventeur");
		return aliases;
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
						metier = MetierBrevet.INSTANCE;
						request.setAttribute(Constants.BREVET, metier.getOne(id));
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

	private void delete(HttpServletRequest request) {
		int id = parsePositiveInt(request.getParameter("id"), -1);
		if (id <= 0) {
			request.setAttribute("globalError", "Identifiant de brevet invalide.");
			return;
		}
		metier = MetierBrevet.INSTANCE;
		metier.delete(new Brevet(id));
		request.setAttribute("status", "deleted");
	}

	private void loadBrevetSearchPageData(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);

		String keyword = normalizeKeyword(request.getParameter("keyword"));
		Integer inventeurId = parseOptionalPositiveInt(request.getParameter("inventeurId"));
		Integer entrepriseId = parseOptionalPositiveInt(request.getParameter("entrepriseId"));
		Integer domaineId = parseOptionalPositiveInt(request.getParameter("domaineId"));
		LocalDate dateDepotFrom = parseOptionalDate(request.getParameter("dateDepotFrom"));
		LocalDate dateDepotTo = parseOptionalDate(request.getParameter("dateDepotTo"));
		LocalDate dateValidationFrom = parseOptionalDate(request.getParameter("dateValidationFrom"));
		LocalDate dateValidationTo = parseOptionalDate(request.getParameter("dateValidationTo"));
		String sortBy = normalizeSortBy(request.getParameter("sortBy"));
		String sortDirection = normalizeSortDirection(request.getParameter("sortDir"));

		long totalBrevets = MetierBrevet.INSTANCE.countSearch(keyword, inventeurId, entrepriseId, domaineId, dateDepotFrom,
				dateDepotTo, dateValidationFrom, dateValidationTo);
		int totalPages = (int) Math.ceil(totalBrevets / (double) PAGE_SIZE);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(requestedPage, totalPages);

		List<Brevet> brevets = MetierBrevet.INSTANCE.searchPage(keyword, inventeurId, entrepriseId, domaineId,
				dateDepotFrom, dateDepotTo, dateValidationFrom, dateValidationTo, sortBy, sortDirection, currentPage,
				PAGE_SIZE);

		request.setAttribute(Constants.BREVETS, brevets);
		request.getSession().setAttribute(Constants.BREVETS, brevets);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("pageSize", PAGE_SIZE);
		request.setAttribute("totalResults", totalBrevets);
		request.setAttribute("hasPagination", totalBrevets > PAGE_SIZE);

		List<Inventeur> inventeurs = MetierInventeur.INSTANCE.getAll();
		request.setAttribute(Constants.INVENTEURS, inventeurs);
		List<Entreprise> entreprises = MetierEntreprise.INSTANCE.getAll();
		request.setAttribute(Constants.ENTREPRISES, entreprises);
		List<Domaine> domaines = MetierDomaine.INSTANCE.getAll();
		request.setAttribute(Constants.DOMAINES, domaines);

		List<ActiveFilterChip> activeFilters = buildActiveFilters(keyword, inventeurId, entrepriseId, domaineId, dateDepotFrom,
				dateDepotTo, dateValidationFrom, dateValidationTo, sortBy, sortDirection, inventeurs, entreprises, domaines);
		request.setAttribute("activeFilters", activeFilters);
		request.setAttribute("hasActiveFilters", !activeFilters.isEmpty());
	}

	private List<ActiveFilterChip> buildActiveFilters(String keyword, Integer inventeurId, Integer entrepriseId, Integer domaineId,
			LocalDate dateDepotFrom, LocalDate dateDepotTo, LocalDate dateValidationFrom, LocalDate dateValidationTo,
			String sortBy, String sortDirection, List<Inventeur> inventeurs, List<Entreprise> entreprises,
			List<Domaine> domaines) {
		List<ActiveFilterChip> filters = new ArrayList<>();

		if (keyword != null) {
			filters.add(new ActiveFilterChip("Recherche: " + keyword, buildFilterUrl(null, inventeurId, entrepriseId, domaineId,
					dateDepotFrom, dateDepotTo, dateValidationFrom, dateValidationTo, sortBy, sortDirection)));
		}
		if (inventeurId != null) {
			filters.add(new ActiveFilterChip("Inventeur: " + findInventeurLabel(inventeurs, inventeurId),
					buildFilterUrl(keyword, null, entrepriseId, domaineId, dateDepotFrom, dateDepotTo, dateValidationFrom,
							dateValidationTo, sortBy, sortDirection)));
		}
		if (entrepriseId != null) {
			filters.add(new ActiveFilterChip("Entreprise: " + findEntrepriseLabel(entreprises, entrepriseId),
					buildFilterUrl(keyword, inventeurId, null, domaineId, dateDepotFrom, dateDepotTo, dateValidationFrom,
							dateValidationTo, sortBy, sortDirection)));
		}
		if (domaineId != null) {
			filters.add(new ActiveFilterChip("Domaine: " + findDomaineLabel(domaines, domaineId), buildFilterUrl(keyword,
					inventeurId, entrepriseId, null, dateDepotFrom, dateDepotTo, dateValidationFrom, dateValidationTo, sortBy,
					sortDirection)));
		}

		String depotRange = formatDateRange("Depot", dateDepotFrom, dateDepotTo);
		if (depotRange != null) {
			filters.add(new ActiveFilterChip(depotRange, buildFilterUrl(keyword, inventeurId, entrepriseId, domaineId, null, null,
					dateValidationFrom, dateValidationTo, sortBy, sortDirection)));
		}

		String validationRange = formatDateRange("Validation", dateValidationFrom, dateValidationTo);
		if (validationRange != null) {
			filters.add(new ActiveFilterChip(validationRange, buildFilterUrl(keyword, inventeurId, entrepriseId, domaineId,
					dateDepotFrom, dateDepotTo, null, null, sortBy, sortDirection)));
		}

		return filters;
	}

	private String buildFilterUrl(String keyword, Integer inventeurId, Integer entrepriseId, Integer domaineId,
			LocalDate dateDepotFrom, LocalDate dateDepotTo, LocalDate dateValidationFrom, LocalDate dateValidationTo,
			String sortBy, String sortDirection) {
		StringBuilder builder = new StringBuilder("brevets?mode=list");
		appendQueryParam(builder, "keyword", keyword);
		appendQueryParam(builder, "inventeurId", inventeurId);
		appendQueryParam(builder, "entrepriseId", entrepriseId);
		appendQueryParam(builder, "domaineId", domaineId);
		appendQueryParam(builder, "dateDepotFrom", dateDepotFrom);
		appendQueryParam(builder, "dateDepotTo", dateDepotTo);
		appendQueryParam(builder, "dateValidationFrom", dateValidationFrom);
		appendQueryParam(builder, "dateValidationTo", dateValidationTo);
		appendQueryParam(builder, "sortBy", sortBy);
		appendQueryParam(builder, "sortDir", sortDirection);
		return builder.toString();
	}

	private void appendQueryParam(StringBuilder builder, String key, Object value) {
		if (value == null) {
			return;
		}
		String text = String.valueOf(value);
		if (text.isBlank()) {
			return;
		}
		builder.append('&')
				.append(key)
				.append('=')
				.append(URLEncoder.encode(text, StandardCharsets.UTF_8));
	}

	private String findInventeurLabel(List<Inventeur> inventeurs, int inventeurId) {
		if (inventeurs != null) {
			for (Inventeur inventeur : inventeurs) {
				if (inventeur.getNum() == inventeurId) {
					return inventeur.getNom() + " " + inventeur.getPrenom();
				}
			}
		}
		return "#" + inventeurId;
	}

	private String findEntrepriseLabel(List<Entreprise> entreprises, int entrepriseId) {
		if (entreprises != null) {
			for (Entreprise entreprise : entreprises) {
				if (entreprise.getNum() == entrepriseId) {
					return entreprise.getNom();
				}
			}
		}
		return "#" + entrepriseId;
	}

	private String findDomaineLabel(List<Domaine> domaines, int domaineId) {
		if (domaines != null) {
			for (Domaine domaine : domaines) {
				if (domaine.getNum() == domaineId) {
					return domaine.getNom();
				}
			}
		}
		return "#" + domaineId;
	}

	private String formatDateRange(String label, LocalDate from, LocalDate to) {
		if (from == null && to == null) {
			return null;
		}
		if (from != null && to != null) {
			return label + ": du " + from + " au " + to;
		}
		if (from != null) {
			return label + ": a partir du " + from;
		}
		return label + ": jusqu'au " + to;
	}

	private String normalizeKeyword(String keyword) {
		if (keyword == null) {
			return null;
		}
		String normalized = keyword.trim();
		return normalized.isEmpty() ? null : normalized;
	}

	private String normalizeSortBy(String sortBy) {
		if (sortBy == null || sortBy.isBlank()) {
			return "dateDepot";
		}
		switch (sortBy) {
			case "dateValidation":
			case "description":
			case "inventeur":
			case "entreprise":
			case "domaine":
			case "num":
				return sortBy;
			default:
				return "dateDepot";
		}
	}

	private String normalizeSortDirection(String sortDirection) {
		if ("asc".equalsIgnoreCase(sortDirection)) {
			return "asc";
		}
		return "desc";
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
			metier = MetierBrevet.INSTANCE;
			metier.save(brevet);
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
			metier = MetierBrevet.INSTANCE;
			metier.update(brevet);
			request.setAttribute(Constants.BREVET, metier.getOne(id));
			request.setAttribute("status", "updated");
		} catch (Exception ex) {
			request.setAttribute("status", "notUpdated");
			request.setAttribute(Constants.BREVET, brevet);
			request.setAttribute("globalError", "Impossible de mettre a jour le brevet pour le moment.");
			log("Update brevet failed", ex);
		}
		loadBrevetFormData(request);
	}

	private void loadBrevetFormData(HttpServletRequest request) {
		request.setAttribute(Constants.INVENTEURS, MetierInventeur.INSTANCE.getAll());
		request.setAttribute(Constants.INVENTIONS, MetierInvention.INSTANCE.getAll());
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

		if (brevet.getDateDepot() != null && brevet.getDateValidation() != null
				&& brevet.getDateDepot().isAfter(brevet.getDateValidation())) {
			addFieldError(fieldErrors, "datevalidation",
					"La date de validation doit etre superieure ou egale a la date de depot.");
		}
		return brevet;
	}

	public static class ActiveFilterChip {
		private final String label;
		private final String removeUrl;

		public ActiveFilterChip(String label, String removeUrl) {
			this.label = label;
			this.removeUrl = removeUrl;
		}

		public String getLabel() {
			return label;
		}

		public String getRemoveUrl() {
			return removeUrl;
		}
	}
}
