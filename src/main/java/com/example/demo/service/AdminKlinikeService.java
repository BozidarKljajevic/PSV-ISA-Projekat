package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.model.AdminKlinike;

import com.example.demo.model.Authority;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.AdminKlinikeRepository;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.AdminKlinikeRepository;

import com.example.demo.repository.KlinikaRepository;

@Service
public class AdminKlinikeService {
	
	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AdminKlinike findOne(Long id) {
		return adminKlinikeRepository.findById(id).orElseGet(null);
	}
	
	public AdminKlinike findOneM(String mail) {
		return adminKlinikeRepository.findByMail(mail);
	}

	public AdminKlinike findOneMejl(String mejl) {
		return adminKlinikeRepository.findByMail(mejl);
	}
	
	public AdminKlinikeDTO dodajAdminaKlinike(AdminKlinikeDTO adminklinikeDTO) {
		AdminKlinike admin = new AdminKlinike();
		
		Authority auth = this.authorityRepository.findByName("PACIJENT");
		List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
		admin.setAuthorities(auths);
		admin.setIme(adminklinikeDTO.getIme());
		admin.setPrezime(adminklinikeDTO.getPrezime());
		admin.setMail(adminklinikeDTO.getMail());
		admin.setAdresa(adminklinikeDTO.getAdresa());
		admin.setGrad(adminklinikeDTO.getGrad());
		admin.setDrzava(adminklinikeDTO.getDrzava());
		admin.setBrojTelefona(adminklinikeDTO.getBrojTelefona());
		admin.setSifra(adminklinikeDTO.getIme());
		System.out.println(adminklinikeDTO.getKlinika().getId());
		admin.setKlinika(klinikaRepository.findById(adminklinikeDTO.getKlinika().getId()).orElse(null));
		admin.setSifra(passwordEncoder.encode("123"));
		adminKlinikeRepository.save(admin);
		
		AdminKlinikeDTO admindto=new AdminKlinikeDTO(admin);
		return admindto;
	}

	public void izmeniAdminKlinike(AdminKlinikeDTO adminKlinikeDTO) {
		AdminKlinike adminKlinike = adminKlinikeRepository.findById(adminKlinikeDTO.getId()).orElse(null);
		
		if(adminKlinike == null) {
			throw new ValidationException("Admin sa zadatim id-jem nepostoji");
		}
		try {
			adminKlinike = adminKlinikeRepository.getOne(adminKlinikeDTO.getId());
			adminKlinike.setIme(adminKlinikeDTO.getIme());
			adminKlinike.setPrezime(adminKlinikeDTO.getPrezime());
			adminKlinike.setAdresa(adminKlinikeDTO.getAdresa());
			adminKlinike.setBrojTelefona(adminKlinikeDTO.getBrojTelefona());
			adminKlinike.setGrad(adminKlinikeDTO.getGrad());
			adminKlinike.setDrzava(adminKlinikeDTO.getDrzava());
			adminKlinikeRepository.save(adminKlinike);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Admin sa tim idijem nepostoji");
		}
	}
}
