package com.example.demo.dto;

import com.example.demo.model.Bolesti;


public class BolestiDTO {

	private String naziv;
	private Long sifra;
	
	
	public BolestiDTO() {
		
	}

	public BolestiDTO(Bolesti bolesti) {
		this.naziv = bolesti.getNaziv();
		this.sifra = bolesti.getSifra();
		
	}
	
	public BolestiDTO(String naziv, Long sifra) {
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

	public Long getSifra() {
		return sifra;
	}

	public void setSifra(Long sifra) {
		this.sifra = sifra;
	}
}
