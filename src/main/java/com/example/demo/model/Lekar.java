package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Lekar extends User {

	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Klinika klinika;
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private TipPregleda tipPregleda;
	
	@Column(name = "ocena",nullable = false)
	private String ocena;
	
	@Column(name = "radnoOd",nullable = false)
	private String radnoOd;
	
	@Column(name = "radnoDo",nullable = false)
	private String radnoDo;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<OcenaLekar> ocene = new HashSet<OcenaLekar>();

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public String getRadnoOd() {
		return radnoOd;
	}

	public void setRadnoOd(String radnoOd) {
		this.radnoOd = radnoOd;
	}

	public String getRadnoDo() {
		return radnoDo;
	}

	public void setRadnoDo(String radnoDo) {
		this.radnoDo = radnoDo;
	}

	public String getOcena() {
		return ocena;
	}

	public void setOcena(String ocena) {
		this.ocena = ocena;
	}

	public Set<OcenaLekar> getOcene() {
		return ocene;
	}

	public void setOcene(Set<OcenaLekar> ocene) {
		this.ocene = ocene;
	}
}
