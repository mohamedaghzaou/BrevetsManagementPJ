package com.lp.brevets.services;

import java.util.List;

import com.lp.brevets.models.Brevet;
import com.lp.brevets.models.Invention;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.services.dto.BrevetListViewData;
import com.lp.brevets.services.dto.BrevetSearchCriteria;

public interface BrevetService {
	BrevetListViewData loadList(BrevetSearchCriteria criteria, int requestedPage, int pageSize);

	Brevet getOne(int id);

	List<Inventeur> getInventeurOptions();

	List<Invention> getInventionOptions();

	void save(Brevet brevet);

	void update(Brevet brevet);

	void delete(int id);
}
