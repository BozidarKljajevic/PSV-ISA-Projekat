package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.OcenaDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.model.OcenaKlinika;
import com.example.demo.model.OcenaLekar;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;

@RestController
@RequestMapping(value = "ocena")
public class OcenaController {

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private KlinikaService klinikaService;

	@Autowired
	private PacijentService pacijentService;

	@PostMapping(value = "/oceni/{idPacijent}/{idLekar}/{ocenaLekar}/{idKlinike}/{ocenaKlinike}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> oceniLekara(@PathVariable Long idPacijent, @PathVariable Long idLekar,
			@PathVariable Integer ocenaLekar, @PathVariable Long idKlinike, @PathVariable Integer ocenaKlinike) {

		if (ocenaLekar != 0) {
			lekarService.oceniLekara(idPacijent, idLekar, ocenaLekar);
		}
		
		if (ocenaKlinike != 0) {
			klinikaService.oceniKliniku(idPacijent, idKlinike, ocenaKlinike);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping(value = "/ocena/{idPacijent}/{idLekar}/{idKlinika}")
	public ResponseEntity<OcenaDTO> getOcena(@PathVariable Long idPacijent, @PathVariable Long idLekar,
			@PathVariable Long idKlinika) {

		OcenaLekar ocenaLekara = lekarService.getOcena(idPacijent, idLekar);
		OcenaKlinika ocenaKlinike = klinikaService.getOcena(idPacijent, idKlinika);
		OcenaDTO ocenaDTO = new OcenaDTO();

		if (ocenaLekara != null && ocenaKlinike != null) {
			ocenaDTO = new OcenaDTO(ocenaLekara, ocenaKlinike);
		}else{
			ocenaDTO.setLekar(new LekarDTO(lekarService.findOne(idLekar)));
			ocenaDTO.setPacijent(new PacijentDTO(pacijentService.findOne(idPacijent)));
			ocenaDTO.setKlinika(new KlinikaDTO(klinikaService.findOne(idKlinika)));
			ocenaDTO.setOcenaLekar((ocenaLekara == null)? 0:ocenaLekara.getOcena());
			ocenaDTO.setOcenaKlinike((ocenaKlinike == null)? 0:ocenaKlinike.getOcena());
		}

		return new ResponseEntity<>(ocenaDTO, HttpStatus.OK);
	}
}
