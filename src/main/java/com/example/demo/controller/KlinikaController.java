package com.example.demo.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.Klinika;
import com.example.demo.service.KlinikaService;

@RestController
@RequestMapping(value = "klinika")
@CrossOrigin(origins = "http://localhost:8081")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping(value = "/postojecaKlinika")
	public ResponseEntity<KlinikaDTO> getPostojecaKlinika() {
		
		Klinika klinika = klinikaService.findOne((long) 1);
		
		KlinikaDTO klinikaDTO = new KlinikaDTO(klinika);
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniPodatkeKlinike", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KlinikaDTO> izmeniPodatkeKlinike(@RequestBody KlinikaDTO klinikaDTO){
		
		try { 
			klinikaService.izmeniKliniku(klinikaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	
	}
}