package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Klinika {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "naziv", nullable = false, unique = true)
	private String naziv;
	
	@Column(name = "opis", nullable = false)
	private String opis;
	
	@Column(name = "adresa", nullable = false)
	private String adresa;
	
	@Column(name = "brojTelefona", nullable = false)
	private String brojTelefona;
	
	@Column(name = "grad", nullable = false)
	private String grad;
	
	@Column(name = "drzava", nullable = false)
	private String drzava;
	
	@Column(name = "ocena", nullable = false)
	private Double ocena;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AdminKlinike> adminiKlinike = new HashSet<AdminKlinike>();
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<SalaKlinike> saleKlinike = new HashSet<SalaKlinike>();
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<TipPregleda> tipPregleda = new HashSet<TipPregleda>();
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "klinika", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<MedicinskoOsoblje> medicinskaOsoblja = new HashSet<MedicinskoOsoblje>();
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
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
	
	public Double getOcena() {
		return ocena;
	}

	public void setOcena(Double ocena) {
		this.ocena = ocena;
	}
	public Set<AdminKlinike> getAdminKlinike(){
		return adminiKlinike;
	}
	
	public void setAdminKlinike(Set<AdminKlinike> adminKlinike) {
		this.adminiKlinike=adminKlinike;
	}

	public Set<AdminKlinike> getAdminiKlinike() {
		return adminiKlinike;
	}

	public void setAdminiKlinike(Set<AdminKlinike> adminiKlinike) {
		this.adminiKlinike = adminiKlinike;
	}

	public Set<SalaKlinike> getSaleKlinike() {
		return saleKlinike;
	}

	public void setSaleKlinike(Set<SalaKlinike> saleKlinike) {
		this.saleKlinike = saleKlinike;
	}

	public Set<MedicinskoOsoblje> getMedicinskaOsoblja() {
		return medicinskaOsoblja;
	}

	public void setMedicinskaOsoblja(Set<MedicinskoOsoblje> medicinskaOsoblja) {
		this.medicinskaOsoblja = medicinskaOsoblja;
	}

	public Set<TipPregleda> getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(Set<TipPregleda> tipPregleda) {
		this.tipPregleda = tipPregleda;
	}
	
	//public Set<MedicinskoOsoblje> getMedicinskoOsoblje(){
		//return medicinskaOsoblja;
	//}
	
	//public void setMedicinskoOsoblje(Set<MedicinskoOsoblje> medicinskaOsoblja) {
		//this.medicinskaOsoblja=medicinskaOsoblja;
	//}
}
