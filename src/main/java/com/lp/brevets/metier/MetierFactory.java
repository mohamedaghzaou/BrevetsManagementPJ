package com.lp.brevets.metier;

public class MetierFactory {

	public static IMetier getMetier(METIER type) {

		switch (type) {
		case BREVET:
			return MetierBrevet.INSTANCE;
		case ENTREPRISE:
			return MetierEntreprise.INSTANCE;
		case DOMAINE:
			return MetierDomaine.INSTANCE;
		case INVENTEUR:
			return MetierInventeur.INSTANCE;
		case INVENTION:	
			return MetierInvention.INSTANCE;
		}
		return null;
	}

}

