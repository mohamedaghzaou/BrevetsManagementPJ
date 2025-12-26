package com.lp.brevets.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Entreprise implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_ENTREPRISE")
	private int num;
	@Column(name = "NOM_ENTREPRISE")
	private String nom;
	@Column(name = "ACTIVITE")
	private String activite;
	@Column(name = "CA")
	private double ca;
	@Column(name = "VILLE")
	private String ville;

	@OneToMany(mappedBy = "entreprise", cascade = CascadeType.REMOVE)
	private List<Inventeur> inventeurs;

	public Entreprise(String nom, String activite, double ca, String ville) {
		this.nom = nom;
		this.activite = activite;
		this.ca = ca;
		this.ville = ville;
	}

	public Entreprise(int id) {
		this.num = id;
	}

	@Override
	public String toString() {
		return nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(num);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entreprise other = (Entreprise) obj;
		return num == other.num;
	}
	
	

}

