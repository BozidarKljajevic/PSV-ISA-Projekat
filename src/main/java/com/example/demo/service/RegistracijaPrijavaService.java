package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.AdminCentra;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.AdminCentraRepository;
import com.example.demo.repository.AdminKlinikeRepository;
import com.example.demo.repository.MedicinskoOsobljeRepository;
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
}
