package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Operacija;
import com.example.demo.model.Pregled;
import com.example.demo.repository.OperacijRepository;



@Service
public class OperacijaService {

	@Autowired
	private OperacijRepository operacijaRepository;
	
	public Operacija findOne(Long id) {
		return operacijaRepository.findById(id).orElseGet(null);
	}

	public List<Operacija> findAll() {
		return operacijaRepository.findAll();
	}
}
