package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Authority;

import com.example.demo.model.MedicinskaSestra;
import com.example.demo.repository.AdminKlinikeRepository;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.MedicinskaSestraRepository;
import com.example.demo.repository.TipPregledaRepository;

@Service
public class MedicinskaSestraService {

	@Autowired
	private MedicinskaSestraRepository medicinskaSestraRepository;
	
	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public MedicinskaSestra findOne(Long id) {
		return medicinskaSestraRepository.findById(id).orElse(null);
	}

	public List<MedicinskaSestra> findAll() {
		return medicinskaSestraRepository.findAll();
	}
	
	public void remove(Long id) {
		medicinskaSestraRepository.deleteById(id);
	}
	
	public MedicinskaSestra dodaj(MedicinskaSestraDTO medicinskaSestraDTO,Long id) {
		MedicinskaSestra medicinskaSestra = new MedicinskaSestra();
		AdminKlinike admin = adminKlinikeRepository.findById(id).orElse(null);
		medicinskaSestra.setAdresa(medicinskaSestraDTO.getAdresa());
		Authority auth = this.authorityRepository.findByName("MEDICINSKASESTRA");
		List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
	    medicinskaSestra.setAuthorities(auths);
		medicinskaSestra.setBrojTelefona(medicinskaSestraDTO.getBrojTelefona());
		medicinskaSestra.setDrzava(medicinskaSestraDTO.getDrzava());
		medicinskaSestra.setEnabled(true);
		medicinskaSestra.setPromenjenaSifra(false);
		medicinskaSestra.setGrad(medicinskaSestraDTO.getGrad());
		medicinskaSestra.setIme(medicinskaSestraDTO.getIme());
		medicinskaSestra.setMail(medicinskaSestraDTO.getMail());
		medicinskaSestra.setPrezime(medicinskaSestraDTO.getPrezime());
		medicinskaSestra.setSifra(passwordEncoder.encode("123"));
		medicinskaSestra.setKlinika(admin.getKlinika());
		medicinskaSestra.setRadnoDo(medicinskaSestraDTO.getRadnoDo());
		medicinskaSestra.setRadnoOd(medicinskaSestraDTO.getRadnoOd());
		
		this.medicinskaSestraRepository.save(medicinskaSestra);
		
		return medicinskaSestra;
	}
	
	public void izmeni(MedicinskaSestraDTO medicinskaSestraDTO) {
		MedicinskaSestra medicinskaSestra = medicinskaSestraRepository.findById(medicinskaSestraDTO.getId()).orElse(null);
		
		if(medicinskaSestra == null) {
			throw new ValidationException("Medicinsko osoblje sa zadatim id-jem nepostoji");
		}
		try {
			medicinskaSestra = medicinskaSestraRepository.getOne(medicinskaSestraDTO.getId());
			medicinskaSestra.setIme(medicinskaSestraDTO.getIme());
			medicinskaSestra.setPrezime(medicinskaSestraDTO.getPrezime());
			medicinskaSestra.setAdresa(medicinskaSestraDTO.getAdresa());
			medicinskaSestra.setBrojTelefona(medicinskaSestraDTO.getBrojTelefona());
			medicinskaSestra.setGrad(medicinskaSestraDTO.getGrad());
			medicinskaSestra.setDrzava(medicinskaSestraDTO.getDrzava());
			medicinskaSestra.setRadnoOd(medicinskaSestraDTO.getRadnoOd());
			medicinskaSestra.setRadnoDo(medicinskaSestraDTO.getRadnoDo());
			medicinskaSestraRepository.save(medicinskaSestra);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Medicinsko osoblje sa tim idijem nepostoji");
		}
	}
}
