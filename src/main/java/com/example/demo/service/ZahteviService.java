package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Zahtev;
import com.example.demo.repository.ZahteviRepository;




@Service
public class ZahteviService {

	@Autowired
	private ZahteviRepository zahteviRepository;
	
	public List<Zahtev> findAll() {
		return zahteviRepository.findAll();
	}
}
