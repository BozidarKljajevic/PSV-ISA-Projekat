package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class OcenaKlinika {

	@Id
	@SequenceGenerator(name = "ocenaKlinika_id_seq", sequenceName = "ocenaKlinika_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ocenaKlinika_id_seq")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Pacijent pacijent;
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Klinika klinika;
	
	@Column(name = "ocena",nullable = false)
	private Integer ocena;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
	}
}
