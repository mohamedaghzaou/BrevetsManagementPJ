package com.lp.brevets.services;

import com.lp.brevets.models.Entreprise;
import com.lp.brevets.services.dto.PageResult;

public interface EntrepriseService {
	PageResult<Entreprise> loadPage(int requestedPage, int pageSize);

	Entreprise getOne(int id);

	void save(Entreprise entreprise);

	void update(Entreprise entreprise);

	void delete(int id);
}
