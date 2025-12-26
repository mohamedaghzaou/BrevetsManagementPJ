package com.lp.brevets.models;

import java.io.Serializable;
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
public class Invention implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "NUM_INVENTION")
	private int num;
	@Column(name = "DESCRIPTIF")
	private String descriptif;
	@Column(name = "RESUME")
	private String resume;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "num_domaine")
	private Domaine domaine;
	@OneToMany(mappedBy = "invention", cascade = CascadeType.REMOVE)
	private List<Brevet> brevets;

	public Invention(int num) {
		this.num = num;
	}

	public Invention(String descriptif, String resume, Domaine domaine) {
		super();
		this.descriptif = descriptif;
		this.resume = resume;
		this.domaine = domaine;
	}

	public Invention(int num, String descriptif, String resume) {
		super();
		this.num = num;
		this.descriptif = descriptif;
		this.resume = resume;
	}

	public Invention(int num, String descriptif, String resume, Domaine domaine) {
		this(num, descriptif, resume);
		this.domaine = domaine;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invention other = (Invention) obj;
		if (num != other.num)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  resume.length()>20?  resume.substring(0, 20) + "...": resume;
	}
	
	
}

