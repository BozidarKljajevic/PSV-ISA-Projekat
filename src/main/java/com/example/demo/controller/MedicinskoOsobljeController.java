package com.example.demo.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.MedicinskoOsobljeDTO;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.service.MedicinskoOsobljeService;

@RestController
@RequestMapping(value = "medicinskoOsoblje")
@CrossOrigin(origins = "http://localhost:8081")
public class MedicinskoOsobljeController {

	@Autowired
	 private MedicinskoOsobljeService medicinskoOsobljeService;
	
	@GetMapping(value = "/postojeceMedicinskoOsoblje")
	public ResponseEntity<MedicinskoOsobljeDTO> getPostojeceMedOsoblje() {
		
		MedicinskoOsoblje medicinskoOsoblje = medicinskoOsobljeService.findOne((long) 1);
		
		MedicinskoOsobljeDTO medicinskoOsobljeDTO = new MedicinskoOsobljeDTO(medicinskoOsoblje);
		
		return new ResponseEntity<>(medicinskoOsobljeDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/dodajMedicinskoOsoblje", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicinskoOsobljeDTO> dodajMedicinskoOsoblje(@RequestBody MedicinskoOsobljeDTO medicinskoOsobljeDTO) {
		
		MedicinskoOsobljeDTO medicinskoOsobljeDTO2 = new MedicinskoOsobljeDTO();
		try {
			medicinskoOsobljeDTO2 = medicinskoOsobljeService.dodajMedicinskoOsoblje(medicinskoOsobljeDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(medicinskoOsobljeDTO2, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniPodatkeMedicinskogOsoblja", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicinskoOsobljeDTO> izmeniPodatkeMedicinskogOsoblja(@RequestBody MedicinskoOsobljeDTO medicinskoOsobljeDTO){
		
		try {
			medicinskoOsobljeService.izmeniMedicinskoOsoblje(medicinskoOsobljeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(medicinskoOsobljeDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniPodatkeMedicinskogOsobljaAdmin", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicinskoOsobljeDTO> izmeniPodatkeMedicinskogOsobljaAdmin(@RequestBody MedicinskoOsobljeDTO medicinskoOsobljeDTO){
		
		try {
			medicinskoOsobljeService.izmeniMedicinskoOsobljeAdmin(medicinskoOsobljeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(medicinskoOsobljeDTO, HttpStatus.OK);
	}
}
