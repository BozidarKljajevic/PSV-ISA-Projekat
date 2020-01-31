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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "OPERACIJA")
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
public class Operacija {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "pregled_id_seq", sequenceName = "pregled_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pregled_id_seq")
	private Long id;
	
	@Column(name = "datum", nullable = false, unique = false)
	private String datum;
	
	@Column(name = "vreme", nullable = false, unique = false)
	private String vreme;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "sala_klinike_id")
	private SalaKlinike sala;
	
	@OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Lekar> lekarKlinike = new HashSet<Lekar>();
	
	@Column(name = "cena", nullable = false, unique = false)
	private Double cena;
	
	@Column(name = "id_pacijenta", nullable = true, unique = false)
	private Long idPacijenta;
	
	@Column(name = "zavrsen", nullable = true, unique = false)
	private Boolean zavrsen;
	
	@Column(name = "trajanje_operacije", nullable = false, unique = false)
	private Double trajanjeOperacije;
	
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

	public Set<Lekar> getLekarKlinike() {
		return lekarKlinike;
	}

	public void setLekarKlinike(Set<Lekar> lekarKlinike) {
		this.lekarKlinike = lekarKlinike;
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
		return trajanjeOperacije;
	}

	public void setTrajanjePregleda(Double trajanjeOperacije) {
		this.trajanjeOperacije = trajanjeOperacije;
	}
}
