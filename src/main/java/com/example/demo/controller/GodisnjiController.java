package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.example.demo.dto.GodisnjiDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PorukaDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Godisnji;
import com.example.demo.model.Lekar;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.User;
import com.example.demo.model.Zahtev;
import com.example.demo.repository.GodisnjiRepository;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.EmailService;
import com.example.demo.service.GodisnjiService;
import com.example.demo.service.LekarService;
import com.example.demo.service.MedicinskaSestraService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PregledService;
import com.example.demo.service.ZahteviService;


@RestController
@RequestMapping(value = "/godisnji")

public class GodisnjiController {

	@Autowired
	private GodisnjiService godisnjiService;
	
	@Autowired
	private GodisnjiRepository godisnjiRepository;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private ZahteviService zahteviService;
	

	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private MedicinskaSestraService sestraService;
	
	@PostMapping(value = "/zahtevGodisnjiLekar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> dodajZahtevZaGodisnji(@RequestBody GodisnjiDTO godisnjiDTO,@PathVariable Long id) throws ParseException {
		
		Lekar lekar = lekarService.findOne(id);
		
		boolean flag = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datumOdDate = sdf.parse(godisnjiDTO.getDatumOd());
		Date datumDoDate = sdf.parse(godisnjiDTO.getDatumDo());

		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		
		for (Pregled pregled : pregledi) {
			String[] datumPregleda = pregled.getDatum().split("/");
			String datum = datumPregleda[2]+"-"+datumPregleda[1]+"-"+datumPregleda[0];
			Date datumPregledaDate = sdf.parse(datum);
			if (lekar.getId() == pregled.getLekar().getId() && pregled.getZavrsen() == false) {
				if (datumPregledaDate.compareTo(datumOdDate) >= 0 && datumPregledaDate.compareTo(datumDoDate) <= 0) {
					flag = true;
					break;
				}
			}
		}
		
		for (Operacija operacija : operacije) {
			String[] datumPregleda = operacija.getDatum().split("/");
			String datum = datumPregleda[2]+"-"+datumPregleda[1]+"-"+datumPregleda[0];
			Date datumPregledaDate = sdf.parse(datum);
			if (lekar.getId() == operacija.getLekariKlinike().get(0).getId() && operacija.getZavrsen() == false) {
				if (datumPregledaDate.compareTo(datumOdDate) >= 0 && datumPregledaDate.compareTo(datumDoDate) <= 0) {
					flag = true;
					break;
				}
			}
		}
		
		for (Zahtev zahtev : zahtevi) {
			String[] datumPregleda = zahtev.getDatum().split("/");
			String datum = datumPregleda[2]+"-"+datumPregleda[1]+"-"+datumPregleda[0];
			Date datumPregledaDate = sdf.parse(datum);
			if (lekar.getId() == zahtev.getLekar().getId()) {
				if (datumPregledaDate.compareTo(datumOdDate) >= 0 && datumPregledaDate.compareTo(datumDoDate) <= 0) {
					flag = true;
					break;
				}
			}
		}
		
		if(flag == false) {
			godisnjiDTO.setLekar(new LekarDTO(lekar));
			godisnjiService.dodajZahtevGodisnjiLekar(godisnjiDTO);
		}
		
		if(flag == true) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping(value = "/zahtevGodisnjiSestra/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('MEDICINSKASESTRA')")
	public ResponseEntity<?> dodajZahtevZaGodisnjiSestre(@RequestBody GodisnjiDTO godisnjiDTO,@PathVariable Long id) {
		
		MedicinskaSestra sestra = sestraService.findOne(id);
		godisnjiDTO.setSestra(new MedicinskaSestraDTO(sestra));
		godisnjiService.dodajZahtevGodisnjiSestra(godisnjiDTO);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/sviGodisnjiZahteviLekar/{id}")
	public ResponseEntity<?> getGodisnjiZahteviLekar(@PathVariable Long id) {

		List<Godisnji> godisnji = godisnjiService.findAll();
		List<GodisnjiDTO> godisnjiDTO = new ArrayList<>();
		AdminKlinike admin = adminKlinikeService.findOne(id);
		for (Godisnji god : godisnji) {
			if (god.getLekar() != null && admin.getKlinika() == god.getLekar().getKlinika() && god.getOdobren() == false) {
				godisnjiDTO.add(new GodisnjiDTO(god));
			}
		}

		return new ResponseEntity<>(godisnjiDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/sviGodisnjiZahteviSestra/{id}")
	public ResponseEntity<?> getGodisnjiZahteviSestra(@PathVariable Long id) {

		List<Godisnji> godisnji = godisnjiService.findAll();
		List<GodisnjiDTO> godisnjiDTO = new ArrayList<>();
		AdminKlinike admin = adminKlinikeService.findOne(id);
		for (Godisnji god : godisnji) {
			if (god.getSestra() != null && admin.getKlinika() == god.getSestra().getKlinika() && god.getOdobren() == false) {
				godisnjiDTO.add(new GodisnjiDTO(god));
			}
		}

		return new ResponseEntity<>(godisnjiDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/aktivirajGodisnjiLekar/{id}/{idAdmin}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> aktivirajPacijenta(@PathVariable Long id,@PathVariable Long idAdmin) throws MailException, InterruptedException {
		
		Godisnji godisnji = godisnjiService.findOne(id);
		AdminKlinike admin = adminKlinikeService.findOne(idAdmin);
		godisnji.setOdobren(true);
		godisnjiRepository.save(godisnji);
		if (godisnji == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		

	
		
		
		String message = "Godisnji odmor vam je prihvacen ";
		emailService.sendNotificaitionAsync((User) godisnji.getLekar(), message);
		
		
		List<Godisnji> sviGodisnji = godisnjiService.findAll();

		List<GodisnjiDTO> godisnjiDTO = new ArrayList<>();
		for (Godisnji god : sviGodisnji) {
			if(god.getLekar() != null && admin.getKlinika() == god.getLekar().getKlinika() && god.getOdobren() == false) {
				godisnjiDTO.add(new GodisnjiDTO(god));
			}
		}
		
		return new ResponseEntity<>(godisnjiDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/aktivirajGodisnjiSestra/{id}/{idAdmin}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> aktivirajGodSestra(@PathVariable Long id,@PathVariable Long idAdmin) throws MailException, InterruptedException {
		
		Godisnji godisnji = godisnjiService.findOne(id);
		AdminKlinike admin = adminKlinikeService.findOne(idAdmin);
		godisnji.setOdobren(true);
		godisnjiRepository.save(godisnji);
		if (godisnji == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		

	
		
		
		String message = "Godisnji odmor vam je prihvacen ";
		emailService.sendNotificaitionAsync((User) godisnji.getSestra(), message);
		
		
		List<Godisnji> sviGodisnji = godisnjiService.findAll();

		List<GodisnjiDTO> godisnjiDTO = new ArrayList<>();
		for (Godisnji god : sviGodisnji) {
			if(god.getLekar() != null && admin.getKlinika() == god.getLekar().getKlinika() && god.getOdobren() == false) {
				godisnjiDTO.add(new GodisnjiDTO(god));
			}
		}
		
		return new ResponseEntity<>(godisnjiDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izbrisiOdbijenZahtevZaGodisnjiLekar/{id}/{idAdmin}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<GodisnjiDTO>> izbrisiPacijenta(@RequestBody PorukaDTO poruka, @PathVariable String id,@PathVariable Long idAdmin) throws MailException, InterruptedException {

		Long idLong = Long.parseLong(id);
		Godisnji god = godisnjiService.findOne(idLong);
		AdminKlinike admin = adminKlinikeService.findOne(idAdmin);
		List<GodisnjiDTO> godisnjiDTO = new ArrayList<>();
		if (god != null) {
			
			godisnjiService.remove(god);
			List<Godisnji> godisnji = godisnjiService.findAll();

			for (Godisnji g : godisnji) {
				if(g.getLekar() != null && admin.getKlinika() == g.getLekar().getKlinika() && g.getOdobren() == false) {
					godisnjiDTO.add(new GodisnjiDTO(g));
					System.out.println(g.getId());
				}
			}
			emailService.sendNotificaitionAsync((User) god.getLekar(), poruka.getPoruka());
			return new ResponseEntity<>(godisnjiDTO,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(godisnjiDTO,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/izbrisiOdbijenZahtevZaGodisnjiSestra/{id}/{idAdmin}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<GodisnjiDTO>> izbrisiSestra(@RequestBody PorukaDTO poruka, @PathVariable String id,@PathVariable Long idAdmin) throws MailException, InterruptedException {

		Long idLong = Long.parseLong(id);
		Godisnji god = godisnjiService.findOne(idLong);
		AdminKlinike admin = adminKlinikeService.findOne(idAdmin);
		List<GodisnjiDTO> godisnjiDTO = new ArrayList<>();
		if (god != null) {
			
			godisnjiService.remove(god);
			List<Godisnji> godisnji = godisnjiService.findAll();

			for (Godisnji g : godisnji) {
				if(g.getSestra() != null && admin.getKlinika() == g.getSestra().getKlinika() && g.getOdobren() == false) {
					godisnjiDTO.add(new GodisnjiDTO(g));
					System.out.println(g.getId());
				}
			}
			emailService.sendNotificaitionAsync((User) god.getSestra(), poruka.getPoruka());
			return new ResponseEntity<>(godisnjiDTO,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(godisnjiDTO,HttpStatus.NOT_FOUND);
		}
	}
	
}
