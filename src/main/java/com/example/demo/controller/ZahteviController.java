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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;

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
import com.example.demo.model.User;
import com.example.demo.model.Zahtev;
import com.example.demo.service.EmailService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PacijentService;
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
	private EmailService emailService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	
	@PostMapping(value = "/izmeniZahtev", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ZahtevDTO> izmeniPodatkeSaleKlinike(@RequestBody ZahtevDTO zahtevDTO) {

		try {
			zahteviService.izmeniZahtev(zahtevDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(zahtevDTO, HttpStatus.OK);
	}
	
	

	
	@GetMapping(value = "/zahteviZaPreglede")
	public ResponseEntity<?> getZahteviZaPregledi() {

		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahteviDTO = new ArrayList<>();

		List<SalaKlinike> sale = salaKlinikeService.findAll();
		List<TipPregleda> tipovi = tipPregledaService.findAll();

		
		
		for (Zahtev zahtev : zahtevi) {
			if (zahtev.isIzbor() == true && zahtev.getSala() == null) {
				//zahtev.setSala(sale.get(0));
				//zahtev.setTipPregleda(tipovi.get(0));
				zahteviDTO.add(new ZahtevDTO(zahtev));
			}
		}

		return new ResponseEntity<>(zahteviDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/rezervisiSalu/{idSale}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> rezervisiSalu(@RequestBody ZahtevDTO zahtevDTO, @PathVariable Long idSale) throws MailException, InterruptedException {

		Zahtev zahtev = zahteviService.findOne(zahtevDTO.getId());
		zahtev.setLekar(lekarService.findOne(zahtevDTO.getLekar().getId()));
		zahtev.setDatum(zahtevDTO.getDatum());
		zahtev.setVreme(zahtevDTO.getVreme());
		

		SalaKlinike sala = salaKlinikeService.findOne(idSale);
	
		zahtev.setSala(sala);
		
		zahteviService.dodajRezervisanuSalu(zahtev);
		
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahteviDTO = new ArrayList<>();
		
		
		for (Zahtev z : zahtevi) {
			if (z.getSala() == null) {
				
				zahteviDTO.add(new ZahtevDTO(z));
			}
		}
		List<Pacijent> pacijenti = pacijentService.findAll();

		Pacijent pacijent = pacijentService.findOne(zahtev.getIdPacijenta());
		
				String message = "Dobili ste salu, da li vam odgovara ovaj termin i ova lekar, ako da odgovorite ? "
						+"lekar:"+ zahtevDTO.getLekar().getIme() + "datum: " + zahtevDTO.getDatum() + "vreme: " + zahtevDTO.getVreme()
						+ "zahtev id: " + zahtevDTO.getId();
				emailService.sendNotificaitionAsync((User) pacijent, message);
				
				String message2 = "Dobili ste salu, da li vam odgovara ovaj termin, ako da odgovorite ? "
						+"lekar:"+ zahtevDTO.getLekar().getIme() + "datum: " + zahtevDTO.getDatum() + "vreme: " + zahtevDTO.getVreme()
						+ "zahtev id: " + zahtevDTO.getId()+ "pacijent: " + pacijent.getIme();
				emailService.sendNotificaitionAsync((User) zahtev.getLekar(), message);
				
				
		/*for (Pacijent pacijent : pacijenti) {
			if (adminKlinike.getKlinika().getId() == zahtevDTO.getLekar().getKlinika().getId()) {
				String message = "Dodata je sala i za pregled na Vasoj klinici za lekara "
						+ zahtevDTO.getLekar().getIme() + " " + zahtevDTO.getLekar().getPrezime();
				emailService.sendNotificaitionAsync((User) zahtev.getIdPacijenta(), message);
			}
		} */
		
		return new ResponseEntity<>(zahteviDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/zahteviZaOperacije")
	public ResponseEntity<List<ZahtevDTO>> getZakazaneOperacije() {

		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahtevDTO = new ArrayList<>();

		List<SalaKlinike> sale= salaKlinikeService.findAll();
		List<TipPregleda> tipovi= tipPregledaService.findAll();
		
		for (Zahtev zahtev : zahtevi) {
			if (zahtev.isIzbor() == false) {
				//zahtev.setSala(sale.get(0));
				//zahtev.setTipPregleda(tipovi.get(0));
				zahtevDTO.add(new ZahtevDTO(zahtev));
			}
		}

		return new ResponseEntity<>(zahtevDTO, HttpStatus.OK);
	}

}
