package com.example.demo.controller;

import java.util.ArrayList;
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
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PorukaDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Godisnji;
import com.example.demo.model.Lekar;
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
	
	@PostMapping(value = "/zahtevGodisnjiLekar/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> dodajZahtevZaGodisnji(@RequestBody GodisnjiDTO godisnjiDTO,@PathVariable Long id) {
		
		GodisnjiDTO godisnjiDTO2 = new GodisnjiDTO();
		Lekar lekar = lekarService.findOne(id);
		String datumOd[] = godisnjiDTO.getDatumOd().split("-");
		String datumDo[] = godisnjiDTO.getDatumDo().split("-");
		int danOd =  Integer.parseInt(datumOd[2]);
		int mesecOd = Integer.parseInt(datumOd[1]);
		int godinaOd = Integer.parseInt(datumOd[0]);
		System.out.println(danOd);
		System.out.println(mesecOd);
		System.out.println(godinaOd);
		boolean flag = false;
		int danDo =  Integer.parseInt(datumDo[2]);
		int mesecDo = Integer.parseInt(datumDo[1]);
		int godinaDo = Integer.parseInt(datumDo[0]);
		System.out.println(danDo);
		System.out.println(mesecDo);
		System.out.println(godinaDo);
		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		String pregledDatum[];
		String zahtevDatum[];
		for (Pregled p : pregledi) {
			pregledDatum = p.getDatum().split("/");
			int pregledDan = Integer.parseInt(pregledDatum[0]);
			int pregledMesec = Integer.parseInt(pregledDatum[1]);
			int pregledGodina = Integer.parseInt(pregledDatum[2]);
			if(lekar.getId() == p.getLekar().getId() && pregledGodina >= godinaOd && pregledGodina <= godinaDo) {
				if(godinaOd != godinaDo && pregledGodina == godinaDo && pregledMesec <= mesecDo) {
					if(pregledMesec == mesecDo && pregledDan <= danDo) {
						flag = true;
					} 
					if(pregledMesec < mesecDo) {
						flag = true;
					}
				}
				else if(godinaOd != godinaDo && pregledGodina == godinaOd && pregledMesec >= mesecOd && pregledDan >= danOd) {
					if(pregledMesec == mesecOd && pregledDan >= danDo) {
						flag = true;
					} 
					if(pregledMesec > mesecOd) {
						flag = true;
					}
					
				}
				else if(godinaOd == godinaDo && pregledMesec >= mesecOd && pregledMesec <= mesecDo) {
					 if(mesecDo != mesecOd && pregledMesec == mesecDo && pregledDan <= danDo) {
						 flag = true;
					 }
					 else if(mesecDo != mesecOd && pregledMesec == mesecOd && pregledDan >= danOd) {
						 flag = true;
						 
					 }
					 else if(mesecDo == mesecOd && pregledDan >= danOd && pregledDan <= danDo) {
						 flag = true;
						 
					 }
				}
			}
		}
		
		for (Operacija o : operacije) {
			pregledDatum = o.getDatum().split("/");
			int pregledDan = Integer.parseInt(pregledDatum[0]);
			int pregledMesec = Integer.parseInt(pregledDatum[1]);
			int pregledGodina = Integer.parseInt(pregledDatum[2]);
			if(o.getLekariKlinike().contains(lekar) && pregledGodina >= godinaOd && pregledGodina <= godinaDo) {
				if(godinaOd != godinaDo && pregledGodina == godinaDo && pregledMesec <= mesecDo) {
					if(pregledMesec == mesecDo && pregledDan <= danDo) {
						flag = true;
					} 
					if(pregledMesec < mesecDo) {
						flag = true;
					}
				}
				else if(godinaOd != godinaDo && pregledGodina == godinaOd && pregledMesec >= mesecOd && pregledDan >= danOd) {
					if(pregledMesec == mesecOd && pregledDan >= danDo) {
						flag = true;
					} 
					if(pregledMesec > mesecOd) {
						flag = true;
					}
					
				}
				else if(godinaOd == godinaDo && pregledMesec >= mesecOd && pregledMesec <= mesecDo) {
					 if(mesecDo != mesecOd && pregledMesec == mesecDo && pregledDan <= danDo) {
						 flag = true;
					 }
					 else if(mesecDo != mesecOd && pregledMesec == mesecOd && pregledDan >= danOd) {
						 flag = true;
						 
					 }
					 else if(mesecDo == mesecOd && pregledDan >= danOd && pregledDan <= danDo) {
						 flag = true;
						 
					 }
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
				if(god.getLekar() != null && admin.getKlinika() == god.getLekar().getKlinika() && god.getOdobren() == false) {
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
	
}
