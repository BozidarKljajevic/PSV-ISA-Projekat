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
public class Godisnji {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "godisnji_id_seq", sequenceName = "godisnji_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "godisnji_id_seq")
	private Long id;
	
	@Column(name = "datumOd", nullable = false, unique = false)
	private String datumOd;
	
	@Column(name = "datumDo", nullable = false, unique = false)
	private String datumDo;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "lekar_id")
	private Lekar lekar;
	
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name = "sestra_id")
	private MedicinskaSestra sestra;
	
	@Column(name = "odobren", nullable = true, unique = false)
	private Boolean odobren;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(String datumOd) {
		this.datumOd = datumOd;
	}

	public String getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(String datumDo) {
		this.datumDo = datumDo;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}

	public MedicinskaSestra getSestra() {
		return sestra;
	}

	public void setSestra(MedicinskaSestra sestra) {
		this.sestra = sestra;
	}

	public Boolean getOdobren() {
		return odobren;
	}

	public void setOdobren(Boolean odobren) {
		this.odobren = odobren;
	}
	
	
}
