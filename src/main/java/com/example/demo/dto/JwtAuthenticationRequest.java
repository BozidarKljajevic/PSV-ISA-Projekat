package com.example.demo.dto;

public class JwtAuthenticationRequest {

	private String mail;
    private String sifra;
    
    public JwtAuthenticationRequest() {
        super();
    }
    
    public JwtAuthenticationRequest(String mail, String sifra) {
        this.mail = mail;
        this.sifra = sifra;
    }

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
    
}
