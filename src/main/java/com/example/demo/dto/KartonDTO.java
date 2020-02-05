package com.example.demo.dto;

import com.example.demo.model.Karton;

public class KartonDTO {

	private Long id;
	private PacijentDTO pacijent;
	private String datumRodjenja;
	private String pol;
	private Double visina;
	private Double tezina;
	private String krvnaGrupa;
	private Double dioptrija;

	public KartonDTO() {

	}
	
	public KartonDTO(Karton karton) {
		super();
		this.id = karton.getId();
		this.pacijent = new PacijentDTO(karton.getPacijent());
		this.datumRodjenja = karton.getDatumRodjenja();
		this.pol = karton.getPol();
		this.visina = karton.getVisina();
		this.tezina = karton.getTezina();
		this.krvnaGrupa = karton.getKrvnaGrupa();
		this.dioptrija = karton.getDioptrija();
	}

	public KartonDTO(Long id, PacijentDTO pacijent, String datumRodjenja, String pol, Double visina, Double tezina,
			String krvnaGrupa, Double dioptrija) {
		super();
		this.id = id;
		this.pacijent = pacijent;
		this.datumRodjenja = datumRodjenja;
		this.pol = pol;
		this.visina = visina;
		this.tezina = tezina;
		this.krvnaGrupa = krvnaGrupa;
		this.dioptrija = dioptrija;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PacijentDTO getPacijent() {
		return pacijent;
	}

	public void setPacijent(PacijentDTO pacijent) {
		this.pacijent = pacijent;
	}

	public String getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public Double getVisina() {
		return visina;
	}

	public void setVisina(Double visina) {
		this.visina = visina;
	}

	public Double getTezina() {
		return tezina;
	}

	public void setTezina(Double tezina) {
		this.tezina = tezina;
	}

	public String getKrvnaGrupa() {
		return krvnaGrupa;
	}

	public void setKrvnaGrupa(String krvnaGrupa) {
		this.krvnaGrupa = krvnaGrupa;
	}

	public Double getDioptrija() {
		return dioptrija;
	}

	public void setDioptrija(Double dioptrija) {
		this.dioptrija = dioptrija;
	}
}
