package com.example.demo.service;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.repository.AdminKlinikeRepository;

@Service
public class AdminKlinikeService {

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	public AdminKlinike findOne(Long id) {
		return adminKlinikeRepository.findById(id).orElseGet(null);
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
