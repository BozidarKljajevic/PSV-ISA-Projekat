package com.example.demo.dto;

import com.example.demo.model.Pacijent;

public class LoginDTO {

	private String mail;
	private String sifra;
	
	public LoginDTO() {

	}
	
	public LoginDTO(String mail, String sifra) {
		super();
		this.mail = mail;
		this.sifra = sifra;
	}

	public LoginDTO(Pacijent pacijent) {
		super();
		this.mail = pacijent.getMail();
		this.sifra = pacijent.getSifra();
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
}
