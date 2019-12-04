package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MedicinskoOsoblje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mail", nullable = false, unique = true)
	private String mail;
	
	@Column(name = "lozinka", nullable = false)
	private String lozinka;
	
	@Column(name = "ime", nullable = false)
	private String ime;
	
	@Column(name = "prezime", nullable = false)
	private String prezime;

	@Column(name = "adresa", nullable = false)
	private String adresa;

	@Column(name = "grad", nullable = false)
	private String grad;

	@Column(name = "drzava", nullable = false)
	private String drzava;

	@Column(name = "brojTelefona", nullable = false)
	private String brojTelefona;
	
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private Klinika klinika;
	
	
	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//private Klinika klinika;
	
	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}

	@Column(name = "lekar",nullable = false)
	private Boolean lekar;
	
	@Column(name = "radnoOd",nullable = false)
	private String radnoOd;
	
	@Column(name = "radnoDo",nullable = false)
	private String radnoDo;
	
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

	public String getLozinka() {
		return lozinka;
	}

	public void setSifra(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
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
	
	public Boolean getLekar() {
		return lekar;
	}

	public void setLekar(Boolean lekar) {
		this.lekar = lekar;
	}
	
	//public Klinika getKlinika() {
		//return klinika;
	//}

	//public void setKlinika(Klinika klinika) {
		//this.klinika = klinika;
	//}

}
