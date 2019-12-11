package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.repository.AdminKlinikeRepository;
import com.example.demo.repository.KlinikaRepository;

@Service
public class AdminKlinikeService {
	
	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	public AdminKlinike findOne(Long id) {
		return adminKlinikeRepository.findById(id).orElseGet(null);
	}
	
	
	public List<AdminKlinike> findAll() {
		return adminKlinikeRepository.findAll();
	}
	
	public AdminKlinike findOneMejl(String mejl) {
		return adminKlinikeRepository.findByMail(mejl);
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
