package com.example.demo.model;

import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Recept {

	@Id
	@SequenceGenerator(name = "recept_id_seq", sequenceName = "recept_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recept_id_seq")
	private Long id;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "sestra_id")
	private MedicinskaSestra sestra;
	
	@Column(name = "overen")
	private boolean overen;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "lekovi_recept", joinColumns = @JoinColumn(name = "recept_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "lek_id", referencedColumnName = "sifra"))
	private List<Lek> lekoviRecepta;
	/*
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "izvestaj_id")
	private IzvestajOPregledu izvestaj;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	public IzvestajOPregledu getIzvestaj() {
		return izvestaj;
	}

	public void setIzvestaj(IzvestajOPregledu izvestaj) {
		this.izvestaj = izvestaj;
	}*/

	public MedicinskaSestra getSestra() {
		return sestra;
	}

	public void setSestra(MedicinskaSestra sestra) {
		this.sestra = sestra;
	}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public List<Lek> getLekoviRecepta() {
		return lekoviRecepta;
	}

	public void setLekoviRecepta(List<Lek> lekoviRecepta) {
		this.lekoviRecepta = lekoviRecepta;
	}
	
	
}
