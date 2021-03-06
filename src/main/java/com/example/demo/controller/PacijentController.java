package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.IzmenaSifreDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.PorukaDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SifraDTO;
import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.User;
import com.example.demo.service.EmailService;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.PregledService;

@RestController
@RequestMapping(value = "/pacijent")
public class PacijentController {

	@Autowired
	private PacijentService pacijentService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	@GetMapping(value = "/dozvola/{idPac}/{id}")
	//@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> dozvola(@PathVariable Long idPac,@PathVariable Long id) {
		
		
		Lekar lekar = lekarService.findOne(id);
		
		List<Pregled> pregledi = pregledService.findAll();
		
		List<Operacija> operacije = operacijaService.findAll();
		
        Boolean prikazi = false;
		for (Pregled pregled : pregledi) {
			if (pregled.getIdPacijenta() != null && pregled.getLekar().getId() == id
					&& pregled.getIdPacijenta() == idPac) {

					prikazi = true;
			}
		}
		
		for (Operacija o : operacije) {
			if (o.getIdPacijenta() != null && o.getLekariKlinike().contains(lekar)
					&& o.getIdPacijenta() == idPac ) {

					prikazi = true;
			}
		}

		return new ResponseEntity<>(prikazi, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/preuzmi/{id}")
	//@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> getPostojeciPacijent(@PathVariable Long id) {
		
		Pacijent pacijent = (Pacijent) pacijentService.findOne(id);
		
		if (pacijent == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		PacijentDTO pacijentDTO = new PacijentDTO(pacijent);

		return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/preuzmiPac/{id}")
	//@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> getPostojeciPacijentLekar(@PathVariable Long id) {
		
		Pregled pregled = (Pregled) pregledService.findOne(id);
		
		List<Pacijent> pacijenti = pacijentService.findAll();
		for (Pacijent pacijent : pacijenti) {
			if(pacijent.getId() == pregled.getIdPacijenta()) {
				PacijentDTO pacijentDTO = new PacijentDTO(pacijent);
				return new ResponseEntity<>(pacijentDTO, HttpStatus.OK);
			}
		}
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	
	}
	
	@PostMapping(value = "/izmeni")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> izmeniPacijenta(@RequestBody PacijentDTO pacijentDTO) {
		
		Pacijent pacijent = (Pacijent) pacijentService.findOne(pacijentDTO.getId());
		
		if (pacijent == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		Pacijent izmenjenPacijent = pacijentService.izmeni(pacijent, pacijentDTO);

		PacijentDTO newPacijentDTO = new PacijentDTO(izmenjenPacijent);

		return new ResponseEntity<>(newPacijentDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/postojeciNeaktivanPacijent")
	public ResponseEntity<List<PacijentDTO>> getPostojeciNeaktivanPacijent() {
		
		List<Pacijent> pacijenti = pacijentService.findAll();

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent pacijent : pacijenti) {
			if(pacijent.isEnabled()==false) {
				pacijentiDTO.add(new PacijentDTO(pacijent));
			}
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciAktivanPacijent")
	public ResponseEntity<List<PacijentDTO>> getPostojeciAktivanPacijent() {
		
		List<Pacijent> pacijenti = pacijentService.findAll();

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for (Pacijent pacijent : pacijenti) {
			if(pacijent.isEnabled()==true) {
				pacijentiDTO.add(new PacijentDTO(pacijent));
			}
		}

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/pacijentiKlinike/{id}")
	public ResponseEntity<List<PacijentDTO>> getPacijentiKlinike(@PathVariable Long id) {
		Lekar lekar = lekarService.findOne(id);
		Boolean flag = false;
		List<Pacijent> pacijenti = pacijentService.findAll();
		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<Pregled> preglediKlinike = new ArrayList<>();
		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		for(Pregled p : pregledi) {
			if(p.getLekar().getKlinika() == lekar.getKlinika()) {
				Pacijent pac = pacijentService.findOne(p.getIdPacijenta());
				for(PacijentDTO pacijent : pacijentiDTO) {
					if(pacijent.getId() == pac.getId()) {
						flag = true;
						break;
					}
				}
				if(flag == false) {
					pacijentiDTO.add(new PacijentDTO(pac));
				}
				
			}
		}
		
		
		for(Operacija o : operacije) {
			for(Lekar l : o.getLekariKlinike()) {
				if(l.getKlinika().getId() == lekar.getKlinika().getId()) {
					Pacijent pac = pacijentService.findOne(o.getIdPacijenta());
					for(PacijentDTO pacijent : pacijentiDTO) {
						
						if(pacijent.getId() == pac.getId()) {
							flag = true;
							break;
						}
					}
					if(flag == false) {
						pacijentiDTO.add(new PacijentDTO(pac));
					}
					
					
				}
				
			}
		}
		
		

		return new ResponseEntity<>(pacijentiDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/ibrisiNeaktivnogPacijenta/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<List<PacijentDTO>> izbrisiPacijenta(@RequestBody PorukaDTO poruka, @PathVariable String id) throws MailException, InterruptedException {

		Long idLong = Long.parseLong(id);
		Pacijent pac = pacijentService.findOne(idLong);

		List<PacijentDTO> pacijentiDTO = new ArrayList<>();
		if (pac != null) {
			
			pacijentService.remove(pac);
			List<Pacijent> pacijenti = pacijentService.findAll();

			for (Pacijent pacijent : pacijenti) {
				if(pacijent.isEnabled()==false) {
					pacijentiDTO.add(new PacijentDTO(pacijent));
				}
			}
			emailService.sendNotificaitionAsync((User)pac, poruka.getPoruka());
			return new ResponseEntity<>(pacijentiDTO,HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(pacijentiDTO,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/promeniSifruPacijent/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> promeniSifruPacijenta(@PathVariable Long id, @RequestBody IzmenaSifreDTO sifra)
	{
		Pacijent pacijent = pacijentService.findOne(id);
		
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(pacijent.getMail(),
						sifra.getStara()));
		
		User user = (User) authentication.getPrincipal();
		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		boolean success = pacijentService.izmeniSifru(pacijent, sifra);
		
		if (success) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
