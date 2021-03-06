package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;

public class OperacijaDTO {

	private Long id;
	private String datum;
	private String vreme;
	private SalaKlinikeDTO sala;
	private TipPregledaDTO tipOperacije;
	private Double cena;
	private Long idPacijenta;
	private Boolean zavrsen;
	private Double trajanjeOperacije;
	private List<LekarDTO> lekari;
	
	public OperacijaDTO() {
		
		lekari = new ArrayList<LekarDTO>();
	}
	
	public OperacijaDTO(Operacija operacija) {
		super();
		this.id = operacija.getId();
		this.datum = operacija.getDatum();
		this.vreme = operacija.getVreme();
		this.sala = new SalaKlinikeDTO(operacija.getSala());
		this.tipOperacije= new TipPregledaDTO(operacija.getTipOperacije());
		this.cena = operacija.getCena();
		this.idPacijenta = operacija.getIdPacijenta();
		this.zavrsen = operacija.getZavrsen();
		this.trajanjeOperacije = operacija.getTrajanjeOperacije();
		this.lekari = new ArrayList<LekarDTO>();
		for (Lekar lekar : operacija.getLekariKlinike()) {
			lekari.add(new LekarDTO(lekar));
		}
	}
	

	public OperacijaDTO(Long id, String datum, String vreme, SalaKlinikeDTO sala,TipPregledaDTO tipOperacije, Double cena, Long idPacijenta,
			Boolean zavrsen, Double trajanjeOperacije) {
		super();
		this.id = id;
		this.datum = datum;
		this.vreme = vreme;
		this.sala = sala;
		this.tipOperacije=tipOperacije;
		this.cena = cena;
		this.idPacijenta = idPacijenta;
		this.zavrsen = zavrsen;
		this.trajanjeOperacije = trajanjeOperacije;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getVreme() {
		return vreme;
	}

	public void setVreme(String vreme) {
		this.vreme = vreme;
	}

	public SalaKlinikeDTO getSala() {
		return sala;
	}

	public void setSala(SalaKlinikeDTO sala) {
		this.sala = sala;
	}
	
	

	public TipPregledaDTO getTipOperacije() {
		return tipOperacije;
	}

	public void setTipOperacije(TipPregledaDTO tipOperacije) {
		this.tipOperacije = tipOperacije;
	}

	public Double getCena() {
		return cena;
	}

	public void setCena(Double cena) {
		this.cena = cena;
	}

	public Long getIdPacijenta() {
		return idPacijenta;
	}

	public void setIdPacijenta(Long idPacijenta) {
		this.idPacijenta = idPacijenta;
	}

	public Boolean getZavrsen() {
		return zavrsen;
	}

	public void setZavrsen(Boolean zavrsen) {
		this.zavrsen = zavrsen;
	}

	public Double getTrajanjeOperacije() {
		return trajanjeOperacije;
	}

	public void setTrajanjeOperacije(Double trajanjeOperacije) {
		this.trajanjeOperacije = trajanjeOperacije;
	}

	public List<LekarDTO> getLekari() {
		return lekari;
	}

	public void setLekari(List<LekarDTO> lekari) {
		this.lekari = lekari;
	}
}
