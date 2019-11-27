package com.example.demo.dto;

import com.example.demo.model.NeaktivanPacijent;
import com.example.demo.model.Pacijent;

public class PacijentDTO {

	private Long id;
	private String mail;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	
	public PacijentDTO() {

	}
	
	public PacijentDTO(Pacijent pacijent) {
		this.id = pacijent.getId();
		this.mail = pacijent.getMail();
		this.ime = pacijent.getIme();
		this.prezime = pacijent.getPrezime();
		this.adresa = pacijent.getAdresa();
		this.grad = pacijent.getGrad();
		this.drzava = pacijent.getDrzava();
		this.brojTelefona = pacijent.getBrojTelefona();
	}
	
	public PacijentDTO(NeaktivanPacijent pacijent) {
		this.id = pacijent.getId();
		this.mail = pacijent.getMail();
		this.ime = pacijent.getIme();
		this.prezime = pacijent.getPrezime();
		this.adresa = pacijent.getAdresa();
		this.grad = pacijent.getGrad();
		this.drzava = pacijent.getDrzava();
		this.brojTelefona = pacijent.getBrojTelefona();
	}
	
	public PacijentDTO(RegisterDTO pacijent) {
		this.id = pacijent.getId();
		this.mail = pacijent.getMail();
		this.ime = pacijent.getIme();
		this.prezime = pacijent.getPrezime();
		this.adresa = pacijent.getAdresa();
		this.grad = pacijent.getGrad();
		this.drzava = pacijent.getDrzava();
		this.brojTelefona = pacijent.getBrojTelefona();
	}

	public PacijentDTO(Long id, String mail, String ime, String prezime, String adresa, String grad, String drzava,
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
