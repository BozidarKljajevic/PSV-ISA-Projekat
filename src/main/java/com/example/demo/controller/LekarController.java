package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;
import javax.websocket.server.PathParam;

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
import com.example.demo.dto.DogadjajDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.SifraDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pregled;
import com.example.demo.model.User;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.PregledService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "lekar")
public class LekarController {

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PregledService pregledService;
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	
	@PostMapping(value = "/izmeniGenerickuSifru/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> izmeniGenerickuSifru(@RequestBody SifraDTO sifra, @PathVariable Long id) {
		
		User user = userService.findOne(id);
		
		System.out.println(sifra.getSifra());
		System.out.println(id);
		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		userService.izmeniSifru(user, sifra.getSifra());

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/obavezeLekara/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public List<DogadjajDTO> obavezeLekara(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);
		
		List<Pregled> pregledi = pregledService.findAll();
		List<DogadjajDTO> datumi = new ArrayList<>();
		
		for(Pregled preg : pregledi)
		{
			if((preg.getLekar().getId()).equals(idLong)) {
				String[] datum = preg.getDatum().split("/");
				String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
				
				String[] vr = preg.getVreme().split(":");
				double sat = Double.parseDouble(vr[0]);
				double min = Double.parseDouble(vr[1]);
				
				double trajanjeMin = preg.getTrajanjePregleda() * 60;
				double trajanjeMinOstatak = trajanjeMin % 60;
				double trajanjeSat = trajanjeMin / 60;
				int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak)/60);
				double krajPregledaMin = min + trajanjeMinOstatak;
				
				System.out.println(sat+" "+krajPregledaSat+" "+min+" "+trajanjeMinOstatak);
				
				String minStr = "";
				if (krajPregledaMin == 0) {
					minStr = "00";
					krajPregledaSat++;
				}else {
					minStr = "30";
				}
				
				
				
				String start = datumStr+ ' ' +preg.getVreme();
				String end = datumStr +' '+ krajPregledaSat+":"+minStr;
				datumi.add(new DogadjajDTO(start, end));
			}
		};
		return datumi;
	}
	
	@PostMapping(value = "/dodajLekara/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LekarDTO> dodajLekara(@RequestBody LekarDTO lekarDTO1,@PathVariable String id) throws Exception {
		LekarDTO lekarDTO2 = new LekarDTO();
		
		User existUser = this.userService.findOne(lekarDTO1.getMail());
		if (existUser != null) {
			throw new Exception("Alredy exist");
		}
		
		Long idLong = Long.parseLong(id);
		lekarDTO2 = new LekarDTO(lekarService.dodaj(lekarDTO1,idLong));
		return new ResponseEntity<>(lekarDTO2, HttpStatus.OK);
	}

	
	@PostMapping(value = "/izmeniLekara", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<LekarDTO> izmeniPodatkeLekara(@RequestBody LekarDTO lekarDTO){
		
		try {
			lekarService.izmeni(lekarDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeniLekaraAdmin", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LekarDTO> izmeniPodatkeLekaraAdmin(@RequestBody LekarDTO lekarDTO){
		
		try {
			lekarService.izmeni(lekarDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciLekar/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);
		
		LekarDTO lekarDTO = new LekarDTO(lekar);
		
		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/postojeciLekarAdmin/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LekarDTO> getLekarAdmin(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);
		
		LekarDTO lekarDTO = new LekarDTO(lekar);
		
		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/izbrisiLekara/{id}")
	public ResponseEntity<?> deleteExam(@PathVariable Long id) {

		Lekar lekar = lekarService.findOne(id);
		List<LekarDTO> lekarDTO = new ArrayList<>();
		boolean flag = false;
		if (lekar != null) {
			List<Pregled> pregledi = pregledService.findAll();
			for(Pregled pr : pregledi) {
				if(pr.getLekar().getId() == id) {
					flag = true;
					break;
				}
			}
			
			if(flag == false) {
				lekarService.remove(id);
			}
				
			List<Lekar> lekari = lekarService.findAll();

			
			for (Lekar le : lekari) {
				if(le.getKlinika().getId() == lekar.getKlinika().getId()) {
				lekarDTO.add(new LekarDTO(le));
				}
			}
			return new ResponseEntity<>(lekarDTO,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping(value = "/postojeciSviLekari")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<LekarDTO>> getPostojeciNeaktivanPacijent() {
		
		List<Lekar> lekar = lekarService.findAll();

		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar le : lekar) {
			lekarDTO.add(new LekarDTO(le));
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciLekariKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<LekarDTO>> getPostojeciLekariKlinike(@PathVariable String id) {
		
		List<Lekar> lekar = lekarService.findAll();
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar le : lekar) {
			 if(le.getKlinika().getId() == adm.getKlinika().getId()) {
			lekarDTO.add(new LekarDTO(le));
			 }
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	
}
