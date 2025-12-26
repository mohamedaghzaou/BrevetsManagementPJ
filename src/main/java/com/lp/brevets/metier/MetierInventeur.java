package com.lp.brevets.metier;

import java.util.List;
import java.util.Optional;

import com.lp.brevets.dao.DAO;
import com.lp.brevets.dao.DaoFactory;
import com.lp.brevets.dao.IDAO;
import com.lp.brevets.models.Inventeur;

public class MetierInventeur implements IMetier<Inventeur> {

	private static IDAO<Inventeur> daoInventeur;
	public final static MetierInventeur INSTANCE = new MetierInventeur();

	public MetierInventeur() {
		if (Optional.ofNullable(daoInventeur).isEmpty()) {
			daoInventeur = DaoFactory.getDAO(DAO.INVENTEUR);
		}
	}

	@Override
	public List<Inventeur> getAll() {
		return daoInventeur.getAll();
	}

	@Override
	public Inventeur getOne(int id) {
		return daoInventeur.getOne(id);
	}

	@Override
	public boolean save(Inventeur obj) {
		return daoInventeur.save(obj);
	}

	@Override
	public boolean update(Inventeur obj) {
		return daoInventeur.update(obj);
	}

	@Override
	public boolean delete(Inventeur obj) {
		return daoInventeur.delete(obj);
	}

}

