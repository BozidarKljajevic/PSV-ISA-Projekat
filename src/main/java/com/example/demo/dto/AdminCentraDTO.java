package com.example.demo.dto;

import com.example.demo.model.AdminCentra;

public class AdminCentraDTO {

	private String mail;
	
	public AdminCentraDTO() {
		
	}

	public AdminCentraDTO(String mail) {
		super();
		this.mail = mail;
	}
	
	public AdminCentraDTO(AdminCentra adminC) {
		this.mail = adminC.getMail();
		
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
