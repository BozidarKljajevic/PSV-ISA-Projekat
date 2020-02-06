package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pregled;
import com.example.demo.model.Zahtev;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.OperacijRepository;
import com.example.demo.repository.SalaKlinikeRepository;
import com.example.demo.repository.TipPregledaRepository;



@Service
public class OperacijaService {

	@Autowired
	private OperacijRepository operacijaRepository;
	
	@Autowired
	private SalaKlinikeRepository salaKlinikeRepository;
	
	@Autowired
	private LekarRepository lekarRepository;
	
	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	public Operacija findOne(Long id) {
		return operacijaRepository.findById(id).orElseGet(null);
	}

	public List<Operacija> findAll() {
		return operacijaRepository.findAll();
	}
	
	public void potvrdiZahtevZaOperaciju(Zahtev zahtev) {
		Operacija operacija = new Operacija();
		
		operacija.setDatum(zahtev.getDatum());
		operacija.setVreme(zahtev.getVreme());
		operacija.setCena(zahtev.getCena());
		//operacija.setLekariKlinike(lekarRepository.findById(zahtev.getLekar().getId()).orElse(null));
		operacija.setSala(salaKlinikeRepository.getOne(zahtev.getSala().getId()));
		operacija.setTrajanjeOperacije(zahtev.getTrajanjePregleda());
		operacija.setIdPacijenta(zahtev.getIdPacijenta());
		operacija.setTipOperacije(tipPregledaRepository.getOne(zahtev.getTipPregleda().getId()));
		operacija.setZavrsen(false);
		List<Lekar> lekariKlinike = new ArrayList<>();
		lekariKlinike.add(zahtev.getLekar());
		lekariKlinike.add(zahtev.getLekar1());
		lekariKlinike.add(zahtev.getLekar2());
		operacija.setLekariKlinike(lekariKlinike);
		
		operacijaRepository.save(operacija);
	}

	public List<Operacija> getOperacijePacijenta(Long id) {
		
		return operacijaRepository.findByIdPacijenta(id);
	}

	public List<Operacija> getOperacijeOdLekara(Long id) {
		List<Operacija> operacije = new ArrayList<Operacija>();
		
		for (Operacija operacija : operacijaRepository.findAll()) {
			for (Lekar lekar : operacija.getLekariKlinike()) {
				if (lekar.getId() == id) {
					operacije.add(operacija);
				}
			}
		}
		
		return operacije;
	}
	
	public void izbrisiOperaciju(Operacija operacija) {
		operacijaRepository.delete(operacija);		
	}
}
