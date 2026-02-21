package com.lp.brevets.services;

import java.util.List;

import com.lp.brevets.models.Domaine;
import com.lp.brevets.models.Invention;
import com.lp.brevets.services.dto.PageResult;

public interface InventionService {
	PageResult<Invention> loadPage(int requestedPage, int pageSize);

	Invention getOne(int id);

	List<Domaine> getDomaineOptions();

	void save(Invention invention);

	void update(Invention invention);

	void delete(int id);
}
