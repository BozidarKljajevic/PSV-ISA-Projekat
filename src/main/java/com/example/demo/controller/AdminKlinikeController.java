package com.example.demo.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.User;
import com.example.demo.service.AdminKlinikeService;

@RestController
@RequestMapping(value = "adminKlinike")
public class AdminKlinikeController {

	@Autowired
	private AdminKlinikeService adminKlinikeService;
		
	@PostMapping(value = "/dodajAdminaKlinike", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<AdminKlinikeDTO> dodajAdminaKlinike(@RequestBody AdminKlinikeDTO adminDTO) throws Exception {
		
		User existUser = this.adminKlinikeService.findOneM(adminDTO.getMail());
		if (existUser != null) {
			throw new Exception("Alredy exist");
		}
		
		AdminKlinikeDTO admindto = new AdminKlinikeDTO();
		try {
			admindto = adminKlinikeService.dodajAdminaKlinike(adminDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(admindto, HttpStatus.OK);
	}
}