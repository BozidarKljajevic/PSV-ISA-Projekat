package com.example.demo.dto;

import com.example.demo.model.Bolesti;


public class BolestiDTO {

	private String naziv;
	private String sifra;
	
	
	public BolestiDTO() {
		
	}

	public BolestiDTO(Bolesti bolesti) {
		this.naziv = bolesti.getNaziv();
		this.sifra = bolesti.getSifra();
		
	}
	
	public BolestiDTO(String naziv, String sifra) {
		super();
		this.naziv = naziv;
		this.sifra = sifra;
		
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
}
