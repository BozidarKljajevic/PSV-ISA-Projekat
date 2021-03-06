package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Lek;
import com.example.demo.model.Recept;

public class ReceptDTO {

	private Long id;
	private MedicinskaSestraDTO sestra;
	private boolean overen;
	private List<LekDTO> lekovi;
	//private IzvestajOPregleduDTO izvestaj;
	
	public ReceptDTO() {
		super();
		lekovi = new ArrayList<LekDTO>();
	}

	public ReceptDTO(Long id, MedicinskaSestraDTO sestra, boolean izbor, List<LekDTO> lekovi) {
		super();
		this.id = id;
		this.sestra = sestra;
		this.overen = izbor;
		this.lekovi = lekovi;
		//this.izvestaj = izvestaj;
		
	}
	
	public ReceptDTO(Recept recept) {
		super();
		this.id = recept.getId();
		if (recept.getSestra() != null) {
			this.sestra = new MedicinskaSestraDTO(recept.getSestra());
		}else {
			this.sestra = null;
		}
		this.overen= recept.isOveren();
		//this.izvestaj= new IzvestajOPregleduDTO(recept.getIzvestaj());
		this.lekovi = new ArrayList<LekDTO>();
		for (Lek lek : recept.getLekoviRecepta()) {
			lekovi.add(new LekDTO(lek));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MedicinskaSestraDTO getSestra() {
		return sestra;
	}

	public void setSestra(MedicinskaSestraDTO sestra) {
		this.sestra = sestra;
	}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public List<LekDTO> getLekovi() {
		return lekovi;
	}

	public void setLekovi(List<LekDTO> lekovi) {
		this.lekovi = lekovi;
	}
}
