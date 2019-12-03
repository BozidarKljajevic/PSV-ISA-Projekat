package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dto.BolestiDTO;
import com.example.demo.model.Bolesti;
import com.example.demo.repository.BolestiRepository;



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
	
}
