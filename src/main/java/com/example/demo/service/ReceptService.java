package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.IzvestajOPregleduDTO;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Lek;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Recept;
import com.example.demo.repository.IzvestajOPregleduRepository;
import com.example.demo.repository.ReceptRepository;


@Service
public class ReceptService {

	@Autowired
	private ReceptRepository receptRepository;
	
	@Autowired
	private IzvestajOPregleduRepository izvestajOPregleduRepository;
	
	public Recept findOne(Long id) {
		return receptRepository.findById(id).orElseGet(null);
	}

	public List<Recept> findAll() {
		return receptRepository.findAll();
	}
/*
	public void receptIzIzvestaja(List<Lek> lekovi, IzvestajOPregleduDTO izvestaj) {
		
		Recept recept = new Recept();
		
		recept.setLekoviRecepta(lekovi);
		IzvestajOPregledu izvestaj1 = izvestajOPregleduRepository.findById(izvestaj.getId()).orElse(null);
		recept.setIzvestaj(izvestaj1);
		
		receptRepository.save(recept);
		
	}
*/

	public void sestraOverila(Recept recept, MedicinskaSestra sestra) {
		
		recept.setSestra(sestra);
		recept.setOveren(true);
		receptRepository.save(recept);
		
	}
}
