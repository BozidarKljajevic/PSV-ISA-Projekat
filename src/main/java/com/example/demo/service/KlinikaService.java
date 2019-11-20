package com.example.demo.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.dto.KlinikaDTO;

import com.example.demo.model.Klinika;
import com.example.demo.repository.KlinikaRepository;

@Service
public class KlinikaService {

	@Autowired
	private KlinikaRepository klinikaRepository;
	
	public Klinika findOne(Long id) {
		return klinikaRepository.findById(id).orElseGet(null);
	}
	
	public void izmeniKliniku(KlinikaDTO klinikaDTO) {
		Klinika klinika = klinikaRepository.findById(klinikaDTO.getId()).orElse(null);
		
		if(klinika == null) {
			throw new ValidationException("Klinika sa zadatim id-jem nepostoji");
		}
		try {
			klinika = klinikaRepository.getOne(klinikaDTO.getId());
			klinika.setNaziv(klinikaDTO.getNaziv());
			klinika.setAdresa(klinikaDTO.getAdresa());
			klinika.setBrojTelefona(klinikaDTO.getBrojTelefona());
			klinika.setGrad(klinikaDTO.getGrad());
			klinika.setDrzava(klinikaDTO.getDrzava());
			klinikaRepository.save(klinika);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Klinika sa tim idijem nepostoji");
		}
		
	}
}