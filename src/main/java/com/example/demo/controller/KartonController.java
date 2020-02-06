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

import com.example.demo.dto.IzvestajOPregleduDTO;
import com.example.demo.dto.KartonDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Karton;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.service.IzvestajOPregleduService;
import com.example.demo.service.KartonService;

@RestController
@RequestMapping(value = "karton")
public class KartonController {

	@Autowired
	private KartonService kartonService;
	
	@Autowired
	private IzvestajOPregleduService izvestajOPregleduService;
	
	@GetMapping(value = "/kartonPacijenta/{id}")
	public ResponseEntity<KartonDTO> getKartonPacijnta(@PathVariable Long id) {

		return new ResponseEntity<>(new KartonDTO(kartonService.getKartonPacijenta(id)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/izvestajiKartonaPacijenta/{id}")
	public ResponseEntity<List<IzvestajOPregleduDTO>> getIzvestajeKartonaPacijnta(@PathVariable Long id) {

		Karton karton = kartonService.getKartonPacijenta(id);
		
		List<IzvestajOPregledu> izvestaji = izvestajOPregleduService.getIzvestajeKartona(karton.getId());
		List<IzvestajOPregleduDTO> izvestajiDTO = new ArrayList<IzvestajOPregleduDTO>();
		
		for (IzvestajOPregledu izvestaj : izvestaji) {
			izvestajiDTO.add(new IzvestajOPregleduDTO(izvestaj));
		}
		
		return new ResponseEntity<>(izvestajiDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/sacuvajIzmeneKartona", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<KartonDTO> izmeniPodatkeKartona(@RequestBody KartonDTO kartonDTO){
		
		try {
			kartonService.izmeni(kartonDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(kartonDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciKarton/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<KartonDTO> getLekar(@PathVariable Long id) {
		
		
		Karton karton = kartonService.findOne(id);
		
		KartonDTO kartonaDTO = new KartonDTO(karton);
		
		return new ResponseEntity<>(kartonaDTO, HttpStatus.OK);
	}
}
