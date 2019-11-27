package com.example.demo.dto;

import com.example.demo.model.AdminKlinike;
import com.example.demo.model.MedicinskoOsoblje;

public class MedicinskoOsobljeDTO {

	private Long id;
	private String mail;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private KlinikaDTO idKlinike;
	private Boolean lekar;
	private String radnoOd;
	private String radnoDo;

	
	public MedicinskoOsobljeDTO() {
		
	}
	
	public MedicinskoOsobljeDTO(MedicinskoOsoblje medOsoblje) {
		this.id = medOsoblje.getId();
		this.mail = medOsoblje.getMail();
		this.ime = medOsoblje.getIme();
		this.prezime = medOsoblje.getPrezime();
		this.adresa = medOsoblje.getAdresa();
		this.grad = medOsoblje.getGrad();
		this.drzava = medOsoblje.getDrzava();
		this.brojTelefona = medOsoblje.getBrojTelefona();
		this.lekar = medOsoblje.getLekar();
		this.radnoOd = medOsoblje.getRadnoOd();
		this.radnoDo = medOsoblje.getRadnoDo();
		idKlinike = new KlinikaDTO(medOsoblje.getKlinika());
	}

	public MedicinskoOsobljeDTO(Long id, String mail, String ime, String prezime, String adresa, String grad, String drzava,
			String brojTelefona,Boolean lekar,String radnoOd, String radnoDo) {
		super();
		this.id = id;
		this.mail = mail;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
		this.lekar = lekar;
		this.radnoOd = radnoOd;
		this.radnoDo = radnoDo;
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
	
	public KlinikaDTO getIdKlinike() {
		return idKlinike;
	}

	public void setKlinikaId(KlinikaDTO idKlinike) {
		this.idKlinike = idKlinike ;
	}
	
	public Boolean getLekar() {
		return lekar;
	}

	public void setLekar(Boolean lekar) {
		this.lekar = lekar;
	}
	
	public String getRadnoOd() {
		return radnoOd;
	}

	public void setRadnoOd(String radnoOd) {
		this.radnoOd = radnoOd;
	}
	
	public String getRadnoDo() {
		return radnoDo;
	}

	public void setRadnoDo(String radnoDo) {
		this.radnoDo = radnoDo;
	}
}
