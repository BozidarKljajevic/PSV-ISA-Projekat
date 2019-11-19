package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Pacijent;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "pacijent")
@CrossOrigin(origins = "http://localhost:8081")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	
	@GetMapping(value = "/postojeciPacijent")
	public ResponseEntity<PacijentDTO> getPostojeciPacijent() {
		
		Pacijent pacijent = pacijentService.findOne((long) 1);
		
		PacijentDTO pacijentDTO = new PacijentDTO(pacijent);
		
		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}
}
