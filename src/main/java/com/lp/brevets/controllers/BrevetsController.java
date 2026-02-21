package com.lp.brevets.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
public class BrevetsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 9;

	private IMetier metier;

	public BrevetsController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("destination", Constants.BREVETS);
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");

		String page = "/WEB-INF/views/brevets/list.jsp";

		switch (command) {
			case "list":
				loadBrevetSearchPageData(request);
				break;
			case "adding":
				page = "/WEB-INF/views/brevets/add.jsp";

				if (request.getParameter("op") != null) {
					add(request, response);
				} else {
					metier = MetierInventeur.INSTANCE;
					request.setAttribute(Constants.INVENTEURS, metier.getAll());
					metier = MetierInvention.INSTANCE;
					request.setAttribute(Constants.INVENTIONS, metier.getAll());
				}

				break;
			case "updating":
				page = "/WEB-INF/views/brevets/update.jsp";

				if (request.getParameter("op") != null) {
					update(request, response);

				} else {

					int id = Integer.parseInt(request.getParameter("id"));
					metier = MetierBrevet.INSTANCE;
					request.setAttribute(Constants.BREVET, metier.getOne(id));

					metier = MetierInventeur.INSTANCE;
					request.setAttribute(Constants.INVENTEURS, metier.getAll());

					metier = MetierInvention.INSTANCE;
					request.setAttribute(Constants.INVENTIONS, metier.getAll());

				}

				break;
			case "delete":

				delete(request, response);
				loadBrevetSearchPageData(request);
				break;
		}
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {

		int id = Integer.parseInt(request.getParameter("id"));
		metier = MetierBrevet.INSTANCE;
		metier.delete(new Brevet(id));

	}

	private void loadBrevetSearchPageData(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);

		String keyword = normalizeKeyword(request.getParameter("keyword"));
		Integer inventeurId = parseOptionalInteger(request.getParameter("inventeurId"));
		Integer entrepriseId = parseOptionalInteger(request.getParameter("entrepriseId"));
		Integer domaineId = parseOptionalInteger(request.getParameter("domaineId"));
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

		metier = MetierInventeur.INSTANCE;
		List<Inventeur> inventeurs = metier.getAll();
		request.setAttribute(Constants.INVENTEURS, inventeurs);

		metier = MetierEntreprise.INSTANCE;
		List<Entreprise> entreprises = metier.getAll();
		request.setAttribute(Constants.ENTREPRISES, entreprises);

		metier = MetierDomaine.INSTANCE;
		List<Domaine> domaines = metier.getAll();
		request.setAttribute(Constants.DOMAINES, domaines);

		List<String> activeFilters = buildActiveFilters(keyword, inventeurId, entrepriseId, domaineId, dateDepotFrom,
				dateDepotTo, dateValidationFrom, dateValidationTo, inventeurs, entreprises, domaines);
		request.setAttribute("activeFilters", activeFilters);
		request.setAttribute("hasActiveFilters", !activeFilters.isEmpty());
	}

	private List<String> buildActiveFilters(String keyword, Integer inventeurId, Integer entrepriseId, Integer domaineId,
			LocalDate dateDepotFrom, LocalDate dateDepotTo, LocalDate dateValidationFrom, LocalDate dateValidationTo,
			List<Inventeur> inventeurs, List<Entreprise> entreprises, List<Domaine> domaines) {
		List<String> filters = new ArrayList<>();

		if (keyword != null) {
			filters.add("Recherche: " + keyword);
		}

		if (inventeurId != null) {
			filters.add("Inventeur: " + findInventeurLabel(inventeurs, inventeurId));
		}

		if (entrepriseId != null) {
			filters.add("Entreprise: " + findEntrepriseLabel(entreprises, entrepriseId));
		}

		if (domaineId != null) {
			filters.add("Domaine: " + findDomaineLabel(domaines, domaineId));
		}

		String depotRange = formatDateRange("Depot", dateDepotFrom, dateDepotTo);
		if (depotRange != null) {
			filters.add(depotRange);
		}

		String validationRange = formatDateRange("Validation", dateValidationFrom, dateValidationTo);
		if (validationRange != null) {
			filters.add(validationRange);
		}

		return filters;
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

	private Integer parseOptionalInteger(String value) {
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

	private LocalDate parseOptionalDate(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException ex) {
			return null;
		}
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

	private int parsePositiveInt(String value, int defaultValue) {
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

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			metier = MetierBrevet.INSTANCE;
			Brevet v = constructBrevet(request, response);
			if (!isDateOrderValid(v)) {
				request.setAttribute("status", "invalidDates");
				loadBrevetFormData(request);
				return;
			}
			metier.save(v);
			request.setAttribute("status", "added");
		}

		catch (Exception eX) {
			request.setAttribute("status", "Notadde");

			System.out.println("conversion error");
		}

		loadBrevetFormData(request);

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		metier = MetierBrevet.INSTANCE;

		Brevet v = constructBrevet(request, response);
		v.setNum(Integer.parseInt(request.getParameter("num")));
		if (!isDateOrderValid(v)) {
			request.setAttribute("status", "invalidDates");
			request.setAttribute(Constants.BREVET, v);
			loadBrevetFormData(request);
			return;
		}

		try {
			metier = MetierBrevet.INSTANCE;
			metier.update(v);
			int id = Integer.parseInt(request.getParameter("num"));
			metier = MetierBrevet.INSTANCE;
			request.setAttribute(Constants.BREVET, metier.getOne(id));
			metier = MetierInventeur.INSTANCE;
			request.setAttribute(Constants.INVENTEURS, metier.getAll());
			metier = MetierInvention.INSTANCE;
			request.setAttribute(Constants.INVENTIONS, metier.getAll());
			request.setAttribute("status", "updated");

		} catch (Exception eX) {
			request.setAttribute("status", "notUpdated");
			int id = Integer.parseInt(request.getParameter("num"));
			metier = MetierBrevet.INSTANCE;
			request.setAttribute(Constants.BREVET, metier.getOne(id));
			loadBrevetFormData(request);

			System.out.println("conversion error");
		}

	}

	private void loadBrevetFormData(HttpServletRequest request) {
		metier = MetierInventeur.INSTANCE;
		request.setAttribute(Constants.INVENTEURS, metier.getAll());
		metier = MetierInvention.INSTANCE;
		request.setAttribute(Constants.INVENTIONS, metier.getAll());
	}

	private boolean isDateOrderValid(Brevet brevet) {
		if (brevet.getDateDepot() == null || brevet.getDateValidation() == null) {
			return false;
		}
		return !brevet.getDateDepot().isAfter(brevet.getDateValidation());
	}

	private Brevet constructBrevet(HttpServletRequest request, HttpServletResponse response) {

		Brevet v = new Brevet();
		v.setInvention(new Invention(Integer.parseInt(request.getParameter("invention"))));

		v.setInventeur(new Inventeur(Integer.parseInt(request.getParameter("inventeur"))));
		v.setDescription(request.getParameter("description"));

		try {
			SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");

			Date dateDepot = dateformt.parse(request.getParameter("datedepot"));
			LocalDate localdateDepot = Instant.ofEpochMilli(dateDepot.getTime()).atZone(ZoneId.systemDefault())
					.toLocalDate();

			v.setDateDepot(localdateDepot);

			Date datevalidation = dateformt.parse(request.getParameter("datevalidation"));
			LocalDate localdatevalidation = Instant.ofEpochMilli(datevalidation.getTime())
					.atZone(ZoneId.systemDefault()).toLocalDate();

			v.setDateValidation(localdatevalidation);
		} catch (ParseException e) {
			System.out.println("conversion error");

		}
		return v;
	}

}

