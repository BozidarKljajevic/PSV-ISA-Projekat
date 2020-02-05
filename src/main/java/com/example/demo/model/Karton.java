package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Karton {

	@Id
	@SequenceGenerator(name = "karton_id_seq", sequenceName = "karton_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "karton_id_seq")
	private Long id;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "pacijent_id")
	private Pacijent pacijent;
	
	@Column(name = "datum_rodjenja")
	private String datumRodjenja;
	
	@Column(name = "pol")
	private String pol;
	
	@Column(name = "visina")
	private Double visina;
	
	@Column(name = "tezina")
	private Double tezina;
	
	@Column(name = "krvna_grupa")
	private String krvnaGrupa;
	
	@Column(name = "dioptrija")
	private Double dioptrija;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "karton", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<IzvestajOPregledu> izvestajiOPregledima = new HashSet<IzvestajOPregledu>();

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

	public String getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public Double getVisina() {
		return visina;
	}

	public void setVisina(Double visina) {
		this.visina = visina;
	}

	public Double getTezina() {
		return tezina;
	}

	public void setTezina(Double tezina) {
		this.tezina = tezina;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public Double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(Double dioptrija) {
		this.dioptrija = dioptrija;
	}

	public Set<IzvestajOPregledu> getIzvestajiOPregledima() {
		return izvestajiOPregledima;
	}

	public void setIzvestajiOPregledima(Set<IzvestajOPregledu> izvestajiOPregledima) {
		this.izvestajiOPregledima = izvestajiOPregledima;
	}
}
