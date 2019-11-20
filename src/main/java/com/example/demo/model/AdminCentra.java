package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdminCentra {

	
	@Id
	@Column(name = "mail", nullable = false, unique = true)
	private String mail;
	
	@Column(name = "lozinka", nullable = false)
	private String lozinka;
	
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setSifra(String lozinka) {
		this.lozinka = lozinka;
	}

}
