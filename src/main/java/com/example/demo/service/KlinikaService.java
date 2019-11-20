package com.example.demo.service;

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