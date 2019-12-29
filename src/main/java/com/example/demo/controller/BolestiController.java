package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BolestiDTO;
import com.example.demo.model.Bolesti;
import com.example.demo.service.BolestiService;



@RestController
@RequestMapping(value = "bolesti")
public class BolestiController {

	@Autowired
	private BolestiService bolestiService;
	
	@PostMapping(value = "/dodajBolest", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<BolestiDTO> dodajBolest(@RequestBody BolestiDTO bolestDTO) {
		List<Bolesti> bolesti = bolestiService.findAll();
		for (Bolesti bolest : bolesti) {
			if (bolest.getSifra() == bolestDTO.getSifra()) {
				
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		BolestiDTO bolestdto = new BolestiDTO();
		try {
			bolestdto = bolestiService.dodajBolest(bolestDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(bolestdto, HttpStatus.OK);
			
	}
	
	@GetMapping(value = "/postojeceBolesti")
	
	public ResponseEntity<List<BolestiDTO>> getPostojeceBolesti() {
		
		List<Bolesti> bolesti = bolestiService.findAll();

		List<BolestiDTO> bolestDTO = new ArrayList<>();
		for (Bolesti bolest : bolesti) {
			bolestDTO.add(new BolestiDTO(bolest));
		}

		return new ResponseEntity<>(bolestDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/izbrisiBolest/{id}")
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<List<BolestiDTO>> izbrisiBolest(@PathVariable Long id) {

		Bolesti bolest = bolestiService.findOne(id);

		List<BolestiDTO> bolestDTO = new ArrayList<>();
		if (bolest != null) {
			bolestiService.remove(id);
			List<Bolesti> bolesti = bolestiService.findAll();

			
			for (Bolesti bolest1 : bolesti) {
				bolestDTO.add(new BolestiDTO(bolest1));
			}
			
			return new ResponseEntity<>(bolestDTO,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(bolestDTO,HttpStatus.NOT_FOUND);
		}
	}

	
}
