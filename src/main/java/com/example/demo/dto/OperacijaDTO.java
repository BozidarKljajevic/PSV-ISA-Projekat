package com.example.demo.dto;

import com.example.demo.model.Operacija;

public class OperacijaDTO {

	private Long id;
	private String datum;
	private String vreme;
	private SalaKlinikeDTO sala;
	private Double cena;
	private Long idPacijenta;
	private Boolean zavrsen;
	private Double trajanjeOperacije;
	
	public OperacijaDTO() {
		
	}
	
	public OperacijaDTO(Operacija operacija) {
		super();
		this.id = operacija.getId();
		this.datum = operacija.getDatum();
		this.vreme = operacija.getVreme();
		this.sala = new SalaKlinikeDTO(operacija.getSala());
		this.cena = operacija.getCena();
		this.idPacijenta = operacija.getIdPacijenta();
		this.zavrsen = operacija.getZavrsen();
		this.trajanjeOperacije = operacija.getTrajanjePregleda();
	}
	

	public OperacijaDTO(Long id, String datum, String vreme, SalaKlinikeDTO sala, Double cena, Long idPacijenta,
			Boolean zavrsen, Double trajanjeOperacije) {
		super();
		this.id = id;
		this.datum = datum;
		this.vreme = vreme;
		this.sala = sala;
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
	
	
}
