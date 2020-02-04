package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Lek {

	@Id
	@Column(name = "sifra", nullable = false, unique = true)
	private Long sifra;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	/*
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private IzvestajOPregledu izvestaj;
	*/
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Long getSifra() {
		return sifra;
	}

	public void setSifra(Long sifra) {
		this.sifra = sifra;
	}
/*
	public IzvestajOPregledu getIzvestaj() {
		return izvestaj;
	}

	public void setIzvestaj(IzvestajOPregledu izvestaj) {
		this.izvestaj = izvestaj;
	}
	*/
	
}
