package com.lp.brevets.services.impl;

import java.util.List;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.services.DomaineService;
import com.lp.brevets.services.dto.PageResult;

public class DomaineServiceImpl implements DomaineService {
	private final IMetier<Domaine> domaineMetier;

	public DomaineServiceImpl(IMetier<Domaine> domaineMetier) {
		this.domaineMetier = domaineMetier;
	}

	@Override
	public PageResult<Domaine> loadPage(int requestedPage, int pageSize) {
		long totalDomaines = domaineMetier.count();
		int totalPages = (int) Math.ceil(totalDomaines / (double) pageSize);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(Math.max(requestedPage, 1), totalPages);
		List<Domaine> items = domaineMetier.getPage(currentPage, pageSize);
		return new PageResult<>(items, currentPage, totalPages, pageSize, totalDomaines, totalDomaines > pageSize);
	}

	@Override
	public Domaine getOne(int id) {
		return domaineMetier.getOne(id);
	}

	@Override
	public void save(Domaine domaine) {
		domaineMetier.save(domaine);
	}

	@Override
	public void update(Domaine domaine) {
		domaineMetier.update(domaine);
	}

	@Override
	public void delete(int id) {
		domaineMetier.delete(new Domaine(id));
	}
}
