package com.example.demo.controller;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.PregledDTO;

import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pregled;
import com.example.demo.model.SalaKlinike;
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
		 boolean flag = false;
		 
		 String[] RadnoOd = pregledDTO.getLekar().getRadnoOd().split(":");
		 double radnoP = Double.parseDouble(RadnoOd[0]);
		 String[] RadnoDo = pregledDTO.getLekar().getRadnoDo().split(":");
		 double radnoK = Double.parseDouble(RadnoDo[0]);
		 
		 String[] vrP = pregledDTO.getVreme().split(":");
			double satP = Double.parseDouble(vrP[0]);
			double minP = Double.parseDouble(vrP[1]);
			double pocetakPregledaP = 0;
			if(minP%60 != 0) {
				 pocetakPregledaP = satP+ 0.5;
			}
			else {
				 pocetakPregledaP = satP;
			}
			double trajanjeMinP = pregledDTO.getTrajanjePregleda()*60;
			double trajanjeMinOstatakP = trajanjeMinP%60;
			double trajanjeSatP = trajanjeMinP/60;
			double krajPregledaSatP = satP+trajanjeSatP;
			double krajPregledaMinP = minP+trajanjeMinOstatakP;
			
			if(krajPregledaMinP%60 != 0) {
				krajPregledaSatP++;
				krajPregledaMinP = 0;
			} else {
				krajPregledaSatP+= 0.5;
			}
		 
		
			
			List<Pregled> pregledi = pregledService.findAll();
			for(Pregled pregled : pregledi) {
				if(pregledDTO.getDatum().equals(pregled.getDatum()) && pregled.getIdPacijenta() == null && pregled.getSala().getId() == pregledDTO.getSala().getId()) {
					
					String[] vr = pregled.getVreme().split(":");
					double sat = Double.parseDouble(vr[0]);
					double min = Double.parseDouble(vr[1]);
					double pocetakPregleda = 0;
					if(min%60 != 0) {
						 pocetakPregleda = sat+ 0.5;
					}
					else {
						 pocetakPregleda = sat;
					}
					double trajanjeMin = pregledDTO.getTrajanjePregleda()*60;
					double trajanjeMinOstatak = trajanjeMin%60;
					double trajanjeSat = trajanjeMin/60;
					double krajPregledaSat = sat+trajanjeSat;
					double krajPregledaMin = min+trajanjeMinOstatak;
					if(krajPregledaMin%60 != 0) {
						krajPregledaSat++;
						krajPregledaMin = 0;
					}
					else {
						krajPregledaSat+= 0.5;
					}
					
					
					if(pocetakPregledaP >= pocetakPregleda && pocetakPregledaP <= krajPregledaSat) {
						flag = true;
					}
					else if(pocetakPregledaP <= pocetakPregleda && krajPregledaSatP >= pocetakPregleda) {
						flag = true;
					}
					
				}
				else if(pregledDTO.getDatum().equals(pregled.getDatum()) && pregled.getIdPacijenta() == null && pregled.getSala().getId() != pregledDTO.getSala().getId() && pregled.getLekar().getId() == pregledDTO.getLekar().getId()) {
					
					String[] vr = pregled.getVreme().split(":");
					double sat = Double.parseDouble(vr[0]);
					double min = Double.parseDouble(vr[1]);
					double pocetakPregleda = 0;
					if(min%60 != 0) {
						 pocetakPregleda = sat+ 0.5;
					}
					else {
						 pocetakPregleda = sat;
					}
					double trajanjeMin = pregledDTO.getTrajanjePregleda()*60;
					double trajanjeMinOstatak = trajanjeMin%60;
					double trajanjeSat = trajanjeMin/60;
					double krajPregledaSat = sat+trajanjeSat;
					double krajPregledaMin = min+trajanjeMinOstatak;
					if(krajPregledaMin%60 != 0) {
						krajPregledaSat++;
						krajPregledaMin = 0;
					}
					else {
						krajPregledaSat+= 0.5;
					}
					
					
					if(pocetakPregledaP >= pocetakPregleda && pocetakPregledaP <= krajPregledaSat) {
						flag = true;
					}
					else if(pocetakPregledaP <= pocetakPregleda && krajPregledaSatP >= pocetakPregleda) {
						flag = true;
					}
					
				} else if(pocetakPregledaP < radnoP || pocetakPregledaP > radnoK) {
					flag=true;
				} else if(pocetakPregledaP > radnoP && krajPregledaSatP > radnoK) {
					flag = true;
				}
			}
			
			if(flag == false) {
				pregleddto = pregledService.dodajPregled(pregledDTO);
				
			}
			
			
			
		if(flag == true) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<>(pregleddto, HttpStatus.OK);
	}
	
	
	
}
