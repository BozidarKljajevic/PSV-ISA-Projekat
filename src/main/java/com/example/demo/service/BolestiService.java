package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BolestiDTO;
import com.example.demo.model.Bolesti;
import com.example.demo.model.Lek;
import com.example.demo.repository.BolestiRepository;


@Service
public class BolestiService {

	@Autowired
	private BolestiRepository bolestiRepository;
	
	public BolestiDTO dodajBolest(BolestiDTO bolestDTO) {
		Bolesti bolest = new Bolesti();
		
		bolest.setNaziv(bolestDTO.getNaziv());
		bolest.setSifra(bolestDTO.getSifra());
		
		bolestiRepository.save(bolest);
		
		BolestiDTO bolestdto=new BolestiDTO(bolest);
		return bolestdto;
	}
	
	public List<Bolesti> findAll() {
		return bolestiRepository.findAll();
	}
	
	public Bolesti findOne(Long id) {
		return bolestiRepository.findById(id).orElseGet(null);
	}
	public void remove(Long id) {
		bolestiRepository.deleteById(id);
	}
	
}
