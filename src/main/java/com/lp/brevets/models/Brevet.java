package com.lp.brevets.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Brevet implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_BREVET")
	private int num;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "DATE_DEPOT")
	private LocalDate dateDepot;
	@Column(name = "DATE_VALIDATION")
	private LocalDate dateValidation;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "num_invention")
	private Invention invention;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "num_inventeur")
	private Inventeur inventeur;

	public Brevet(int id) {
		this.num = id;
	}
	

	public Brevet(String description, LocalDate dateDepot, LocalDate dateValidation, Invention invention,
			Inventeur inventeur) {
		this.description = description;
		this.dateDepot = dateDepot;
		this.dateValidation = dateValidation;
		this.invention = invention;
		this.inventeur = inventeur;
	}

	@Override
	public String toString() {
		return "Brevet [num=" + num + ", description=" + description + ", dateDepot=" + dateDepot + ", dateValidation="
				+ dateValidation + "]";
	}

}

