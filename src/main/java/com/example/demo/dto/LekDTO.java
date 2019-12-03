package com.example.demo.dto;

import com.example.demo.model.Bolesti;
import com.example.demo.model.Lek;

public class LekDTO {

	private String naziv;
	private Long sifra;
	
	
	public LekDTO() {
		
	}

	public LekDTO(Lek lek) {
		this.naziv = lek.getNaziv();
		this.sifra = lek.getSifra();
		
	}
	
	public LekDTO(String naziv, Long sifra) {
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
