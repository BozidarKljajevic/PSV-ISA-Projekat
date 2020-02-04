package com.example.demo.dto;

import com.example.demo.model.OcenaKlinika;
import com.example.demo.model.OcenaLekar;

public class OcenaDTO {

	private PacijentDTO pacijent;
	private LekarDTO lekar;
	private KlinikaDTO klinika;
	private Integer ocenaLekar;
	private Integer ocenaKlinike;

	public OcenaDTO() {

	}
	
	public OcenaDTO(OcenaLekar ocenalekar, OcenaKlinika ocenaklinika) {
		this.pacijent = new PacijentDTO(ocenalekar.getPacijent());
		this.lekar = new LekarDTO(ocenalekar.getLekar());
		this.ocenaLekar = ocenalekar.getOcena();
		this.klinika = new KlinikaDTO(ocenaklinika.getKlinika());
		this.ocenaKlinike = ocenaklinika.getOcena();
	}

	public OcenaDTO(PacijentDTO pacijent, LekarDTO lekar, Integer ocena, KlinikaDTO klinika, Integer ocena2) {
		super();
		this.pacijent = pacijent;
		this.lekar = lekar;
		this.ocenaLekar = ocena;
		this.klinika = klinika;
		this.ocenaKlinike = ocena2;
	}

	public PacijentDTO getPacijent() {
		return pacijent;
	}

	public void setPacijent(PacijentDTO pacijent) {
		this.pacijent = pacijent;
	}

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}

	public Integer getOcenaLekar() {
		return ocenaLekar;
	}

	public void setOcenaLekar(Integer ocenaLekar) {
		this.ocenaLekar = ocenaLekar;
	}

	public KlinikaDTO getKlinika() {
		return klinika;
	}

	public void setKlinika(KlinikaDTO klinika) {
		this.klinika = klinika;
	}

	public Integer getOcenaKlinike() {
		return ocenaKlinike;
	}

	public void setOcenaKlinike(Integer ocenaKlinike) {
		this.ocenaKlinike = ocenaKlinike;
	}
}
