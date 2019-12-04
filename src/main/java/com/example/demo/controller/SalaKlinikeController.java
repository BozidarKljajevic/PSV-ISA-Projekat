package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MedicinskoOsobljeDTO;
import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.model.SalaKlinike;
import com.example.demo.service.SalaKlinikeService;

@RestController
@RequestMapping(value = "salaKLinike")
@CrossOrigin(origins = "http://localhost:8081")
public class SalaKlinikeController {

	@Autowired
	private SalaKlinikeService salaKlinikeService;
	
	@PostMapping(value = "/dodajSaluKlinike", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SalaKlinikeDTO> dodajSaluKlinike(@RequestBody SalaKlinikeDTO salaDTO) {
		
		SalaKlinikeDTO salaDto = new SalaKlinikeDTO();
		try {
			salaDto = salaKlinikeService.dodajSaluKlinike(salaDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(salaDto, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniPodatkeSaleKlinike", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SalaKlinikeDTO> izmeniPodatkeSaleKlinike(@RequestBody SalaKlinikeDTO salaKlinikeDTO){
		
		try {
			salaKlinikeService.izmeniSaluKlinike(salaKlinikeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(salaKlinikeDTO, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/izbrisiSaluKlinike/{id}")
	public ResponseEntity<Void> deleteSalu(@PathVariable Long id) {

		SalaKlinike salaKlinike = salaKlinikeService.findOne(id);
		List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
		if (salaKlinike != null) {
			salaKlinikeService.remove(id);
			List<SalaKlinike> salaKli = salaKlinikeService.findAll();

			
			for (SalaKlinike sala : salaKli) {
				salaDTO.add(new SalaKlinikeDTO(sala));
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/sveSale")
	public ResponseEntity<List<SalaKlinikeDTO>> getSveSale() {
		
		List<SalaKlinike> sale = salaKlinikeService.findAll();

		
		List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
		for (SalaKlinike s : sale) {
			salaDTO.add(new SalaKlinikeDTO(s));
		}

		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}
}
