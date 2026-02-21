package com.lp.brevets.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(indexes = {
		@Index(name = "idx_entreprise_nom", columnList = "NOM_ENTREPRISE")
})
@Getter
@Setter
@NoArgsConstructor
public class Entreprise implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_ENTREPRISE")
	private int num;
	@Column(name = "NOM_ENTREPRISE")
	@NotBlank(message = "Le nom de l'entreprise est obligatoire.")
	@Size(max = 120, message = "Le nom de l'entreprise ne doit pas depasser 120 caracteres.")
	private String nom;
	@Column(name = "ACTIVITE")
	@NotBlank(message = "L'activite est obligatoire.")
	@Size(max = 120, message = "L'activite ne doit pas depasser 120 caracteres.")
	private String activite;
	@Column(name = "CA")
	@PositiveOrZero(message = "Le chiffre d'affaires doit etre superieur ou egal a 0.")
	private double ca;
	@Column(name = "VILLE")
	@NotBlank(message = "La ville est obligatoire.")
	@Size(max = 120, message = "La ville ne doit pas depasser 120 caracteres.")
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

