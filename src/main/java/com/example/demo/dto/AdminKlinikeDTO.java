package com.example.demo.dto;

import com.example.demo.model.AdminKlinike;


public class AdminKlinikeDTO {

	private Long id;
	private String mail;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	
	public AdminKlinikeDTO() {
		
	}
	
	public AdminKlinikeDTO(AdminKlinike adminK) {
		this.id = adminK.getId();
		this.mail = adminK.getMail();
		this.ime = adminK.getIme();
		this.prezime = adminK.getPrezime();
		this.adresa = adminK.getAdresa();
		this.grad = adminK.getGrad();
		this.drzava = adminK.getDrzava();
		this.brojTelefona = adminK.getBrojTelefona();
	}

	public AdminKlinikeDTO(Long id, String mail, String ime, String prezime, String adresa, String grad, String drzava,
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
