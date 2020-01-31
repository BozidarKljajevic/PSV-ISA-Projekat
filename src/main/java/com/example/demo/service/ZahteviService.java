package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pregled;
import com.example.demo.model.Zahtev;
import com.example.demo.repository.ZahteviRepository;




@Service
public class ZahteviService {

	@Autowired
	private ZahteviRepository zahteviRepository;
	
	public List<Zahtev> findAll() {
		return zahteviRepository.findAll();
	}
	
	public Zahtev findOne(Long id) {
		return zahteviRepository.findById(id).orElseGet(null);
	}
	
	public void dodajRezervisanuSalu(Zahtev zahtev) {
		zahteviRepository.save(zahtev);
	}

	public List<Zahtev> getZahteveOdLekara(Long id) {
		return zahteviRepository.findByLekarId(id);
	}

	public void remove(Long id) {
		zahteviRepository.deleteById(id);
		
	}
}
