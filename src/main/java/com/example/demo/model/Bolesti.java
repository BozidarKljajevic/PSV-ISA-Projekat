package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bolesti {

	@Id
	@Column(name = "sifra", nullable = false, unique = true)
	private Long sifra;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	
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
}
