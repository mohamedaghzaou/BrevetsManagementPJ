package com.lp.brevets.util;

import java.io.Serializable;

public class InventionParEntreprise implements Serializable {

	private String Entreprise;
	private long nbInventions;

	public InventionParEntreprise() {
	}

	public InventionParEntreprise(String entreprise, long nbInventions) {
		super();
		Entreprise = entreprise;
		this.nbInventions = nbInventions;
	}

	public String getEntreprise() {
		return Entreprise;
	}

	public void setEntreprise(String entreprise) {
		Entreprise = entreprise;
	}

	public long getNbInventions() {
		return nbInventions;
	}

	public void setNbInventions(long nbInventions) {
		this.nbInventions = nbInventions;
	}

	@Override
	public String toString() {
		return "InventionParEntreprise [Entreprise=" + Entreprise + ", nbInventions=" + nbInventions + "]";
	}

}

