package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Karton;
import com.example.demo.repository.KartonRepository;

@Service
public class KartonService {

	@Autowired
	private KartonRepository kartonRepository;

	public Karton getKartonPacijenta(Long id) {
		return kartonRepository.findByPacijentId(id);
	}
}
