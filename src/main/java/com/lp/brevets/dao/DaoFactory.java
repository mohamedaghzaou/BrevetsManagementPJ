package com.lp.brevets.dao;

public class DaoFactory {

	public static IDAO getDAO(DAO type) {

		switch (type) {
		case BREVET:
			return new DaoBrevet();
		case ENTREPRISE:
			return new DaoEntreprise();
		case DOMAINE:
			return new DaoDomaine();
		case INVENTEUR:
			return new DaoInventeur();
		case INVENTION:
			return new DaoInvention();
		}
		return null;
	}
}

