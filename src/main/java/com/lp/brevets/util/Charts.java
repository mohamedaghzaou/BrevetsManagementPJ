package com.lp.brevets.util;

import java.util.List;

public class Charts {

	public Charts() {
	}

	public Charts(List<InventionParEntreprise> inventionParEntreprises,
			List<InventionParDomaine> inventionParDomaines, long totalBrevets, long totalInventions,
			long totalInventeurs, long totalEntreprises, long totalDomaines) {
		super();
		this.inventionParEntreprises = inventionParEntreprises;
		this.inventionParDomaines = inventionParDomaines;
		this.totalBrevets = totalBrevets;
		this.totalInventions = totalInventions;
		this.totalInventeurs = totalInventeurs;
		this.totalEntreprises = totalEntreprises;
		this.totalDomaines = totalDomaines;
	}

	private List<InventionParEntreprise> inventionParEntreprises;
	private List<InventionParDomaine> inventionParDomaines;
	private long totalBrevets;
	private long totalInventions;
	private long totalInventeurs;
	private long totalEntreprises;
	private long totalDomaines;

	public List<InventionParEntreprise> getInventionParEntreprises() {
		return inventionParEntreprises;
	}

	public void setInventionParEntreprises(List<InventionParEntreprise> inventionParEntreprises) {
		this.inventionParEntreprises = inventionParEntreprises;
	}

	public List<InventionParDomaine> getInventionParDomaines() {
		return inventionParDomaines;
	}

	public void setInventionParDomaines(List<InventionParDomaine> inventionParDomaines) {
		this.inventionParDomaines = inventionParDomaines;
	}

	public long getTotalBrevets() {
		return totalBrevets;
	}

	public void setTotalBrevets(long totalBrevets) {
		this.totalBrevets = totalBrevets;
	}

	public long getTotalInventions() {
		return totalInventions;
	}

	public void setTotalInventions(long totalInventions) {
		this.totalInventions = totalInventions;
	}

	public long getTotalInventeurs() {
		return totalInventeurs;
	}

	public void setTotalInventeurs(long totalInventeurs) {
		this.totalInventeurs = totalInventeurs;
	}

	public long getTotalEntreprises() {
		return totalEntreprises;
	}

	public void setTotalEntreprises(long totalEntreprises) {
		this.totalEntreprises = totalEntreprises;
	}

	public long getTotalDomaines() {
		return totalDomaines;
	}

	public void setTotalDomaines(long totalDomaines) {
		this.totalDomaines = totalDomaines;
	}

}

