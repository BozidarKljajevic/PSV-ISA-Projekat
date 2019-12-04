package com.example.demo.dto;

import com.example.demo.model.TipPregleda;

public class TipPregledaDTO {

	private Long id;
	private String naziv;
	private String oznaka;
	private KlinikaDTO klinika;
	
	public TipPregledaDTO() {
		
	}
	
	public TipPregledaDTO(TipPregleda tipPregleda) {
		this.id = tipPregleda.getId();
		this.naziv = tipPregleda.getNaziv();
		this.oznaka = tipPregleda.getOznaka();
		klinika = new KlinikaDTO(tipPregleda.getKlinika());
	}
	
	public TipPregledaDTO(Long id, String naziv, String oznaka) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.oznaka = oznaka;
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}
	
	
	
}
