package com.example.demo.dto;

import com.example.demo.model.Klinika;

public class KlinikaDTO {

	private Long id;
	private String naziv;
	private String opis;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private Double ocena;
	
	public KlinikaDTO() {
		
	}

	public KlinikaDTO(Klinika klinika) {
		this.id = klinika.getId();
		this.naziv = klinika.getNaziv();
		this.opis = klinika.getOpis();
		this.adresa = klinika.getAdresa();
		this.opis = klinika.getOpis();
		this.grad = klinika.getGrad();
		this.drzava = klinika.getDrzava();
		this.brojTelefona = klinika.getBrojTelefona();
		this.ocena = klinika.getOcena();
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


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public String getAdresa() {
		return adresa;
	}


	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}


	public String getGrad() {
		return grad;
	}


	public void setGrad(String grad) {
		this.grad = grad;
	}


	public String getDrzava() {
		return drzava;
	}


	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}


	public String getBrojTelefona() {
		return brojTelefona;
	}


	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}


	public Double getOcena() {
		return ocena;
	}


	public void setOcena(Double ocena) {
		this.ocena = ocena;
	}


	public KlinikaDTO(Long id, String naziv, String opis, String adresa, String grad, String drzava,
			String brojTelefona, Double ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		this.ocena = ocena;
	}
	
}
