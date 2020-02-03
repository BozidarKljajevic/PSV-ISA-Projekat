package com.example.demo.dto;

import com.example.demo.model.Zahtev;

public class ZahtevDTO {

	private Long id;
	private String datum;
	private String vreme;
	private SalaKlinikeDTO sala;
	private TipPregledaDTO tipPregleda;
	private LekarDTO lekar;
	private LekarDTO lekar1;
	private LekarDTO lekar2;
	private Double cena;
	private Long idPacijenta;
	private Double trajanjePregleda;
	private boolean izbor;
	
	public ZahtevDTO() {

	}
	
	public ZahtevDTO(Zahtev zahtev) {
		this.id = zahtev.getId();
		this.datum = zahtev.getDatum();
		this.vreme = zahtev.getVreme();
		if (zahtev.getSala() != null) {
			this.sala = new SalaKlinikeDTO(zahtev.getSala());
		}else {
			this.sala = null;
		}
		if (zahtev.getLekar1() != null) {
			this.lekar1 = new LekarDTO(zahtev.getLekar1());
		}else {
			this.lekar1 = null;
		}
		if (zahtev.getLekar2() != null) {
			this.lekar2 = new LekarDTO(zahtev.getLekar2());
		}else {
			this.lekar2 = null;
		}
		this.tipPregleda = new TipPregledaDTO(zahtev.getTipPregleda());
		this.lekar = new LekarDTO(zahtev.getLekar());
		this.cena = zahtev.getCena();
		this.idPacijenta = zahtev.getIdPacijenta();
		this.trajanjePregleda = zahtev.getTrajanjePregleda();
		this.izbor = zahtev.isIzbor();
	}
	
	public ZahtevDTO(Long id, String datum, String vreme, SalaKlinikeDTO sala, TipPregledaDTO tipPregleda,
			LekarDTO lekar,LekarDTO lekar1, LekarDTO lekar2, Double cena, Long idPacijenta, Double trajanjePregleda,boolean izbor) {
		super();
		this.id = id;
		this.datum = datum;
		this.vreme = vreme;
		this.sala = sala;
		this.tipPregleda = tipPregleda;
		this.lekar = lekar;
		this.lekar1=lekar1;
		this.lekar2=lekar2;
		this.cena = cena;
		this.idPacijenta = idPacijenta;
		this.trajanjePregleda = trajanjePregleda;
		this.izbor = izbor;
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

	public boolean isIzbor() {
		return izbor;
	}

	public void setIzbor(boolean izbor) {
		this.izbor = izbor;
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

	
	
	public LekarDTO getLekar1() {
		return lekar1;
	}

	public void setLekar1(LekarDTO lekar1) {
		this.lekar1 = lekar1;
	}

	public LekarDTO getLekar2() {
		return lekar2;
	}

	public void setLekar2(LekarDTO lekar2) {
		this.lekar2 = lekar2;
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

	public Double getTrajanjePregleda() {
		return trajanjePregleda;
	}

	public void setTrajanjePregleda(Double trajanjePregleda) {
		this.trajanjePregleda = trajanjePregleda;
	}
}
