package com.example.demo.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class IzvestajOPregledu {

	@Id
	@SequenceGenerator(name = "izvestaj_id_seq", sequenceName = "izvestaj_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "izvestaj_id_seq")
	private Long id;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "pacijent_id")
	private Pacijent pacijenta;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "lekar_id")
	private Lekar lekara;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "bolest_id")
	private Bolesti bolest;
	
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "lekovi_izvestaji", joinColumns = @JoinColumn(name = "izvestaj_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "lek_id", referencedColumnName = "sifra"))
	private List<Lek> lekoviIzvestaja;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "recept_id")
	private Recept recept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	

	public Recept getRecept() {
		return recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}

	public Pacijent getPacijenta() {
		return pacijenta;
	}

	public void setPacijenta(Pacijent pacijenta) {
		this.pacijenta = pacijenta;
	}

	public Lekar getLekara() {
		return lekara;
	}

	public void setLekara(Lekar lekara) {
		this.lekara = lekara;
	}

	public Bolesti getBolest() {
		return bolest;
	}

	public void setBolest(Bolesti bolest) {
		this.bolest = bolest;
	}

	public List<Lek> getLekoviIzvestaja() {
		return lekoviIzvestaja;
	}

	public void setLekoviIzvestaja(List<Lek> lekoviIzvestaja) {
		this.lekoviIzvestaja = lekoviIzvestaja;
	}
	
	
}
