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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.SalaKlinikeService;

@RestController
@RequestMapping(value = "salaKLinike")
public class SalaKlinikeController {

	@Autowired
	private SalaKlinikeService salaKlinikeService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@PostMapping(value = "/dodajSaluKlinike/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SalaKlinikeDTO> dodajSaluKlinike(@RequestBody SalaKlinikeDTO salaDTO,@PathVariable String id) {
		
		SalaKlinikeDTO salaDto = new SalaKlinikeDTO();
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<SalaKlinike> sale = salaKlinikeService.findAll();
		for(SalaKlinike s : sale) {
			if(s.getNaziv().equals(salaDTO.getNaziv()) && (s.getKlinika().getId() == adm.getKlinika().getId())) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(s.getBroj().equals(salaDTO.getBroj()) && (s.getKlinika().getId() == adm.getKlinika().getId())) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		try {
			salaDto = salaKlinikeService.dodajSaluKlinike(salaDTO,idLong);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(salaDto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeniPodatkeSaleKlinike", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SalaKlinikeDTO> izmeniPodatkeSaleKlinike(@RequestBody SalaKlinikeDTO salaKlinikeDTO){
		
		try {
			salaKlinikeService.izmeniSaluKlinike(salaKlinikeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(salaKlinikeDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/izbrisiSaluKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteSalu(@PathVariable Long id) {

		SalaKlinike salaKlinike = salaKlinikeService.findOne(id);
		List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
		if (salaKlinike != null) {
			salaKlinikeService.remove(id);
			List<SalaKlinike> salaKli = salaKlinikeService.findAll();

			
			for (SalaKlinike sala : salaKli) {
				salaDTO.add(new SalaKlinikeDTO(sala));
			}
			return new ResponseEntity<>(salaDTO,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/SaleKlinike/{id}")
	public ResponseEntity<List<SalaKlinikeDTO>> getSveSale(@PathVariable String id) {
		
		List<SalaKlinike> sale = salaKlinikeService.findAll();

		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		
		List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
		for (SalaKlinike s : sale) {
			 if(s.getKlinika().getId() == adm.getKlinika().getId()) {
			salaDTO.add(new SalaKlinikeDTO(s));
			 }
		}

		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}
	
}