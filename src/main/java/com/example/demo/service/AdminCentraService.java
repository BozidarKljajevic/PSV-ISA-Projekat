package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AdminCentraDTO;
import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.model.AdminCentra;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Authority;
import com.example.demo.repository.AdminCentraRepository;
import com.example.demo.repository.AuthorityRepository;

@Service
public class AdminCentraService {

	@Autowired
	private AdminCentraRepository adminCentraRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AdminCentra findOne(Long id) {
		return adminCentraRepository.findById(id).orElseGet(null);
	}
	
	public AdminCentra findOneM(String mail) {
		return adminCentraRepository.findByMail(mail);
	}

	public AdminCentraDTO dodajAdminaCentar(AdminCentraDTO adminDTO) {
		
		AdminCentra admin = new AdminCentra();
		
		Authority auth = this.authorityRepository.findByName("ADMINCENTRA");
		List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
		admin.setAuthorities(auths);
		admin.setIme(adminDTO.getIme());
		admin.setEnabled(true);
		admin.setPrezime(adminDTO.getPrezime());
		admin.setMail(adminDTO.getMail());
		admin.setAdresa(adminDTO.getAdresa());
		admin.setGrad(adminDTO.getGrad());
		admin.setDrzava(adminDTO.getDrzava());
		admin.setBrojTelefona(adminDTO.getBrojTelefona());
		admin.setSifra(adminDTO.getIme());
		admin.setPromenjenaSifra(false);
		admin.setSifra(passwordEncoder.encode("123"));
		adminCentraRepository.save(admin);
		
		AdminCentraDTO admindto=new AdminCentraDTO(admin);
		return admindto;
	}
}
