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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pregled;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PregledService;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "klinika")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@GetMapping(value = "/sveKlinike")
	//@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<List<KlinikaDTO>> getSveKlinike() {
		
		List<Klinika> klinike = klinikaService.findAll();

		List<KlinikaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika klinika : klinike) {
			klinikeDTO.add(new KlinikaDTO(klinika));
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajKliniku", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<KlinikaDTO> dodajKliniku(@RequestBody KlinikaDTO klinikaDTO) {
		
		KlinikaDTO klinikadto = new KlinikaDTO();
		try {
			klinikadto = klinikaService.dodajKliniku(klinikaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(klinikadto, HttpStatus.OK);
	}

	
	@GetMapping(value = "/postojecaKlinika/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<KlinikaDTO> getPostojecaKlinika(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Klinika klinika = klinikaService.findOne(adminKlinikeService.findOne(idLong).getKlinika().getId());
		
		KlinikaDTO klinikaDTO = new KlinikaDTO(klinika);
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeniPodatkeKlinike", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<KlinikaDTO> izmeniPodatkeKlinike(@RequestBody KlinikaDTO klinikaDTO){
		
		try { 
			klinikaService.izmeniKliniku(klinikaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciLekariKlinike/{id}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<List<LekarDTO>> getPostojeciLekariKlinikePacijent(@PathVariable Long id) {

		List<Lekar> lekari = lekarService.findAll();
		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar lekar : lekari) {
			if (lekar.getKlinika().getId() == id) {
				lekarDTO.add(new LekarDTO(lekar));
			}
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/drzaveKlinika")
	public ResponseEntity<List<String>> getDrzaveKlinika() {
		
		List<String> drzave = klinikaService.getDrzave();
		
		return new ResponseEntity<>(drzave, HttpStatus.OK);
	}
	
	@GetMapping(value = "/gradoviKlinika/{drzava}")
	public ResponseEntity<List<String>> getDrzaveKlinika(@PathVariable String drzava) {
		
		List<String> gradovi = new ArrayList<String>();
		
		if(drzava.equals("none")) {
			gradovi = klinikaService.getGradovi();
		}else {
			gradovi = klinikaService.getGradovi(drzava);
		}
		
		return new ResponseEntity<>(gradovi, HttpStatus.OK);
	}
	
	@PostMapping(value = "/pretraziKlinike/{datumPregleda}/{tipPregleda}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<Collection<KlinikaDTO>> pretraziKlinike(@PathVariable String datumPregleda, @PathVariable Long tipPregleda) {
		
		List<Klinika> klinike = klinikaService.findAll();
		String[] yyyymmdd = datumPregleda.split("-");
		String datum = yyyymmdd[2]+"/"+yyyymmdd[1]+"/"+yyyymmdd[0];
		
		TipPregleda tipPregledaPretraga = tipPregledaService.findOne(tipPregleda);

		Map<Long, KlinikaDTO> klinikeDTO = new HashMap();
		for (Klinika klinika : klinike) {
			List<Lekar> lekari = lekarService.sviLekariKlinike(klinika.getId());
			for (Lekar lekar : lekari) {
				boolean imaPregledZaZadatiDatum = false;
				int trajanjePregledaMin = 0;
				if (lekar.getTipPregleda().getNaziv().toLowerCase().equals(tipPregledaPretraga.getNaziv().toLowerCase())) {
					List<Pregled> pregledi = pregledService.getPregledeOdLekara(lekar.getId());
					for (Pregled pregled : pregledi) {
						if (datum.equals(pregled.getDatum())) {
							imaPregledZaZadatiDatum = true;
							trajanjePregledaMin += pregled.getTrajanjePregleda()*60;
						}
					}
					if (imaPregledZaZadatiDatum) {
						String[] hhmmOd = lekar.getRadnoOd().split(":");
						int hOd = Integer.parseInt(hhmmOd[0]);
						int mOd = Integer.parseInt(hhmmOd[1]);
						
						String[] hhmmDo = lekar.getRadnoDo().split(":");
						int hDo = Integer.parseInt(hhmmDo[0]);
						int mDo = Integer.parseInt(hhmmDo[1]);
						
						int slobodnoVremeLekaraMin = Math.abs((hDo - hOd))*60 - (mDo - mOd);
						
						if (slobodnoVremeLekaraMin - trajanjePregledaMin > 0) {
							if (!klinikeDTO.containsKey(klinika.getId())) {
								klinikeDTO.put(klinika.getId() , new KlinikaDTO(klinika));
							}
						}
						
					} else {
						if (!klinikeDTO.containsKey(klinika.getId())) {
							klinikeDTO.put(klinika.getId() , new KlinikaDTO(klinika));
						}
					}
				}
			}
		}

		return new ResponseEntity<>(klinikeDTO.values(), HttpStatus.OK);
	}
	
}
