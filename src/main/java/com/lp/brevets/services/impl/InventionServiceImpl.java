package com.lp.brevets.services.impl;

import java.util.List;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.models.Invention;
import com.lp.brevets.services.InventionService;
import com.lp.brevets.services.dto.PageResult;

public class InventionServiceImpl implements InventionService {
	private final IMetier<Invention> inventionMetier;
	private final IMetier<Domaine> domaineMetier;

	public InventionServiceImpl(IMetier<Invention> inventionMetier, IMetier<Domaine> domaineMetier) {
		this.inventionMetier = inventionMetier;
		this.domaineMetier = domaineMetier;
	}

	@Override
	public PageResult<Invention> loadPage(int requestedPage, int pageSize) {
		long totalInventions = inventionMetier.count();
		int totalPages = (int) Math.ceil(totalInventions / (double) pageSize);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(Math.max(requestedPage, 1), totalPages);
		List<Invention> items = inventionMetier.getPage(currentPage, pageSize);
		return new PageResult<>(items, currentPage, totalPages, pageSize, totalInventions, totalInventions > pageSize);
	}

	@Override
	public Invention getOne(int id) {
		return inventionMetier.getOne(id);
	}

	@Override
	public List<Domaine> getDomaineOptions() {
		return domaineMetier.getAll();
	}

	@Override
	public void save(Invention invention) {
		inventionMetier.save(invention);
	}

	@Override
	public void update(Invention invention) {
		inventionMetier.update(invention);
	}

	@Override
	public void delete(int id) {
		inventionMetier.delete(new Invention(id));
	}
}
