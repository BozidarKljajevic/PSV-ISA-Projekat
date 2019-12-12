package com.example.demo.dto;

import com.example.demo.model.Lekar;
import com.example.demo.model.Pregled;

public class PregledDTO {

	private Long id;
	private String datum;
	private String vreme;
	private SalaKlinikeDTO sala;
	private TipPregledaDTO tipPregleda;
	private LekarDTO lekar;
	private Double cena;
	private Long idPacijenta;
	private Boolean zavrsen;
	private Double trajanjePregleda;
	
	public PregledDTO() {

	}
	
	public PregledDTO(Pregled pregled) {
		this.id = pregled.getId();
		this.datum = pregled.getDatum();
		this.vreme = pregled.getVreme();
		this.sala = new SalaKlinikeDTO(pregled.getSala());
		this.tipPregleda = new TipPregledaDTO(pregled.getTipPregleda());
		this.lekar = new LekarDTO(pregled.getLekar());
		this.cena = pregled.getCena();
		this.idPacijenta = pregled.getIdPacijenta();
		this.zavrsen = pregled.getZavrsen();
		this.trajanjePregleda = pregled.getTrajanjePregleda();
	}

	public PregledDTO(Long id, String datum, String vreme, SalaKlinikeDTO sala, TipPregledaDTO tipPregleda,
			LekarDTO lekar, Double cena, Long idPacijenta, Boolean zavrsen, Double trajanjePregleda) {
		super();
		this.id = id;
		this.datum = datum;
		this.vreme = vreme;
		this.sala = sala;
		this.tipPregleda = tipPregleda;
		this.lekar = lekar;
		this.cena = cena;
		this.idPacijenta = idPacijenta;
		this.zavrsen = zavrsen;
		this.trajanjePregleda = trajanjePregleda;
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

	public TipPregledaDTO getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregledaDTO tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
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

	public Double getTrajanjePregleda() {
		return trajanjePregleda;
	}

	public void setTrajanjePregleda(Double trajanjePregleda) {
		this.trajanjePregleda = trajanjePregleda;
	}
}
