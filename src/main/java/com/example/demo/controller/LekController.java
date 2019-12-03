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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LekDTO;
import com.example.demo.model.Bolesti;
import com.example.demo.model.Lek;
import com.example.demo.service.LekService;



@RestController
@RequestMapping(value = "lek")
@CrossOrigin(origins = "http://localhost:8081")
public class LekController {
	
	@Autowired
	private LekService lekService;
	
	@PostMapping(value = "/dodajLek", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LekDTO> dodajLek(@RequestBody LekDTO lekDTO) {
		Lek lek = lekService.findOne(lekDTO.getSifra());
		if(lek!=null) {
			throw new ValidationException("Lek sa tom sifrom vec psotoji");
		}else {	
			LekDTO lekdto = new LekDTO();
			try {
				lekdto = lekService.dodajLek(lekDTO);
			}catch (ValidationException e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(lekdto, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/postojeciLek")
	public ResponseEntity<List<LekDTO>> getPostojeciLek() {
		
		List<Lek> lekovi = lekService.findAll();

		List<LekDTO> lekDTO = new ArrayList<>();
		for (Lek lek : lekovi) {
			lekDTO.add(new LekDTO(lek));
		}

		return new ResponseEntity<>(lekDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/izbrisiLek/{id}")
	public ResponseEntity<List<LekDTO>> izbrisiLek(@PathVariable Long id) {

		Lek lek = lekService.findOne(id);

		List<LekDTO> lekDTO = new ArrayList<>();
		if (lek != null) {
			lekService.remove(id);
			List<Lek> lekovi = lekService.findAll();

			
			for (Lek lek1 : lekovi) {
				lekDTO.add(new LekDTO(lek1));
			}
			
			return new ResponseEntity<>(lekDTO,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(lekDTO,HttpStatus.NOT_FOUND);
		}
	}
}