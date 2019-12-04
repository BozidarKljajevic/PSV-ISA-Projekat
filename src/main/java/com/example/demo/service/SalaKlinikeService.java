package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.model.SalaKlinike;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.SalaKlinikeRepository;

@Service
public class SalaKlinikeService {

	@Autowired
	private SalaKlinikeRepository salaKlinikeRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	public SalaKlinike findOne(Long id) {
		return salaKlinikeRepository.findById(id).orElseGet(null);
	}
	

	
	public SalaKlinikeDTO dodajSaluKlinike(SalaKlinikeDTO salaKlinikeDTO) {
		SalaKlinike sala = new SalaKlinike();
		
		sala.setNaziv(salaKlinikeDTO.getNaziv());
		sala.setBroj(salaKlinikeDTO.getBroj());
		sala.setKlinika(klinikaRepository.getOne(salaKlinikeDTO.getKlinika().getId()));
		salaKlinikeRepository.save(sala);
		
		SalaKlinikeDTO salaDTO=new SalaKlinikeDTO(sala);
		return salaDTO;
	}
	
	public void remove(Long id) {
		salaKlinikeRepository.deleteById(id);
	}
	
	public void izmeniSaluKlinike(SalaKlinikeDTO salaKlinikeDTO) {
		SalaKlinike sala = salaKlinikeRepository.findById(salaKlinikeDTO.getId()).orElse(null);
		
		if(sala == null) {
			throw new ValidationException("Sala sa zadatim id-jem nepostoji");
		}
		try {
			sala = salaKlinikeRepository.getOne(salaKlinikeDTO.getId());
			sala.setNaziv(salaKlinikeDTO.getNaziv());
			sala.setBroj(salaKlinikeDTO.getBroj());
			salaKlinikeRepository.save(sala);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Sala sa tim idijem nepostoji");
		}
	}
	
	public List<SalaKlinike> findAll() {
		return salaKlinikeRepository.findAll();
	}
}
