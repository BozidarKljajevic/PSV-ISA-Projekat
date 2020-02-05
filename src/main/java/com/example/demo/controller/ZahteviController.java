
package com.example.demo.controller;

import java.text.SimpleDateFormat;
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
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.EmailService;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaKlinikeService;
import com.example.demo.service.TipPregledaService;
import com.example.demo.service.ZahteviService;



@RestController
@RequestMapping(value = "zahtevi")

public class ZahteviController {

	@Autowired
	private ZahteviService zahteviService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
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
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private OperacijaService operacijaService;
	
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
	
	@GetMapping(value = "/zahteviZaPreglede/{id}")
	public ResponseEntity<?> getZahteviZaPregledi(@PathVariable Long id) {

		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahteviDTO = new ArrayList<>();
		AdminKlinike admin = adminKlinikeService.findOne(id);
		List<SalaKlinike> sale = salaKlinikeService.findAll();
		List<TipPregleda> tipovi = tipPregledaService.findAll();

		
		
		for (Zahtev zahtev : zahtevi) {
			if (zahtev.isIzbor() == true && zahtev.getSala() == null && admin.getKlinika() == zahtev.getLekar().getKlinika()) {
				//zahtev.setSala(sale.get(0));
				//zahtev.setTipPregleda(tipovi.get(0));
				zahteviDTO.add(new ZahtevDTO(zahtev));
			}
		}

		return new ResponseEntity<>(zahteviDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/zahtev/{id}")
	public ResponseEntity<?> getZahtev(@PathVariable Long id) {

		Zahtev zahtev = zahteviService.findOne(id);

		return new ResponseEntity<>(new ZahtevDTO(zahtev), HttpStatus.OK);
	}
	
	@PostMapping(value = "/potvrdiZahtev")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> potvrdiZahtev(@RequestBody ZahtevDTO zahtevDTO) {

		Zahtev zahtev = zahteviService.findOne(zahtevDTO.getId());
		zahteviService.remove(zahtevDTO.getId());
		
		pregledService.potvrdiZahtevZaPregled(zahtev);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	/*
	@PostMapping(value = "/potvrdiZahtevOperacije")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> potvrdiZahtevOperacije(@RequestBody ZahtevDTO zahtevDTO) {

		Zahtev zahtev = zahteviService.findOne(zahtevDTO.getId());
		zahteviService.remove(zahtevDTO.getId());
		System.out.println(zahtev.getDatum());
		operacijaService.potvrdiZahtevZaOperaciju(zahtev);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}*/
	
	
	@PostMapping(value = "/odbijZahtev")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> odbijZahtev(@RequestBody ZahtevDTO zahtevDTO) {

		zahteviService.remove(zahtevDTO.getId());

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping(value = "/rezervisiSalu/{idSale}/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> rezervisiSalu(@RequestBody ZahtevDTO zahtevDTO, @PathVariable Long idSale, @PathVariable Long id) throws MailException, InterruptedException {

		Zahtev zahtev = zahteviService.findOne(zahtevDTO.getId());
		AdminKlinike admin = adminKlinikeService.findOne(id);
		System.out.println(zahtevDTO.getLekar().getIme());
		zahtev.setLekar(lekarService.findOne(zahtevDTO.getLekar().getId()));
		zahtev.setDatum(zahtevDTO.getDatum());
		zahtev.setVreme(zahtevDTO.getVreme());
		

		SalaKlinike sala = salaKlinikeService.findOne(idSale);
	
		zahtev.setSala(sala);
		
		zahteviService.dodajRezervisanuSalu(zahtev);
		
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahteviDTO = new ArrayList<>();
		
		
		for (Zahtev z : zahtevi) {
			if (z.getSala() == null && admin.getKlinika() == z.getLekar().getKlinika() && z.isIzbor() == true) {
				
				zahteviDTO.add(new ZahtevDTO(z));
			}
		}
		List<Pacijent> pacijenti = pacijentService.findAll();

		Pacijent pacijent = pacijentService.findOne(zahtev.getIdPacijenta());
		
				String message = "Dobili ste salu, da li vam odgovara ovaj termin i ovaj lekar, ako da odgovorite ? "
						+"lekar: "+ zahtevDTO.getLekar().getIme() + " datum: " + zahtevDTO.getDatum() + " vreme: " + zahtevDTO.getVreme()
						+ " link: localhost:8081/#/potvrdaZahteva/" + zahtevDTO.getId();
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
	
	@PostMapping(value = "/rezervisi/{idSale}/{lekar1}/{lekar2}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> rezervisi(@RequestBody ZahtevDTO zahtevDTO, @PathVariable Long idSale, @PathVariable Long lekar1, @PathVariable Long lekar2) throws MailException, InterruptedException {

		Zahtev zahtev = zahteviService.findOne(zahtevDTO.getId());
		System.out.println(zahtevDTO.getLekar().getIme());
		zahtev.setLekar(lekarService.findOne(zahtevDTO.getLekar().getId()));
		zahtev.setDatum(zahtevDTO.getDatum());
		zahtev.setVreme(zahtevDTO.getVreme());
		
		zahtev.setLekar1(lekarService.findOne(lekar1));
		zahtev.setLekar2(lekarService.findOne(lekar2));
		
		SalaKlinike sala = salaKlinikeService.findOne(idSale);
	
		zahtev.setSala(sala);
		System.out.println(lekar1);
		System.out.println(lekar2);
		zahteviService.dodajRezervisanuSalu(zahtev);
		
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahteviDTO = new ArrayList<>();
		List<Lekar> lekariMail = new ArrayList<>();
		
		for (Zahtev z : zahtevi) {
			if (z.getSala() == null) {
				
				zahteviDTO.add(new ZahtevDTO(z));
			}
		}
		List<Pacijent> pacijenti = pacijentService.findAll();

		Pacijent pacijent = pacijentService.findOne(zahtev.getIdPacijenta());
		Lekar lekar11 = lekarService.findOne(lekar1);
		Lekar lekar22 = lekarService.findOne(lekar2);
				String message = "Vasa operacija je zakazana datuma: " + zahtevDTO.getDatum() + "u vreme: " + zahtevDTO.getVreme();
				emailService.sendNotificaitionAsync((User) pacijent, message);
				
				
				String message2 = "Imate operaciju datuma: " + zahtev.getDatum() + "u vreme: " + zahtev.getVreme();
				if (lekar1!=-1) {
					emailService.sendNotificaitionAsync((User) lekar11, message2);
				}
				if(lekar2!=-1) {
					emailService.sendNotificaitionAsync((User) lekar22, message2);
				}
				
				emailService.sendNotificaitionAsync((User) zahtev.getLekar(), message2);
			
				
				zahteviService.remove(zahtevDTO.getId());
				System.out.println(zahtev.getDatum());
				operacijaService.potvrdiZahtevZaOperaciju(zahtev);
				
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
			if (zahtev.isIzbor() == false && zahtev.getSala() == null) {
				//zahtev.setSala(sale.get(0));
				//zahtev.setTipPregleda(tipovi.get(0));
				zahtevDTO.add(new ZahtevDTO(zahtev));
			}
		}

		return new ResponseEntity<>(zahtevDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/zahteviPacijenta/{id}")
	public ResponseEntity<List<ZahtevDTO>> getZahtevePacijenta(@PathVariable Long id) {
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<ZahtevDTO> zahteviDTO = new ArrayList<>();
		
		for (Zahtev zahtev : zahtevi) {
			if (zahtev.getIdPacijenta() == id) {
				zahteviDTO.add(new ZahtevDTO(zahtev));
			}
		}

		return new ResponseEntity<>(zahteviDTO, HttpStatus.OK);
	}
}