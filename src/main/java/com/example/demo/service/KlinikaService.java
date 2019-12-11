package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.Klinika;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.KlinikaRepository;

@Service
public class KlinikaService {

	@Autowired
	private KlinikaRepository klinikaRepository;
	
	
	
	public Klinika findOne(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}

	
	public List<Klinika> findAll() {
		return klinikaRepository.findAll();
	}

	public KlinikaDTO dodajKliniku(KlinikaDTO klinikaDTO) {
		Klinika klinika = new Klinika();
		
		
		klinika.setNaziv(klinikaDTO.getNaziv());
		klinika.setOpis(klinikaDTO.getOpis());
		klinika.setAdresa(klinikaDTO.getAdresa());
		klinika.setGrad(klinikaDTO.getGrad());
		klinika.setDrzava(klinikaDTO.getDrzava());
		klinika.setBrojTelefona(klinikaDTO.getBrojTelefona());
		klinika.setOcena(klinikaDTO.getOcena());
		klinikaRepository.save(klinika);
		
		KlinikaDTO klinikadto=new KlinikaDTO(klinika);
		return klinikadto;
	}

}
