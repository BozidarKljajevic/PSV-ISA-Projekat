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

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.NeaktivanPacijent;
import com.example.demo.model.Pacijent;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "pacijent")
@CrossOrigin(origins = "http://localhost:8081")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	
	@GetMapping(value = "/postojeciPacijent/{mail}")
	public ResponseEntity<PacijentDTO> getPostojeciPacijent(@PathVariable String mail) {
		
		Pacijent pacijent = pacijentService.findOne(mail);
		
		PacijentDTO pacijentDTO = new PacijentDTO(pacijent);
		
		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/postojeciNeaktivanPacijent")
	public ResponseEntity<List<PacijentDTO>> getPostojeciNeaktivanPacijent() {
		
		List<NeaktivanPacijent> pacijenti = pacijentService.findAll();

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (NeaktivanPacijent pacijent : pacijenti) {
			pacijentiDTO.add(new PacijentDTO(pacijent));
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/promeniPodatkePacijenta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PacijentDTO> promeniPodatkePacijenta(@RequestBody PacijentDTO pacijentDTO) {
		
		try {
			pacijentService.izmeniPacijenta(pacijentDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/aktivirajPacijenta/{mail}")
	public ResponseEntity<?> aktivirajPacijenta(@PathVariable String mail) {
		
		NeaktivanPacijent neaktivanPacijent = pacijentService.findOneN(mail);
		
		if (neaktivanPacijent == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		pacijentService.aktivirajPacijenta(neaktivanPacijent);
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/ibrisiNeaktivnogPacijenta/{id}")
	public ResponseEntity<List<PacijentDTO>> izbrisiPacijenta(@PathVariable Long id) {

		NeaktivanPacijent pac = pacijentService.findOneN(id);

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		if (pac != null) {
			pacijentService.remove(id);
			List<NeaktivanPacijent> pacijenti = pacijentService.findAll();

			
			for (NeaktivanPacijent pacijent : pacijenti) {
				pacijentiDTO.add(new PacijentDTO(pacijent));
			}
			
			return new ResponseEntity<>(pacijentiDTO,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(pacijentiDTO,HttpStatus.NOT_FOUND);
		}
	}
}
