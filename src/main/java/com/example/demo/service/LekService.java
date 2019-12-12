package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LekDTO;
import com.example.demo.model.Lek;
import com.example.demo.repository.LekRepository;


@Service
public class LekService {

	@Autowired
	private LekRepository lekRepository;
	
	public LekDTO dodajLek(LekDTO lekDTO) {
		Lek lek = new Lek();
		
		lek.setNaziv(lekDTO.getNaziv());
		lek.setSifra(lekDTO.getSifra());
		
		lekRepository.save(lek);
		
		LekDTO lekdto=new LekDTO(lek);
		return lekdto;
	}
	
	public List<Lek> findAll() {
		return lekRepository.findAll();
	}
	
	public Lek findOne(Long id) {
		return lekRepository.findById(id).orElseGet(null);
	}
	
	public void remove(Long id) {
		lekRepository.deleteById(id);
	}

}
