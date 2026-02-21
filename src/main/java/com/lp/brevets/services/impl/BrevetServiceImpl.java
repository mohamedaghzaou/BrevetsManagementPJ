package com.lp.brevets.services.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierBrevet;
import com.lp.brevets.models.Brevet;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.models.Invention;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.services.BrevetService;
import com.lp.brevets.services.dto.ActiveFilterChip;
import com.lp.brevets.services.dto.BrevetListViewData;
import com.lp.brevets.services.dto.BrevetSearchCriteria;
import com.lp.brevets.services.dto.PageResult;

public class BrevetServiceImpl implements BrevetService {
	private final MetierBrevet brevetMetier;
	private final IMetier<Inventeur> inventeurMetier;
	private final IMetier<Invention> inventionMetier;
	private final IMetier<Entreprise> entrepriseMetier;
	private final IMetier<Domaine> domaineMetier;

	public BrevetServiceImpl(MetierBrevet brevetMetier, IMetier<Inventeur> inventeurMetier,
			IMetier<Invention> inventionMetier, IMetier<Entreprise> entrepriseMetier, IMetier<Domaine> domaineMetier) {
		this.brevetMetier = brevetMetier;
		this.inventeurMetier = inventeurMetier;
		this.inventionMetier = inventionMetier;
		this.entrepriseMetier = entrepriseMetier;
		this.domaineMetier = domaineMetier;
	}

	@Override
	public BrevetListViewData loadList(BrevetSearchCriteria criteria, int requestedPage, int pageSize) {
		String keyword = normalizeKeyword(criteria.getKeyword());
		String sortBy = normalizeSortBy(criteria.getSortBy());
		String sortDirection = normalizeSortDirection(criteria.getSortDirection());

		long totalBrevets = brevetMetier.countSearch(keyword, criteria.getInventeurId(), criteria.getEntrepriseId(),
				criteria.getDomaineId(), criteria.getDateDepotFrom(), criteria.getDateDepotTo(),
				criteria.getDateValidationFrom(), criteria.getDateValidationTo());
		int totalPages = (int) Math.ceil(totalBrevets / (double) pageSize);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(Math.max(requestedPage, 1), totalPages);

		List<Brevet> items = brevetMetier.searchPage(keyword, criteria.getInventeurId(), criteria.getEntrepriseId(),
				criteria.getDomaineId(), criteria.getDateDepotFrom(), criteria.getDateDepotTo(),
				criteria.getDateValidationFrom(), criteria.getDateValidationTo(), sortBy, sortDirection, currentPage, pageSize);
		PageResult<Brevet> page = new PageResult<>(items, currentPage, totalPages, pageSize, totalBrevets,
				totalBrevets > pageSize);

		List<Inventeur> inventeurs = inventeurMetier.getAll();
		List<Entreprise> entreprises = entrepriseMetier.getAll();
		List<Domaine> domaines = domaineMetier.getAll();
		List<ActiveFilterChip> activeFilters = buildActiveFilters(keyword, criteria, sortBy, sortDirection, inventeurs,
				entreprises, domaines);
		return new BrevetListViewData(page, inventeurs, entreprises, domaines, activeFilters);
	}

	@Override
	public Brevet getOne(int id) {
		return brevetMetier.getOne(id);
	}

	@Override
	public List<Inventeur> getInventeurOptions() {
		return inventeurMetier.getAll();
	}

	@Override
	public List<Invention> getInventionOptions() {
		return inventionMetier.getAll();
	}

	@Override
	public void save(Brevet brevet) {
		brevetMetier.save(brevet);
	}

	@Override
	public void update(Brevet brevet) {
		brevetMetier.update(brevet);
	}

	@Override
	public void delete(int id) {
		brevetMetier.delete(new Brevet(id));
	}

	private List<ActiveFilterChip> buildActiveFilters(String keyword, BrevetSearchCriteria criteria, String sortBy,
			String sortDirection, List<Inventeur> inventeurs, List<Entreprise> entreprises, List<Domaine> domaines) {
		List<ActiveFilterChip> filters = new ArrayList<>();
		if (keyword != null) {
			filters.add(new ActiveFilterChip("Recherche: " + keyword,
					buildFilterUrl(null, criteria.getInventeurId(), criteria.getEntrepriseId(), criteria.getDomaineId(),
							criteria.getDateDepotFrom(), criteria.getDateDepotTo(), criteria.getDateValidationFrom(),
							criteria.getDateValidationTo(), sortBy, sortDirection)));
		}
		if (criteria.getInventeurId() != null) {
			filters.add(new ActiveFilterChip("Inventeur: " + findInventeurLabel(inventeurs, criteria.getInventeurId()),
					buildFilterUrl(keyword, null, criteria.getEntrepriseId(), criteria.getDomaineId(), criteria.getDateDepotFrom(),
							criteria.getDateDepotTo(), criteria.getDateValidationFrom(), criteria.getDateValidationTo(), sortBy,
							sortDirection)));
		}
		if (criteria.getEntrepriseId() != null) {
			filters.add(new ActiveFilterChip("Entreprise: " + findEntrepriseLabel(entreprises, criteria.getEntrepriseId()),
					buildFilterUrl(keyword, criteria.getInventeurId(), null, criteria.getDomaineId(), criteria.getDateDepotFrom(),
							criteria.getDateDepotTo(), criteria.getDateValidationFrom(), criteria.getDateValidationTo(), sortBy,
							sortDirection)));
		}
		if (criteria.getDomaineId() != null) {
			filters.add(new ActiveFilterChip("Domaine: " + findDomaineLabel(domaines, criteria.getDomaineId()),
					buildFilterUrl(keyword, criteria.getInventeurId(), criteria.getEntrepriseId(), null,
							criteria.getDateDepotFrom(), criteria.getDateDepotTo(), criteria.getDateValidationFrom(),
							criteria.getDateValidationTo(), sortBy, sortDirection)));
		}

		String depotRange = formatDateRange("Depot", criteria.getDateDepotFrom(), criteria.getDateDepotTo());
		if (depotRange != null) {
			filters.add(new ActiveFilterChip(depotRange,
					buildFilterUrl(keyword, criteria.getInventeurId(), criteria.getEntrepriseId(), criteria.getDomaineId(), null,
							null, criteria.getDateValidationFrom(), criteria.getDateValidationTo(), sortBy, sortDirection)));
		}

		String validationRange = formatDateRange("Validation", criteria.getDateValidationFrom(),
				criteria.getDateValidationTo());
		if (validationRange != null) {
			filters.add(new ActiveFilterChip(validationRange,
					buildFilterUrl(keyword, criteria.getInventeurId(), criteria.getEntrepriseId(), criteria.getDomaineId(),
							criteria.getDateDepotFrom(), criteria.getDateDepotTo(), null, null, sortBy, sortDirection)));
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
		builder.append('&').append(key).append('=').append(URLEncoder.encode(text, StandardCharsets.UTF_8));
	}

	private String findInventeurLabel(List<Inventeur> inventeurs, int inventeurId) {
		for (Inventeur inventeur : inventeurs) {
			if (inventeur.getNum() == inventeurId) {
				return inventeur.getNom() + " " + inventeur.getPrenom();
			}
		}
		return "#" + inventeurId;
	}

	private String findEntrepriseLabel(List<Entreprise> entreprises, int entrepriseId) {
		for (Entreprise entreprise : entreprises) {
			if (entreprise.getNum() == entrepriseId) {
				return entreprise.getNom();
			}
		}
		return "#" + entrepriseId;
	}

	private String findDomaineLabel(List<Domaine> domaines, int domaineId) {
		for (Domaine domaine : domaines) {
			if (domaine.getNum() == domaineId) {
				return domaine.getNom();
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
		return "asc".equalsIgnoreCase(sortDirection) ? "asc" : "desc";
	}
}
