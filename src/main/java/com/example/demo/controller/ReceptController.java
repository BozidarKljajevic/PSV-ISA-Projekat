package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.IzvestajOPregleduDTO;
import com.example.demo.dto.ReceptDTO;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Recept;
import com.example.demo.service.IzvestajOPregleduService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.ReceptService;


@RestController
@RequestMapping(value = "recept")
public class ReceptController {

	@Autowired
	private ReceptService receptService;
	
	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	@Autowired
	private IzvestajOPregleduService izvestajOPregleduService;
	
	@PostMapping(value = "/overi/{id}/{idRecepta}")
	@PreAuthorize("hasAuthority('MEDICINSKASESTRA')")
	public ResponseEntity<?> zoveriRecept(@PathVariable Long id, @PathVariable Long idRecepta){
		
		MedicinskaSestra sestra = medicinskaSestraService.findOne(id);
		Recept recept = receptService.findOne(idRecepta);
		
		receptService.sestraOverila(recept,sestra);
		
		List <IzvestajOPregledu> izvestaji = izvestajOPregleduService.findAll();
		List<ReceptDTO> receptDTO = new ArrayList<>();
		
		for(IzvestajOPregledu izvestaj : izvestaji) {
			if(sestra.getKlinika().getId() == izvestaj.getLekara().getKlinika().getId() && izvestaj.getRecept().isOveren()==false) {
				receptDTO.add(new ReceptDTO(izvestaj.getRecept()));
			}
			
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
