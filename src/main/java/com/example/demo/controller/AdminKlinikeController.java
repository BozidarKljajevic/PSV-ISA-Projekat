package com.example.demo.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.service.AdminKlinikeService;

@RestController
@RequestMapping(value = "adminKlinike")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminKlinikeController {

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@GetMapping(value = "/postojeciAdminKlinike")
	public ResponseEntity<AdminKlinikeDTO> getPostojeciAdminKlinike() {
		
		AdminKlinike adminKlinike = adminKlinikeService.findOne((long) 1);
		
		AdminKlinikeDTO adminKlinikeDTO = new AdminKlinikeDTO(adminKlinike);
		
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajAdminaKlinike", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdminKlinikeDTO> dodajAdminaKlinike(@RequestBody AdminKlinikeDTO adminDTO) {
		
		AdminKlinikeDTO admindto = new AdminKlinikeDTO();
		try {
			admindto = adminKlinikeService.dodajAdminaKlinike(adminDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(admindto, HttpStatus.OK);
	}
	
}
