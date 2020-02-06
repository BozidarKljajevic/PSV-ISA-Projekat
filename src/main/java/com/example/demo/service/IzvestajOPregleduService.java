package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LekDTO;
import com.example.demo.model.Bolesti;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Karton;
import com.example.demo.model.Lek;
import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.Recept;
import com.example.demo.repository.BolestiRepository;
import com.example.demo.repository.IzvestajOPregleduRepository;
import com.example.demo.repository.KartonRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.ReceptRepository;


@Service
public class IzvestajOPregleduService {

	@Autowired
	private IzvestajOPregleduRepository izvestajRepository;
	
	@Autowired
	private BolestiRepository bolestiRepository;
	
	@Autowired
	private ReceptRepository receptRepository;
	
	@Autowired
	private KartonRepository kartonRepository;
	
	public IzvestajOPregledu findOne(Long id) {
		return izvestajRepository.findById(id).orElseGet(null);
	}

	public List<IzvestajOPregledu> findAll() {
		return izvestajRepository.findAll();
	}

	public void zavrsiIzvestaj(Pacijent pacijent, Lekar lekar, Bolesti bolest, List<Lek> lekovi) {
		
		IzvestajOPregledu izvestaj = new IzvestajOPregledu();
		Recept recept = new Recept();
		
		izvestaj.setPacijenta(pacijent);
		izvestaj.setLekara(lekar);
		izvestaj.setBolest(bolest);
		izvestaj.setLekoviIzvestaja(lekovi);
		if(lekovi.size()!=0) {
			recept.setLekoviRecepta(lekovi);
			receptRepository.save(recept);
			izvestaj.setRecept(recept);
		}
		
		Karton karton = kartonRepository.findByPacijentId(pacijent.getId());
		izvestaj.setKarton(karton);
		
		izvestajRepository.save(izvestaj);
	}

	public List<IzvestajOPregledu> getIzvestajeKartona(Long id) {
		
		return izvestajRepository.findByKartonId(id);
	}
	
	
	
}


