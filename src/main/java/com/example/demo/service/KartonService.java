package com.example.demo.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.KartonDTO;
import com.example.demo.model.Karton;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.repository.KartonRepository;

@Service
public class KartonService {

	@Autowired
	private KartonRepository kartonRepository;

	public Karton findOne(Long id) {
		return kartonRepository.findById(id).orElse(null);
	}
	
	public Karton getKartonPacijenta(Long id) {
		return kartonRepository.findByPacijentId(id);
	}

	public void izmeni(KartonDTO kartonDTO) {
		Karton karton = kartonRepository.findById(kartonDTO.getId()).orElse(null);
		
		if(karton == null) {
			throw new ValidationException("Karton sa zadatim id-jem nepostoji");
		}
		try {
			karton = kartonRepository.getOne(kartonDTO.getId());
			karton.setVisina(kartonDTO.getVisina());
			karton.setTezina(kartonDTO.getTezina());
			karton.setKrvnaGrupa(kartonDTO.getKrvnaGrupa());
			karton.setDioptrija(kartonDTO.getDioptrija());
			kartonRepository.save(karton);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Karton sa tim idijem nepostoji");
		}
	}
}
