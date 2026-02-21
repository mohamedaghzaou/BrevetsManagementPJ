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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(indexes = {
		@Index(name = "idx_inventeur_entreprise", columnList = "NUM_ENTREPRISE")
})
@Getter
@Setter
@NoArgsConstructor
public class Inventeur implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_INVENTEUR")
	private int num;
	@Column(name = "NOM")
	@NotBlank(message = "Le nom est obligatoire.")
	@Size(max = 80, message = "Le nom ne doit pas depasser 80 caracteres.")
	private String nom;
	@Column(name = "PRENOM")
	@NotBlank(message = "Le prenom est obligatoire.")
	@Size(max = 80, message = "Le prenom ne doit pas depasser 80 caracteres.")
	private String prenom;
	@Column(name = "ADRESSE")
	@NotBlank(message = "L'adresse est obligatoire.")
	@Size(max = 255, message = "L'adresse ne doit pas depasser 255 caracteres.")
	private String adresse;
	@Column(name = "DATE_NAISS")
	@NotNull(message = "La date de naissance est obligatoire.")
	@PastOrPresent(message = "La date de naissance ne peut pas etre dans le futur.")
	private LocalDate date_nais;
	@Column(name = "EMAIL")
	@NotBlank(message = "L'email est obligatoire.")
	@Email(message = "Le format de l'email est invalide.")
	@Size(max = 120, message = "L'email ne doit pas depasser 120 caracteres.")
	private String email;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_ENTREPRISE")
	@NotNull(message = "L'entreprise est obligatoire.")
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

