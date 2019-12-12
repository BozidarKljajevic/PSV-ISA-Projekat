package com.example.demo.dto;

public class PorukaDTO {

	private String poruka;

	public PorukaDTO() {
		
	}
	
	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}

	public PorukaDTO(String poruka) {
		super();
		this.poruka = poruka;
	}
}
