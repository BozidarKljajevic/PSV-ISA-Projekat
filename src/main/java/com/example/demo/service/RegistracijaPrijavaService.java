package com.example.demo.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.AdminCentra;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.model.NeaktivanPacijent;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.AdminCentraRepository;
import com.example.demo.repository.AdminKlinikeRepository;
import com.example.demo.repository.MedicinskoOsobljeRepository;
import com.example.demo.repository.NeaktivanPacijentRepository;
import com.example.demo.repository.PacijentRepository;

@Service
public class RegistracijaPrijavaService {

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private MedicinskoOsobljeRepository medicinskoOsobljeRepository;

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;

	@Autowired
	private AdminCentraRepository adminCentraRepository;

	@Autowired
	private NeaktivanPacijentRepository neaktivanPacijentRepository;

	public Pacijent findPacijent(String mail) {
		return pacijentRepository.findByMail(mail);
	}

	public MedicinskoOsoblje findMedicinskoOsoblje(String mail) {
		return medicinskoOsobljeRepository.findByMail(mail);
	}

	public AdminKlinike findAdminKlinike(String mail) {
		return adminKlinikeRepository.findByMail(mail);
	}

	public AdminCentra findAdminCentra(String mail) {
		return adminCentraRepository.findByMail(mail);
	}

	public void register(RegisterDTO pacijentDTO) {
		NeaktivanPacijent pacijent = new NeaktivanPacijent();
		Pacijent pacijentAktivan = pacijentRepository.findByMail(pacijentDTO.getMail());
		NeaktivanPacijent pacijentNeaktivan = neaktivanPacijentRepository.findByMail(pacijentDTO.getMail());

		if (pacijentAktivan != null || pacijentNeaktivan != null) {
			throw new ValidationException("Postoji korisnik sa datim Mailom");
		} else {
			try {
				pacijent.setMail(pacijentDTO.getMail());
				pacijent.setSifra(pacijentDTO.getSifra());
				pacijent.setIme(pacijentDTO.getIme());
				pacijent.setPrezime(pacijentDTO.getPrezime());
				pacijent.setAdresa(pacijentDTO.getAdresa());
				pacijent.setGrad(pacijentDTO.getGrad());
				pacijent.setDrzava(pacijentDTO.getDrzava());
				pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
				neaktivanPacijentRepository.save(pacijent);
			} catch (EntityNotFoundException e) {
				throw new ValidationException("Doslo je do greske");
			}
		}

	}
}
