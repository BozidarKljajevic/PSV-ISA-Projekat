package com.example.demo.dto;

public class DogadjajDTO {

	String start;
	String end;
	String title;
	Long idPregleda;
	
	
	public DogadjajDTO() {
	}

	public DogadjajDTO(String start, String end, String title, Long idPregleda) {
		this.start = start;
		this.end = end;
		this.title=title;
		this.idPregleda=idPregleda;
	}

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public Long getIdPregleda() {
		return idPregleda;
	}

	public void setIdPregleda(Long idPregleda) {
		this.idPregleda = idPregleda;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
}
