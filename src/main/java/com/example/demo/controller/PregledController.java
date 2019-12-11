package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.PregledDTO;

import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pregled;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.PregledService;

@RestController
@RequestMapping(value = "/pregled")
public class PregledController {

	@Autowired
	private PregledService pregledService;

	@PostMapping(value = "/dodajPregled", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PregledDTO> dodajPregled(@RequestBody PregledDTO pregledDTO) {

		PregledDTO pregleddto = new PregledDTO();
		try {
			pregleddto = pregledService.dodajPregled(pregledDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(pregleddto, HttpStatus.OK);
	}

	@GetMapping(value = "/preglediKlinike/{id}")
	public ResponseEntity<?> getPreglediKlinike(@PathVariable Long id) {

		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@GetMapping(value = "/predefinisaniPregledi")
	public ResponseEntity<?> getPredefinisaniPregledi() {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();
		
		for (Pregled pregled : pregledi) {
			 if(pregled.getIdPacijenta() == null) {
				 preglediDTO.add(new PregledDTO(pregled));
			 }
		}
		
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/zakzaniPregledi/{id}")
	public ResponseEntity<?> getZakazaniPregledi(@PathVariable Long id) {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();
		
		for (Pregled pregled : pregledi) {
			 if(pregled.getIdPacijenta() == id) {
				 preglediDTO.add(new PregledDTO(pregled));
			 }
		}
		
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/zakaziPregled/{idPregleda}/{idPacijenta}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> zakaziPregled(@PathVariable Long idPregleda, @PathVariable Long idPacijenta) {

		Pregled zakaziPregled = pregledService.findOne(idPregleda);
		pregledService.zakaziPregled(zakaziPregled, idPacijenta);
		
		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();
		
		for (Pregled pregled : pregledi) {
			 if(pregled.getIdPacijenta() == null) {
				 preglediDTO.add(new PregledDTO(pregled));
			 }
		}
		
		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
}
