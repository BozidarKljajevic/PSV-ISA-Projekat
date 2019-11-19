package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pacijent;
import com.example.demo.repository.PacijentRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;
	
	public Pacijent findOne(Long id) {
		return pacijentRepository.findById(id).orElseGet(null);
	}
}
