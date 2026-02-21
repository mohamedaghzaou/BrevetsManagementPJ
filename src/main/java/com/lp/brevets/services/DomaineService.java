package com.lp.brevets.services;

import com.lp.brevets.models.Domaine;
import com.lp.brevets.services.dto.PageResult;

public interface DomaineService {
	PageResult<Domaine> loadPage(int requestedPage, int pageSize);

	Domaine getOne(int id);

	void save(Domaine domaine);

	void update(Domaine domaine);

	void delete(int id);
}
