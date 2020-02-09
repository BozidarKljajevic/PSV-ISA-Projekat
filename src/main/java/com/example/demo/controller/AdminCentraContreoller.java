package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminCentraDTO;
import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.SifraDTO;
import com.example.demo.model.Pacijent;
import com.example.demo.model.User;
import com.example.demo.service.AdminCentraService;
import com.example.demo.service.EmailService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "adminCentra")
public class AdminCentraContreoller {

	@Autowired
	private AdminCentraService adminCentraService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@PostMapping(value = "/izmeniGenerickuSifru/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
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
	
	@PostMapping(value = "/aktivirajPacijenta/{id}")
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<?> aktivirajPacijenta(@PathVariable Long id) throws MailException, InterruptedException {
		
		User user = userService.findOne(id);

		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		//userService.aktiviran(user);

		emailService.sendNotificaitionAsync(user, "Kliknite na link kako bi aktivirali nalog --> http://localhost:8081/#/aktivacijaPacijenta/"+user.getId());
		
		List<Pacijent> pacijenti = pacijentService.findAll();

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent pacijent : pacijenti) {
			if(pacijent.isEnabled()==false) {
				pacijentiDTO.add(new PacijentDTO(pacijent));
			}
		}
		
		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajAdminaCentra", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<AdminCentraDTO> dodajAdminaKlinike(@RequestBody AdminCentraDTO adminDTO) throws Exception {
		
		User existUser = this.adminCentraService.findOneM(adminDTO.getMail());
		if (existUser != null) {
			throw new Exception("Alredy exist");
		}
		
		AdminCentraDTO admindto = new AdminCentraDTO();
		try {
			admindto = adminCentraService.dodajAdminaCentar(adminDTO);
		}catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(admindto, HttpStatus.OK);
	}

}
