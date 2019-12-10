package com.example.demo.dto;


import com.example.demo.model.MedicinskaSestra;


public class MedicinskaSestraDTO {

	private Long id;
	private String mail;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTelefona;
	private KlinikaDTO klinika;
	private String radnoOd;
	private String radnoDo;

	
	public MedicinskaSestraDTO() {
		
	}
	
	public MedicinskaSestraDTO(MedicinskaSestra medicinskaSestra) {
		this.id = medicinskaSestra.getId();
		this.mail = medicinskaSestra.getMail();
		this.ime = medicinskaSestra.getIme();
		this.prezime = medicinskaSestra.getPrezime();
		this.adresa = medicinskaSestra.getAdresa();
		this.grad = medicinskaSestra.getGrad();
		this.drzava = medicinskaSestra.getDrzava();
		this.brojTelefona = medicinskaSestra.getBrojTelefona();
		this.radnoOd = medicinskaSestra.getRadnoOd();
		this.radnoDo = medicinskaSestra.getRadnoDo();
		klinika = new KlinikaDTO(medicinskaSestra.getKlinika());
		
	}

	public MedicinskaSestraDTO(Long id, String mail, String ime, String prezime, String adresa, String grad, String drzava,
			String brojTelefona,String radnoOd, String radnoDo) {
		super();
		this.id = id;
		this.mail = mail;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTelefona = brojTelefona;
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

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}


}
