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

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.MedicinskoOsobljeDTO;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.service.MedicinskoOsobljeService;

@RestController
@RequestMapping(value = "medicinskoOsoblje")
@CrossOrigin(origins = "http://localhost:8081")
public class MedicinskoOsobljeController {

	@Autowired
	 private MedicinskoOsobljeService medicinskoOsobljeService;
	
	@GetMapping(value = "/postojeceMedicinskoOsoblje")
	public ResponseEntity<MedicinskoOsobljeDTO> getPostojeceMedOsoblje() {
		
		MedicinskoOsoblje medicinskoOsoblje = medicinskoOsobljeService.findOne((long) 1);
		
		MedicinskoOsobljeDTO medicinskoOsobljeDTO = new MedicinskoOsobljeDTO(medicinskoOsoblje);
		
		return new ResponseEntity<>(medicinskoOsobljeDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/postojeciSviLekari")
	public ResponseEntity<List<MedicinskoOsobljeDTO>> getPostojeciNeaktivanPacijent() {
		
		List<MedicinskoOsoblje> MedOsoblje = medicinskoOsobljeService.findAll();

		List<MedicinskoOsobljeDTO> medOsobljeDTO = new ArrayList<>();
		for (MedicinskoOsoblje medO : MedOsoblje) {
			medOsobljeDTO.add(new MedicinskoOsobljeDTO(medO));
		}

		return new ResponseEntity<>(medOsobljeDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajMedicinskoOsoblje", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicinskoOsobljeDTO> dodajMedicinskoOsoblje(@RequestBody MedicinskoOsobljeDTO medicinskoOsobljeDTO) {
		
		MedicinskoOsobljeDTO medicinskoOsobljeDTO2 = new MedicinskoOsobljeDTO();
		try {
			medicinskoOsobljeDTO2 = medicinskoOsobljeService.dodajMedicinskoOsoblje(medicinskoOsobljeDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(medicinskoOsobljeDTO2, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniPodatkeMedicinskogOsoblja", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicinskoOsobljeDTO> izmeniPodatkeMedicinskogOsoblja(@RequestBody MedicinskoOsobljeDTO medicinskoOsobljeDTO){
		
		try {
			medicinskoOsobljeService.izmeniMedicinskoOsoblje(medicinskoOsobljeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(medicinskoOsobljeDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniPodatkeMedicinskogOsobljaAdmin", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicinskoOsobljeDTO> izmeniPodatkeMedicinskogOsobljaAdmin(@RequestBody MedicinskoOsobljeDTO medicinskoOsobljeDTO){
		
		try {
			medicinskoOsobljeService.izmeniMedicinskoOsobljeAdmin(medicinskoOsobljeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(medicinskoOsobljeDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/izbrisiMedicinskoOsoblje/{id}")
	public ResponseEntity<Void> deleteExam(@PathVariable Long id) {

		MedicinskoOsoblje medicinskoOsoblje = medicinskoOsobljeService.findOne(id);
		List<MedicinskoOsobljeDTO> medicinskoOsobljeDTO = new ArrayList<>();
		if (medicinskoOsoblje != null) {
			medicinskoOsobljeService.remove(id);
			List<MedicinskoOsoblje> medOsobolje = medicinskoOsobljeService.findAll();

			
			for (MedicinskoOsoblje medO : medOsobolje) {
				medicinskoOsobljeDTO.add(new MedicinskoOsobljeDTO(medO));
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
