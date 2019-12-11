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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Klinika;

import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.KlinikaService;

@RestController
@RequestMapping(value = "klinika")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@GetMapping(value = "/postojecaKlinika")
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<KlinikaDTO> getPostojecaKlinika() {
		
		Klinika klinika = klinikaService.findOne((long) 1);
		
		KlinikaDTO klinikaDTO = new KlinikaDTO(klinika);
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/sveKlinike")
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<List<KlinikaDTO>> getSveKlinike() {
		
		List<Klinika> klinike = klinikaService.findAll();

		// convert courses to DTOs
		List<KlinikaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika klinika : klinike) {
			klinikeDTO.add(new KlinikaDTO(klinika));
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajKliniku", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<KlinikaDTO> dodajKliniku(@RequestBody KlinikaDTO klinikaDTO) {
		
		KlinikaDTO klinikadto = new KlinikaDTO();
		try {
			klinikadto = klinikaService.dodajKliniku(klinikaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(klinikadto, HttpStatus.OK);
	}

	
	@GetMapping(value = "/postojecaKlinika/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<KlinikaDTO> getPostojecaKlinika(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Klinika klinika = klinikaService.findOne(adminKlinikeService.findOne(idLong).getKlinika().getId());
		
		KlinikaDTO klinikaDTO = new KlinikaDTO(klinika);
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeniPodatkeKlinike", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<KlinikaDTO> izmeniPodatkeKlinike(@RequestBody KlinikaDTO klinikaDTO){
		
		try { 
			klinikaService.izmeniKliniku(klinikaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
}
