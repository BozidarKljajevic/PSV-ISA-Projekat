package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.PacijentRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Pacijent findOne(Long id) {
		return pacijentRepository.findById(id).orElse(null);
	}

	public Pacijent findOne(String mail) {
		return pacijentRepository.findByMail(mail);
	}
	
	public List<Pacijent> findAll() {
		return pacijentRepository.findAll();
	}

	public void remove(Pacijent pac) {
		pacijentRepository.delete(pac);
	}


	public Pacijent save(RegisterDTO pacijent) {
		Pacijent neaktivanPacijent = new Pacijent();
		
		neaktivanPacijent.setAdresa(pacijent.getAdresa());
		Authority auth = this.authorityRepository.findByName("ADMINCENTRA");
		List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
	    neaktivanPacijent.setAuthorities(auths);
		neaktivanPacijent.setBrojTelefona(pacijent.getBrojTelefona());
		neaktivanPacijent.setDrzava(pacijent.getDrzava());
		neaktivanPacijent.setEnabled(false);
		neaktivanPacijent.setGrad(pacijent.getGrad());
		neaktivanPacijent.setIme(pacijent.getIme());
		neaktivanPacijent.setMail(pacijent.getMail());
		neaktivanPacijent.setPrezime(pacijent.getPrezime());
		neaktivanPacijent.setSifra(passwordEncoder.encode(pacijent.getSifra()));
		
		this.pacijentRepository.save(neaktivanPacijent);
		
		return neaktivanPacijent;
	}

	public void aktivirajPacijenta(Pacijent exisPacijent) {
		
		exisPacijent.setEnabled(true);
		this.pacijentRepository.save(exisPacijent);
		
	}
}
