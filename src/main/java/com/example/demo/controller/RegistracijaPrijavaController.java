package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.AdminCentra;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.model.Pacijent;
import com.example.demo.service.RegistracijaPrijavaService;

@RestController
@RequestMapping(value = "prijava")
@CrossOrigin(origins = "http://localhost:8081")
public class RegistracijaPrijavaController {

	@Autowired
	private RegistracijaPrijavaService registracijaPrijavaService;

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponseDTO> Login(@RequestBody LoginDTO user) {

		Pacijent pacijent = registracijaPrijavaService.findPacijent(user.getMail());
		MedicinskoOsoblje medicinskoOsoblje = registracijaPrijavaService.findMedicinskoOsoblje(user.getMail());
		AdminKlinike adminKlinike = registracijaPrijavaService.findAdminKlinike(user.getMail());
		AdminCentra adminCentra = registracijaPrijavaService.findAdminCentra(user.getMail());

		LoginResponseDTO responseUser = new LoginResponseDTO();
		responseUser.setMail(user.getMail());

		if (pacijent == null && medicinskoOsoblje == null && adminKlinike == null && adminCentra == null) {
			System.out.println("Email je nepostojeci");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		if (pacijent != null) {
			if (user.getSifra().equals(pacijent.getSifra())) {
				responseUser.setIme(pacijent.getIme());
				responseUser.setPrezime(pacijent.getPrezime());
				responseUser.setUloga("Pacijent");
			}
		} else if (medicinskoOsoblje != null) {
			if (user.getSifra().equals(medicinskoOsoblje.getLozinka())) {
				responseUser.setIme(medicinskoOsoblje.getIme());
				responseUser.setPrezime(medicinskoOsoblje.getPrezime());
				responseUser.setUloga("MedicinskoOsoblje");
			}
		} else if (adminKlinike != null) {
			if (user.getSifra().equals(adminKlinike.getLozinka())) {
				responseUser.setIme(adminKlinike.getIme());
				responseUser.setPrezime(adminKlinike.getPrezime());
				responseUser.setUloga("AdminKlinike");
			} 
		} else if (adminCentra != null) {
			if (user.getSifra().equals(adminCentra.getLozinka())) {
				responseUser.setIme("Admin");
				responseUser.setPrezime("Admirovic");
				responseUser.setUloga("AdminCentra");
			}
		} else {
			System.out.println("Sifra nije ispravna");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		System.out.println("Uspesno ste se logovali");
		return new ResponseEntity<>(responseUser, HttpStatus.OK);
	}

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void Register(@RequestBody RegisterDTO user) {

	}
}
