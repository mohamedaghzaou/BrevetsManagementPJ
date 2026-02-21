package com.lp.brevets.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(indexes = {
		@Index(name = "idx_brevet_date_depot", columnList = "DATE_DEPOT"),
		@Index(name = "idx_brevet_date_validation", columnList = "DATE_VALIDATION"),
		@Index(name = "idx_brevet_inventeur", columnList = "num_inventeur"),
		@Index(name = "idx_brevet_invention", columnList = "num_invention")
})
@Getter
@Setter
@NoArgsConstructor
public class Brevet implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_BREVET")
	private int num;
	@Column(name = "DESCRIPTION")
	@NotBlank(message = "La description est obligatoire.")
	@Size(max = 255, message = "La description ne doit pas depasser 255 caracteres.")
	private String description;
	@Column(name = "DATE_DEPOT")
	@NotNull(message = "La date de depot est obligatoire.")
	private LocalDate dateDepot;
	@Column(name = "DATE_VALIDATION")
	@NotNull(message = "La date de validation est obligatoire.")
	private LocalDate dateValidation;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "num_invention")
	@NotNull(message = "L'invention est obligatoire.")
	private Invention invention;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "num_inventeur")
	@NotNull(message = "L'inventeur est obligatoire.")
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

