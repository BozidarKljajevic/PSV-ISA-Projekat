package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.Pacijent;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "/pacijent")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;

	@GetMapping(value = "/preuzmi/{id}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> getPostojeciPacijent(@PathVariable Long id) {
		
		Pacijent pacijent = (Pacijent) pacijentService.findOne(id);
		
		if (pacijent == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		PacijentDTO pacijentDTO = new PacijentDTO(pacijent);

		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeni")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> izmeniPacijenta(@RequestBody PacijentDTO pacijentDTO) {
		
		Pacijent pacijent = (Pacijent) pacijentService.findOne(pacijentDTO.getId());
		
		if (pacijent == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		Pacijent izmenjenPacijent = pacijentService.izmeni(pacijent, pacijentDTO);

		PacijentDTO newPacijentDTO = new PacijentDTO(izmenjenPacijent);

		return new ResponseEntity<>(newPacijentDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/postojeciNeaktivanPacijent")
	public ResponseEntity<List<PacijentDTO>> getPostojeciNeaktivanPacijent() {
		
		List<Pacijent> pacijenti = pacijentService.findAll();

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent pacijent : pacijenti) {
			if(pacijent.isEnabled()==false) {
				pacijentiDTO.add(new PacijentDTO(pacijent));
			}
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/ibrisiNeaktivnogPacijenta/{id}")
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<List<PacijentDTO>> izbrisiPacijenta(@PathVariable String id) {

		Long idLong = Long.parseLong(id);
		Pacijent pac = pacijentService.findOne(idLong);

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		if (pac != null) {
			
			pacijentService.remove(pac);
			List<Pacijent> pacijenti = pacijentService.findAll();

			for (Pacijent pacijent : pacijenti) {
				if(pacijent.isEnabled()==false) {
					pacijentiDTO.add(new PacijentDTO(pacijent));
				}
			}
			
			return new ResponseEntity<>(pacijentiDTO,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(pacijentiDTO,HttpStatus.NOT_FOUND);
		}
	}
}
