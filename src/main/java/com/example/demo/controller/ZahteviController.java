package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private TipPregledaService tipPregledaService;

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
