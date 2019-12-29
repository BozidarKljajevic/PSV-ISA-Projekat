package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Zahtev{

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "zahtev_id_seq", sequenceName = "zahtev_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zahtev_id_seq")
	private Long id;
	
	@Column(name = "datum")
	private String datum;
	
	@Column(name = "vreme")
	private String vreme;
	
	@Column(name = "izbor")
	private boolean izbor;
	
	public boolean isIzbor() {
		return izbor;
	}

	public void setIzbor(boolean izbor) {
		this.izbor = izbor;
	}

	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "sala_klinike_id")
	private SalaKlinike sala;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "tip_pregleda_id")
	private TipPregleda tipPregleda;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "lekar_id")
	private Lekar lekar;
	
	@Column(name = "cena")
	private Double cena;
	
	@Column(name = "id_pacijenta")
	private Long idPacijenta;
	
	@Column(name = "trajanje_pregleda")
	private Double trajanjePregleda;

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

	public SalaKlinike getSala() {
		return sala;
	}

	public void setSala(SalaKlinike sala) {
		this.sala = sala;
	}

	public TipPregleda getTipPregleda() {
		return tipPregleda;
	}

	public void setTipPregleda(TipPregleda tipPregleda) {
		this.tipPregleda = tipPregleda;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
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

	public Double getTrajanjePregleda() {
		return trajanjePregleda;
	}

	public void setTrajanjePregleda(Double trajanjePregleda) {
		this.trajanjePregleda = trajanjePregleda;
	}
}
