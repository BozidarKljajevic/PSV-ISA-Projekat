package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.Lekar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.ZahtevDTO;

import com.example.demo.model.Pregled;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.Zahtev;

import com.example.demo.service.LekarService;

import com.example.demo.service.SalaKlinikeService;
import com.example.demo.service.TipPregledaService;
import com.example.demo.service.ZahteviService;



@RestController
@RequestMapping(value = "zahtevi")

public class ZahteviController {

	@Autowired
	private ZahteviService zahteviService;
	
	@Autowired
	private SalaKlinikeService salaKlinikeService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@GetMapping(value = "/zahteviZaPreglede")
	public ResponseEntity<?> getZahteviZaPregledi() {

		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahteviDTO = new ArrayList<>();

		List<SalaKlinike> sale = salaKlinikeService.findAll();
		List<TipPregleda> tipovi = tipPregledaService.findAll();

		
		
		for (Zahtev zahtev : zahtevi) {
			if (zahtev.isIzbor() == true) {
				//zahtev.setSala(sale.get(0));
				//zahtev.setTipPregleda(tipovi.get(0));
				zahteviDTO.add(new ZahtevDTO(zahtev));
			}
		}

		return new ResponseEntity<>(zahteviDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/rezervisiSalu/{idSale}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> rezervisiSalu(@RequestBody ZahtevDTO zahtevDTO, @PathVariable Long idSale) {

		Zahtev zahtev = zahteviService.findOne(zahtevDTO.getId());
		zahtev.setLekar(lekarService.findOne(zahtevDTO.getLekar().getId()));
		zahtev.setDatum(zahtevDTO.getDatum());
		zahtev.setVreme(zahtevDTO.getVreme());
		

		SalaKlinike sala = salaKlinikeService.findOne(idSale);
	
		zahtev.setSala(sala);
		
		zahteviService.dodajRezervisanuSalu(zahtev);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	

	@GetMapping(value = "/zahteviZaOperacije")
	public ResponseEntity<List<ZahtevDTO>> getZakazaneOperacije() {

		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahtevDTO = new ArrayList<>();

		List<SalaKlinike> sale= salaKlinikeService.findAll();
		List<TipPregleda> tipovi= tipPregledaService.findAll();
		
		for (Zahtev zahtev : zahtevi) {
			if (zahtev.isIzbor() == false) {
				zahtev.setSala(sale.get(0));
				zahtev.setTipPregleda(tipovi.get(0));
				zahtevDTO.add(new ZahtevDTO(zahtev));
			}
		}

		return new ResponseEntity<>(zahtevDTO, HttpStatus.OK);
	}

}
