package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.Klinika;
import com.example.demo.service.KlinikaService;

@RestController
@RequestMapping(value = "klinika")
@CrossOrigin(origins = "http://localhost:8080")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@GetMapping(value = "/postojeciPacijent")
	public ResponseEntity<KlinikaDTO> getPostojecaKlinika() {
		
		Klinika klinika = klinikaService.findOne((long) 1);
		
		KlinikaDTO klinikaDTO = new KlinikaDTO(klinika);
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
}