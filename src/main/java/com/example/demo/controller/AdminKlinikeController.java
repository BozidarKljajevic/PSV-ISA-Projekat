package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.service.AdminKlinikeService;

@RestController
@RequestMapping(value = "adminKlinike")
@CrossOrigin(origins = "http://localhost:8081")
public class AdminKlinikeController {

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@GetMapping(value = "/postojeciAdminKlinike")
	public ResponseEntity<AdminKlinikeDTO> getPostojeciAdminKlinike() {
		
		AdminKlinike adminKlinike = adminKlinikeService.findOne((long) 1);
		
		AdminKlinikeDTO adminKlinikeDTO = new AdminKlinikeDTO(adminKlinike);
		
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}
	
}
