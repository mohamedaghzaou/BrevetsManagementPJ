package com.lp.brevets.metier;

import java.util.List;
import java.util.Optional;

import com.lp.brevets.dao.DAO;
import com.lp.brevets.dao.DaoFactory;
import com.lp.brevets.dao.IDAO;
import com.lp.brevets.models.Invention;
import com.lp.brevets.models.Invention;

public class MetierInvention implements IMetier<Invention> {

	private static IDAO<Invention> daoInvention;
	public final static MetierInvention INSTANCE = new MetierInvention();

	private MetierInvention() {
		if (Optional.ofNullable(daoInvention).isEmpty()) {
			daoInvention = DaoFactory.getDAO(DAO.INVENTION);
		}
	}

	@Override
	public List<Invention> getAll() {
		return daoInvention.getAll();
	}

	@Override
	public Invention getOne(int id) {
		return daoInvention.getOne(id);
	}

	@Override
	public boolean save(Invention obj) {
		return daoInvention.save(obj);
	}

	@Override
	public boolean update(Invention obj) {
		return daoInvention.update(obj);
	}

	@Override
	public boolean delete(Invention obj) {
		return daoInvention.delete(obj);
	}

}

