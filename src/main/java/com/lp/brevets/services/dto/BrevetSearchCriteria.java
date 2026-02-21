package com.lp.brevets.services.dto;

import java.time.LocalDate;

public class BrevetSearchCriteria {
	private final String keyword;
	private final Integer inventeurId;
	private final Integer entrepriseId;
	private final Integer domaineId;
	private final LocalDate dateDepotFrom;
	private final LocalDate dateDepotTo;
	private final LocalDate dateValidationFrom;
	private final LocalDate dateValidationTo;
	private final String sortBy;
	private final String sortDirection;

	public BrevetSearchCriteria(String keyword, Integer inventeurId, Integer entrepriseId, Integer domaineId,
			LocalDate dateDepotFrom, LocalDate dateDepotTo, LocalDate dateValidationFrom, LocalDate dateValidationTo,
			String sortBy, String sortDirection) {
		this.keyword = keyword;
		this.inventeurId = inventeurId;
		this.entrepriseId = entrepriseId;
		this.domaineId = domaineId;
		this.dateDepotFrom = dateDepotFrom;
		this.dateDepotTo = dateDepotTo;
		this.dateValidationFrom = dateValidationFrom;
		this.dateValidationTo = dateValidationTo;
		this.sortBy = sortBy;
		this.sortDirection = sortDirection;
	}

	public String getKeyword() {
		return keyword;
	}

	public Integer getInventeurId() {
		return inventeurId;
	}

	public Integer getEntrepriseId() {
		return entrepriseId;
	}

	public Integer getDomaineId() {
		return domaineId;
	}

	public LocalDate getDateDepotFrom() {
		return dateDepotFrom;
	}

	public LocalDate getDateDepotTo() {
		return dateDepotTo;
	}

	public LocalDate getDateValidationFrom() {
		return dateValidationFrom;
	}

	public LocalDate getDateValidationTo() {
		return dateValidationTo;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}
}
