package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.LekarService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "tipPregleda")

public class TipPregledaController {

	@Autowired
	private TipPregledaService tipPregledaService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private LekarService lekarService;

	@PostMapping(value = "/dodajTipPregleda/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TipPregledaDTO> dodajTipPregleda(@RequestBody TipPregledaDTO tipPregledaDTO,
			@PathVariable String id) {

		TipPregledaDTO tipDTO = new TipPregledaDTO();
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<TipPregleda> tipovi = tipPregledaService.findAll();
		for (TipPregleda tip : tipovi) {
			if (tip.getOznaka().equals(tipPregledaDTO.getOznaka())
					&& tip.getKlinika().getId() == adm.getKlinika().getId()) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if (tip.getNaziv().equals(tipPregledaDTO.getNaziv())
					&& tip.getKlinika().getId() == adm.getKlinika().getId()) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		try {
			tipDTO = tipPregledaService.dodajTipPregleda(tipPregledaDTO, idLong);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(tipDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/izmeniPodatkeTipaPregleda/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TipPregledaDTO> izmeniPodatkeTipaPregleda(@PathVariable Long id,@RequestBody TipPregledaDTO tipPregledaDTO) {

		
		
		
		AdminKlinike adm = adminKlinikeService.findOne(id);
		List<Lekar> lekari = lekarService.findAll();
		boolean flag = false;
		
		try {
			for (Lekar lekar : lekari) {
				if ( lekar.getTipPregleda().getId() == tipPregledaDTO.getId()) {
					flag = true;
				}
			}
			
			if(flag == false) {
				tipPregledaService.izmeniTipPregleda(tipPregledaDTO);
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(tipPregledaDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/izbrisiTipPregleda/{id}/{idd}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteTip(@PathVariable Long id, @PathVariable String idd) {

		TipPregleda tipPregleda = tipPregledaService.findOne(id);
		Long idLong = Long.parseLong(idd);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<Lekar> lekari = lekarService.findAll();
		boolean flag = false;
		for (Lekar lekar : lekari) {
			if (adm.getKlinika().getId() == lekar.getKlinika().getId()) {
				if (lekar.getTipPregleda().getId() == tipPregleda.getId()) {
					flag = true;
				}
			}
		}
		if (tipPregleda != null && flag == false) {
			tipPregledaService.remove(id);
			List<TipPregleda> tipPre = tipPregledaService.findAll();
			List<TipPregledaDTO> tipoviDTO = new ArrayList<>();

			for (TipPregleda tip : tipPre) {
				if (tip.getKlinika().getId() == adm.getKlinika().getId()) {
					tipoviDTO.add(new TipPregledaDTO(tip));
				}
			}
			return new ResponseEntity<>(tipoviDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/sviTipovi")
	public ResponseEntity<List<TipPregledaDTO>> getSveKlinike() {

		List<TipPregleda> tipoviPregleda = tipPregledaService.findAll();

		List<TipPregledaDTO> tipoviDTO = new ArrayList<>();

		for (TipPregleda tip : tipoviPregleda) {

			tipoviDTO.add(new TipPregledaDTO(tip));
		}

		return new ResponseEntity<>(tipoviDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/sviTipoviDistinct")
	public ResponseEntity<Collection<TipPregledaDTO>> getSveKlinikeDistinct() {

		List<TipPregleda> tipoviPregleda = tipPregledaService.findAll();

		Map<String, TipPregledaDTO> tipoviDTO = new HashMap();

		for (TipPregleda tip : tipoviPregleda) {
			if (!tipoviDTO.containsKey(tip.getNaziv().toLowerCase())) {
				tipoviDTO.put(tip.getNaziv().toLowerCase(), new TipPregledaDTO(tip));
			}
		}

		return new ResponseEntity<>(tipoviDTO.values(), HttpStatus.OK);
	}

	@GetMapping(value = "/TipoviKlinike/{id}")
	public ResponseEntity<List<TipPregledaDTO>> getTipovi(@PathVariable String id) {

		List<TipPregleda> tipoviPregleda = tipPregledaService.findAll();

		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<TipPregledaDTO> tipoviDTO = new ArrayList<>();

		for (TipPregleda tip : tipoviPregleda) {
			if (tip.getKlinika().getId() == adm.getKlinika().getId()) {
				tipoviDTO.add(new TipPregledaDTO(tip));
			}
		}

		return new ResponseEntity<>(tipoviDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/TipoviLekara/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<LekarDTO>> getTipoviLekara(@PathVariable String id) {

		List<Lekar> lekari = lekarService.findAll();

		Long idLong = Long.parseLong(id);
		TipPregleda tipPregleda = tipPregledaService.findOne(idLong);
		List<LekarDTO> lekarDTO = new ArrayList<>();

		for (Lekar le : lekari) {
			if (le.getTipPregleda().getId() == tipPregleda.getId()) {
				lekarDTO.add(new LekarDTO(le));
			}
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/tipoviPregledaKlinike/{id}")
	public ResponseEntity<List<TipPregledaDTO>> getTipovi(@PathVariable Long id) {

		List<TipPregleda> tipoviPregleda = tipPregledaService.findAll();
		List<TipPregledaDTO> tipoviDTO = new ArrayList<>();

		for (TipPregleda tip : tipoviPregleda) {
			if (tip.getKlinika().getId() == id) {
				tipoviDTO.add(new TipPregledaDTO(tip));
			}
		}

		return new ResponseEntity<>(tipoviDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/tipPregleda/{id}")
	public ResponseEntity<TipPregledaDTO> getTipPregleda(@PathVariable Long id){
		
		return new ResponseEntity<>(new TipPregledaDTO(tipPregledaService.findOne(id)), HttpStatus.OK);
	}
}