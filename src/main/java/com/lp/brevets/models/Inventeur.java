package com.lp.brevets.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inventeur implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_INVENTEUR")
	private int num;
	@Column(name = "NOM")
	private String nom;
	@Column(name = "PRENOM")
	private String prenom;
	@Column(name = "ADRESSE")
	private String adresse;
	@Column(name = "DATE_NAISS")
	private LocalDate date_nais;
	@Column(name = "EMAIL")
	private String email;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NUM_ENTREPRISE")
	private Entreprise entreprise;
	@OneToMany(mappedBy = "inventeur", cascade = CascadeType.REMOVE)
	private List<Brevet> brevets;

	public Inventeur(int num) {
		this.num = num;
	}

	public Inventeur(String nom, String prenom, String adresse, LocalDate date_nais, String email) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.date_nais = date_nais;
		this.email = email;
	}

	public Inventeur(String nom, String prenom, String adresse, LocalDate date_nais, String email,
			Entreprise entreprise) {
		this(nom, prenom, adresse, date_nais, email);
		this.entreprise = entreprise;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventeur other = (Inventeur) obj;
		if (num != other.num)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nom.toUpperCase() + " " + prenom;
	}

}

