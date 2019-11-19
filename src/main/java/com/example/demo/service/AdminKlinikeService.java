package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdminKlinike;
import com.example.demo.repository.AdminKlinikeRepository;

@Service
public class AdminKlinikeService {

	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	public AdminKlinike findOne(Long id) {
		return adminKlinikeRepository.findById(id).orElseGet(null);
	}
}
