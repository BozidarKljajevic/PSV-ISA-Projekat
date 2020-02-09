package com.example.demo.dto;

import com.example.demo.model.AdminCentra;
import com.example.demo.model.AdminKlinike;

public class AdminCentraDTO {

	private Long id;
	private String mail;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	
	public AdminCentraDTO() {
		super();
	}

	public AdminCentraDTO(Long id, String mail, String ime, String prezime, String adresa, String grad, String drzava,
			String brojTelefona) {
		super();
		this.id = id;
		this.mail = mail;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
	}
	
	public AdminCentraDTO(AdminCentra adminC) {
		this.id = adminC.getId();
		this.mail = adminC.getMail();
		this.ime = adminC.getIme();
		this.prezime = adminC.getPrezime();
		this.adresa = adminC.getAdresa();
		this.grad = adminC.getGrad();
		this.drzava = adminC.getDrzava();
		this.brojTelefona = adminC.getBrojTelefona();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
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
	
	
	
}
