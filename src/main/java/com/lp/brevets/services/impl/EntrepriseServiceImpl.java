package com.lp.brevets.services.impl;

import java.util.List;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.services.EntrepriseService;
import com.lp.brevets.services.dto.PageResult;

public class EntrepriseServiceImpl implements EntrepriseService {
	private final IMetier<Entreprise> entrepriseMetier;

	public EntrepriseServiceImpl(IMetier<Entreprise> entrepriseMetier) {
		this.entrepriseMetier = entrepriseMetier;
	}

	@Override
	public PageResult<Entreprise> loadPage(int requestedPage, int pageSize) {
		long totalEntreprises = entrepriseMetier.count();
		int totalPages = (int) Math.ceil(totalEntreprises / (double) pageSize);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(Math.max(requestedPage, 1), totalPages);
		List<Entreprise> items = entrepriseMetier.getPage(currentPage, pageSize);
		return new PageResult<>(items, currentPage, totalPages, pageSize, totalEntreprises, totalEntreprises > pageSize);
	}

	@Override
	public Entreprise getOne(int id) {
		return entrepriseMetier.getOne(id);
	}

	@Override
	public void save(Entreprise entreprise) {
		entrepriseMetier.save(entreprise);
	}

	@Override
	public void update(Entreprise entreprise) {
		entrepriseMetier.update(entreprise);
	}

	@Override
	public void delete(int id) {
		entrepriseMetier.delete(new Entreprise(id));
	}
}
