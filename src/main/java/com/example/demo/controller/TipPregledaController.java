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

import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "tipPregleda")
@CrossOrigin(origins = "http://localhost:8081")
public class TipPregledaController {

	@Autowired
	private TipPregledaService tipPregledaService;
	
	@PostMapping(value = "/dodajTipPregleda", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPregledaDTO> dodajSaluKlinike(@RequestBody TipPregledaDTO tipPregledaDTO) {
		
		TipPregledaDTO tipDTO = new TipPregledaDTO();
		try {
			tipDTO = tipPregledaService.dodajTipPregleda(tipPregledaDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(tipDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniPodatkeTipaPregleda", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPregledaDTO> izmeniPodatkeTipaPregleda(@RequestBody TipPregledaDTO tipPregledaDTO){
		
		try {
			tipPregledaService.izmeniTipPregleda(tipPregledaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(tipPregledaDTO, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/izbrisiTipPregleda/{id}")
	public ResponseEntity<Void> deleteSalu(@PathVariable Long id) {

		TipPregleda tipPregleda = tipPregledaService.findOne(id);
		List<TipPregledaDTO> tipDTO = new ArrayList<>();
		if (tipPregleda != null) {
			tipPregledaService.remove(id);
			List<TipPregleda> tipPre = tipPregledaService.findAll();

			
			for (TipPregleda tip : tipPre) {
				tipDTO.add(new TipPregledaDTO(tip));
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value = "/sviTipoviPregleda")
	public ResponseEntity<List<TipPregledaDTO>> getSveTipovePregleda() {
		
		List<TipPregleda> tipoviPregleda = tipPregledaService.findAll();

		
		List<TipPregledaDTO> tipDTO = new ArrayList<>();
		for (TipPregleda tip : tipoviPregleda) {
			tipDTO.add(new TipPregledaDTO(tip));
		}

		return new ResponseEntity<>(tipDTO, HttpStatus.OK);
	}
}
