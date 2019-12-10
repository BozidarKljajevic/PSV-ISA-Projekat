package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AdminKlinike extends User {

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Klinika klinika;

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
	
}
