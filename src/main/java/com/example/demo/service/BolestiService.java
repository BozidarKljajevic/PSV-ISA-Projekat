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
	
	
	
}
