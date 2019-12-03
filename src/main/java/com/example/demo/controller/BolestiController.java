package com.example.demo.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BolestiDTO;
import com.example.demo.service.BolestiService;



@RestController
@RequestMapping(value = "bolesti")
@CrossOrigin(origins = "http://localhost:8081")
public class BolestiController {

	@Autowired
	private BolestiService bolestiService;
	
	@PostMapping(value = "/dodajBolest", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BolestiDTO> dodajBolest(@RequestBody BolestiDTO bolestDTO) {
		
		BolestiDTO bolestdto = new BolestiDTO();
		try {
			bolestdto = bolestiService.dodajBolest(bolestDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(bolestdto, HttpStatus.OK);
	}
}
