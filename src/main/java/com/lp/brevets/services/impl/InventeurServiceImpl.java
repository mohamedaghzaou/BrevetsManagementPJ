package com.lp.brevets.services.impl;

import java.util.List;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.services.InventeurService;
import com.lp.brevets.services.dto.PageResult;

public class InventeurServiceImpl implements InventeurService {
	private final IMetier<Inventeur> inventeurMetier;
	private final IMetier<Entreprise> entrepriseMetier;

	public InventeurServiceImpl(IMetier<Inventeur> inventeurMetier, IMetier<Entreprise> entrepriseMetier) {
		this.inventeurMetier = inventeurMetier;
		this.entrepriseMetier = entrepriseMetier;
	}

	@Override
	public PageResult<Inventeur> loadPage(int requestedPage, int pageSize) {
		long totalInventeurs = inventeurMetier.count();
		int totalPages = (int) Math.ceil(totalInventeurs / (double) pageSize);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(Math.max(requestedPage, 1), totalPages);
		List<Inventeur> items = inventeurMetier.getPage(currentPage, pageSize);
		return new PageResult<>(items, currentPage, totalPages, pageSize, totalInventeurs, totalInventeurs > pageSize);
	}

	@Override
	public Inventeur getOne(int id) {
		return inventeurMetier.getOne(id);
	}

	@Override
	public List<Entreprise> getEntrepriseOptions() {
		return entrepriseMetier.getAll();
	}

	@Override
	public void save(Inventeur inventeur) {
		inventeurMetier.save(inventeur);
	}

	@Override
	public void update(Inventeur inventeur) {
		inventeurMetier.update(inventeur);
	}

	@Override
	public void delete(int id) {
		inventeurMetier.delete(new Inventeur(id));
	}
}
