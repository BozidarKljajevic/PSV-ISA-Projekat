package com.example.demo.dto;

public class IzmenaSifreDTO {

	private String stara;
	private String nova;
	private String potvrda;

	public IzmenaSifreDTO() {
		
	}

	public IzmenaSifreDTO(String stara, String nova, String potvrda) {
		super();
		this.stara = stara;
		this.nova = nova;
		this.potvrda = potvrda;
	}

	public String getStara() {
		return stara;
	}

	public void setStara(String stara) {
		this.stara = stara;
	}

	public String getNova() {
		return nova;
	}

	public void setNova(String nova) {
		this.nova = nova;
	}

	public String getPotvrda() {
		return potvrda;
	}

	public void setPotvrda(String potvrda) {
		this.potvrda = potvrda;
	}
}
