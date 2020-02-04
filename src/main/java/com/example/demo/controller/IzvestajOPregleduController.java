package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.IzvestajOPregleduDTO;
import com.example.demo.dto.LekDTO;
import com.example.demo.model.Bolesti;
import com.example.demo.model.Lek;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.service.BolestiService;
import com.example.demo.service.IzvestajOPregleduService;
import com.example.demo.service.LekService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;

@RestController
@RequestMapping(value = "izvestajpregleda")
public class IzvestajOPregleduController {

	@Autowired
	private PregledService pregledService;

	@Autowired
	private BolestiService bolestiService;

	@Autowired
	private LekarService lekarService;

	@Autowired
	private PacijentService pacijentService;

	@Autowired
	private LekService lekService;

	@Autowired
	private IzvestajOPregleduService izvestajOPregleduService;

	@PostMapping(value = "/zavrsi/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> zavrsiPregled(@PathVariable Long id, @RequestBody IzvestajOPregleduDTO izvestaj) {

		Pregled pregled = pregledService.findOne(id);
		pregledService.zavrsiPregled(pregled);

		System.out.println("Pacijent: " + izvestaj.getPacijent().getIme() + " " + izvestaj.getPacijent().getPrezime());
		System.out.println("Lekar: " + izvestaj.getLekar().getIme() + " " + izvestaj.getLekar().getPrezime());
		System.out.println("Bolest: " + izvestaj.getBolest().getNaziv());
		for (LekDTO lekDTO : izvestaj.getLekovi()) {
			System.out.println("Lek: " + lekDTO.getNaziv());
		}

		Bolesti bolest = bolestiService.findOne(izvestaj.getBolest().getSifra());
		Lekar lekar = lekarService.findOne(izvestaj.getLekar().getId());
		Pacijent pacijent = pacijentService.findOne(izvestaj.getPacijent().getId());
		List<Lek> lekovi = new ArrayList<Lek>();

		for (LekDTO lekDTO : izvestaj.getLekovi()) {
			lekovi.add(lekService.findOne(lekDTO.getSifra()));
		}

		izvestajOPregleduService.zavrsiIzvestaj(pacijent, lekar, bolest, lekovi);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
