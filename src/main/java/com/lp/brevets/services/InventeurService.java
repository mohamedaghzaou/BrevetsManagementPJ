package com.lp.brevets.services;

import java.util.List;

import com.lp.brevets.models.Entreprise;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.services.dto.PageResult;

public interface InventeurService {
	PageResult<Inventeur> loadPage(int requestedPage, int pageSize);

	Inventeur getOne(int id);

	List<Entreprise> getEntrepriseOptions();

	void save(Inventeur inventeur);

	void update(Inventeur inventeur);

	void delete(int id);
}
