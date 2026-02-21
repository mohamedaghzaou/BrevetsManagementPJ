package com.lp.brevets.services.dto;

import java.util.List;

import com.lp.brevets.models.Brevet;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.models.Inventeur;

public class BrevetListViewData {
	private final PageResult<Brevet> page;
	private final List<Inventeur> inventeurs;
	private final List<Entreprise> entreprises;
	private final List<Domaine> domaines;
	private final List<ActiveFilterChip> activeFilters;

	public BrevetListViewData(PageResult<Brevet> page, List<Inventeur> inventeurs, List<Entreprise> entreprises,
			List<Domaine> domaines, List<ActiveFilterChip> activeFilters) {
		this.page = page;
		this.inventeurs = inventeurs;
		this.entreprises = entreprises;
		this.domaines = domaines;
		this.activeFilters = activeFilters;
	}

	public PageResult<Brevet> getPage() {
		return page;
	}

	public List<Inventeur> getInventeurs() {
		return inventeurs;
	}

	public List<Entreprise> getEntreprises() {
		return entreprises;
	}

	public List<Domaine> getDomaines() {
		return domaines;
	}

	public List<ActiveFilterChip> getActiveFilters() {
		return activeFilters;
	}
}
