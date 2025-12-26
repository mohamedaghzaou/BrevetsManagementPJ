package com.lp.brevets.metier;

import java.util.List;
import java.util.Optional;

import com.lp.brevets.dao.DAO;
import com.lp.brevets.dao.DaoBrevet;
import com.lp.brevets.dao.DaoFactory;
import com.lp.brevets.dao.IDAO;
import com.lp.brevets.models.Brevet;

public class MetierBrevet implements IMetier<Brevet> {

	private static IDAO<Brevet> daoBrevet;
	public final static MetierBrevet INSTANCE = new MetierBrevet();

	private MetierBrevet() {
		if (Optional.ofNullable(daoBrevet).isEmpty()) {
			daoBrevet = (DaoBrevet) DaoFactory.getDAO(DAO.BREVET);
		}
	}

	@Override
	public List<Brevet> getAll() {
		return daoBrevet.getAll();
	}

	@Override
	public Brevet getOne(int id) {
		return daoBrevet.getOne(id);
	}

	@Override
	public boolean save(Brevet obj) {
		return daoBrevet.save(obj);
	}

	@Override
	public boolean update(Brevet obj) {
		return daoBrevet.update(obj);
	}

	@Override
	public boolean delete(Brevet obj) {
		return daoBrevet.delete(obj);
	}

}

