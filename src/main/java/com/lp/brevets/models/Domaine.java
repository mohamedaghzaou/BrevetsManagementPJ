package com.lp.brevets.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Domaine implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_DOMAINE")
	private int num;
	@Column(name = "NOM_DOMAINE")
	private String nom;
	@OneToMany(mappedBy = "domaine", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Invention> inventions;

	public Domaine(int num, String nom) {
		this.num = num;
		this.nom = nom;
	}

	public Domaine(int num) {
		this.num = num;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Domaine) && ((Domaine) obj).num == this.num;

	}

	@Override
	public String toString() {
		return nom;
	}

}

