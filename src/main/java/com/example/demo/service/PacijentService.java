package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.Pacijent;
import com.example.demo.model.User;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.UserRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User findOne(Long id) {
		return pacijentRepository.findById(id).orElse(null);
	}

	public User findOne(String mail) {
		return pacijentRepository.findByMail(mail);
	}

	public Pacijent save(PacijentDTO pacijent) {
		Pacijent neaktivanPacijent = new Pacijent();
		
		neaktivanPacijent.setAdresa(pacijent.getAdresa());
		Authority auth = this.authorityRepository.findByName("PACIJENT");
		List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
	    neaktivanPacijent.setAuthorities(auths);
		neaktivanPacijent.setBrojTelefona(pacijent.getBrojTelefona());
		neaktivanPacijent.setDrzava(pacijent.getDrzava());
		neaktivanPacijent.setEnabled(false);
		neaktivanPacijent.setGrad(pacijent.getGrad());
		neaktivanPacijent.setIme(pacijent.getIme());
		neaktivanPacijent.setMail("trebalobidaradi@kkk.com");
		neaktivanPacijent.setPrezime(pacijent.getPrezime());
		neaktivanPacijent.setSifra(passwordEncoder.encode("123"));
		
		this.pacijentRepository.save(neaktivanPacijent);
		
		return neaktivanPacijent;
	}
}
