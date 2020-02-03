package com.example.demo.dto;

import com.example.demo.model.Godisnji;

public class GodisnjiDTO {
	

	private Long id;
	private String datumOd;
	private String datumDo;
	private LekarDTO lekar;
	private MedicinskaSestraDTO sestra;
	private boolean odobren;

	public GodisnjiDTO() {
		
	}
	
	public GodisnjiDTO(Godisnji godisnji) {
		this.id = godisnji.getId();
		this.datumOd =godisnji.getDatumOd();
		this.datumDo = godisnji.getDatumDo();
		if(godisnji.getLekar() != null) {
			this.lekar = new LekarDTO(godisnji.getLekar());
		} else {
			this.lekar = null;
		}
		if(godisnji.getSestra() != null) {
			this.sestra = new MedicinskaSestraDTO(godisnji.getSestra());
		} else {
			this.sestra = null;
		}
		this.odobren = godisnji.getOdobren();
	}
	
	public GodisnjiDTO(Long id, String datumOd, String datumDo, LekarDTO lekar, MedicinskaSestraDTO sestra,
			boolean odobren) {
		super();
		this.id = id;
		this.datumOd = datumOd;
		this.datumDo = datumDo;
		this.lekar = lekar;
		this.sestra = sestra;
		this.odobren = odobren;
	}

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

	public LekarDTO getLekar() {
		return lekar;
	}

	public void setLekar(LekarDTO lekar) {
		this.lekar = lekar;
	}

	public MedicinskaSestraDTO getSestra() {
		return sestra;
	}

	public void setSestra(MedicinskaSestraDTO sestra) {
		this.sestra = sestra;
	}

	public boolean isOdobren() {
		return odobren;
	}

	public void setOdobren(boolean odobren) {
		this.odobren = odobren;
	}
	
	
	
}
