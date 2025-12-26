package com.lp.brevets.util;

import java.io.Serializable;

public class InventionParDomaine implements Serializable {

	private String Domaine;
	private long nbInventions;

	public InventionParDomaine() {
	}

	public InventionParDomaine(String domaine, long nbInventions) {
		super();
		Domaine = domaine;
		this.nbInventions = nbInventions;
	}

	public String getEntreprise() {
		return Domaine;
	}

	public void setEntreprise(String domaine) {
		Domaine = domaine;
	}

	public long getNbInventions() {
		return nbInventions;
	}

	public void setNbInventions(long nbInventions) {
		this.nbInventions = nbInventions;
	}

	@Override
	public String toString() {
		return "InventionParDomaine [Domaine=" + Domaine + ", nbInventions=" + nbInventions + "]";
	}

}

