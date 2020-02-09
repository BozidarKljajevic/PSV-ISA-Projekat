package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.dto.IzvestajOPregleduDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.ReceptDTO;
import com.example.demo.dto.SifraDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Recept;
import com.example.demo.model.User;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.IzvestajOPregleduService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.ReceptService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "medicinskaSestra")
public class MedicinskaSestraController {

	@Autowired
	private MedicinskaSestraService medicinskaSestraService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private ReceptService receptService;
	
	@Autowired
	private IzvestajOPregleduService izvestajOPregleduService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping(value = "/izmeniGenerickuSifru/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('MEDICINSKASESTRA')")
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
	
	@PostMapping(value = "/dodajSestru/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<MedicinskaSestraDTO> dodajSestru(@RequestBody MedicinskaSestraDTO medicinskaSestraDTO1,@PathVariable String id) throws Exception {
		MedicinskaSestraDTO medicinskaSestraDTO2 = new MedicinskaSestraDTO();
		
		User existUser = this.userService.findOne(medicinskaSestraDTO1.getMail());
		if (existUser != null) {
			throw new Exception("Alredy exist");
		}
		
		Long idLong = Long.parseLong(id);
		medicinskaSestraDTO2 =new MedicinskaSestraDTO(medicinskaSestraService.dodaj(medicinskaSestraDTO1,idLong));
		return new ResponseEntity<>(medicinskaSestraDTO2, HttpStatus.OK);
	}

	@PostMapping(value = "/izmeniSestru", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('MEDICINSKASESTRA')")
	public ResponseEntity<MedicinskaSestraDTO> izmeniPodatkeSestre(@RequestBody MedicinskaSestraDTO medicinskaSestraDTO){
		
		try {
			medicinskaSestraService.izmeni(medicinskaSestraDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(medicinskaSestraDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeniSestruAdmin", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<MedicinskaSestraDTO> izmeniPodatkeSestreAdmin(@RequestBody MedicinskaSestraDTO medicinskaSestraDTO){
		
		try {
			medicinskaSestraService.izmeni(medicinskaSestraDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(medicinskaSestraDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojecaSestra/{id}")
	@PreAuthorize("hasAuthority('MEDICINSKASESTRA')")
	public ResponseEntity<MedicinskaSestraDTO> getLekar(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		MedicinskaSestra medicinskaSestra = medicinskaSestraService.findOne(idLong);
		
		MedicinskaSestraDTO medicinskaSestraDTO = new MedicinskaSestraDTO(medicinskaSestra);
		
		return new ResponseEntity<>(medicinskaSestraDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/postojecaSestraAdmin/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<MedicinskaSestraDTO> getLekarAdmin(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		MedicinskaSestra medicinskaSestra = medicinskaSestraService.findOne(idLong);
		
		MedicinskaSestraDTO medicinskaSestraDTO = new MedicinskaSestraDTO(medicinskaSestra);
		
		return new ResponseEntity<>(medicinskaSestraDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izbrisiSestru/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteExam(@PathVariable Long id) {

		MedicinskaSestra medicinskaSestra = medicinskaSestraService.findOne(id);
		List<MedicinskaSestraDTO> medicinskaSestraDTO = new ArrayList<>();
		if (medicinskaSestra != null) {
			medicinskaSestraService.remove(id);
			List<MedicinskaSestra> sestre = medicinskaSestraService.findAll();

			
			for (MedicinskaSestra sestra : sestre) {
				medicinskaSestraDTO.add(new MedicinskaSestraDTO(sestra));
			}
			return new ResponseEntity<>(medicinskaSestraDTO,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/postojeceSestreKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<MedicinskaSestraDTO>> getPostojeciLekariKlinike(@PathVariable String id) {
		
		List<MedicinskaSestra> medicinskaSestra = medicinskaSestraService.findAll();
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<MedicinskaSestraDTO> medicinskaSestraDTO = new ArrayList<>();
		for (MedicinskaSestra sestra : medicinskaSestra) {
			 if(sestra.getKlinika().getId() == adm.getKlinika().getId()) {
			medicinskaSestraDTO.add(new MedicinskaSestraDTO(sestra));
			 }
		}

		return new ResponseEntity<>(medicinskaSestraDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/recepti/{id}")
	@PreAuthorize("hasAuthority('MEDICINSKASESTRA')")
	public ResponseEntity<List<IzvestajOPregleduDTO>> getRecepti(@PathVariable Long id) {
		
		List <IzvestajOPregledu> izvestaji = izvestajOPregleduService.findAll();
		MedicinskaSestra medicinskaSestra = medicinskaSestraService.findOne(id);
		List<IzvestajOPregleduDTO> izvestajDTO = new ArrayList<>();
		
		for(IzvestajOPregledu izvestaj : izvestaji) {
			if(medicinskaSestra.getKlinika().getId() == izvestaj.getLekara().getKlinika().getId() && izvestaj.getRecept().isOveren()==false) {
				izvestajDTO.add(new IzvestajOPregleduDTO(izvestaj));
			}
			
		}
		
		
		return new ResponseEntity<>(izvestajDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/promeniSifruSestra/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('MEDICINSKASESTRA')")
	public ResponseEntity<?> promeniSifruSestre(@PathVariable Long id, @RequestBody IzmenaSifreDTO sifra)
	{
		MedicinskaSestra sestra = medicinskaSestraService.findOne(id);
		
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(sestra.getMail(),
						sifra.getStara()));
		
		User user = (User) authentication.getPrincipal();
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		boolean success = medicinskaSestraService.izmeniSifru(sestra, sifra);
		
		if (success) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
}


