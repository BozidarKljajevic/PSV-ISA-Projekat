package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.Pregled;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.Zahtev;
import com.example.demo.repository.ZahteviRepository;




@Service
public class ZahteviService {

	@Autowired
	private ZahteviRepository zahteviRepository;
	
	public List<Zahtev> findAll() {
		return zahteviRepository.findAll();
	}
	
	public Zahtev findOne(Long id) {
		return zahteviRepository.findById(id).orElseGet(null);
	}
	
	public void dodajRezervisanuSalu(Zahtev zahtev) {
		zahteviRepository.save(zahtev);
	}
	
	public void izmeniZahtev(ZahtevDTO zahtevDTO) {
		Zahtev zahtev = zahteviRepository.findById(zahtevDTO.getId()).orElse(null);
		
		if(zahtev == null) {
			throw new ValidationException("Sala sa zadatim id-jem nepostoji");
		}
		try {
			zahtev = zahteviRepository.getOne(zahtevDTO.getId());
			zahtev.setDatum(zahtevDTO.getDatum());
			zahtev.setVreme(zahtevDTO.getVreme());
			
			zahteviRepository.save(zahtev);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Sala sa tim idijem nepostoji");
		}
	}

	public List<Zahtev> getZahteveOdLekara(Long id) {
		return zahteviRepository.findByLekarId(id);
	}

	public void remove(Long id) {
		zahteviRepository.deleteById(id);
		
	}
}
