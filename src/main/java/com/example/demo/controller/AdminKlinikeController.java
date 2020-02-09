package com.example.demo.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.IzmenaSifreDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.SifraDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Pacijent;
import com.example.demo.model.User;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "adminKlinike")
public class AdminKlinikeController {

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/izmeniGenerickuSifru/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> izmeniGenerickuSifru(@RequestBody SifraDTO sifra, @PathVariable Long id) {
		
		User user = userService.findOne(id);
		
		System.out.println(sifra.getSifra());
		System.out.println(id);
		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		userService.izmeniSifru(user, sifra.getSifra());

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciAdminKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AdminKlinikeDTO> getPostojeciAdminKlinike(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		AdminKlinike adminKlinike = adminKlinikeService.findOne(idLong);
		
		AdminKlinikeDTO adminKlinikeDTO = new AdminKlinikeDTO(adminKlinike);
		
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeniPodatkeAdminaKlinike", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AdminKlinikeDTO> izmeniPodatkeAdminaKlinike(@RequestBody AdminKlinikeDTO adminKlinikeDTO){
		
		try {
			adminKlinikeService.izmeniAdminKlinike(adminKlinikeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}
		
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
	
	@PostMapping(value = "/promeniSifruAdminKlinike/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> promeniSifruAdmin(@PathVariable Long id, @RequestBody IzmenaSifreDTO sifra)
	{
		AdminKlinike admin = adminKlinikeService.findOne(id);
		
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(admin.getMail(),
						sifra.getStara()));
		
		User user = (User) authentication.getPrincipal();
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		boolean success = adminKlinikeService.izmeniSifru(admin, sifra);
		
		if (success) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}