package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TipPregledaDTO;

import com.example.demo.model.TipPregleda;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.TipPregledaRepository;

@Service
public class TipPregledaService {

	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	public TipPregleda findOne(Long id) {
		return tipPregledaRepository.findById(id).orElseGet(null);
	}
	
	public TipPregledaDTO dodajTipPregleda(TipPregledaDTO tipPregledaDTO) {
		TipPregleda tipPregleda = new TipPregleda();
		
		tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
		tipPregleda.setOznaka(tipPregledaDTO.getOznaka());
		tipPregleda.setKlinika(klinikaRepository.getOne(tipPregledaDTO.getKlinika().getId()));
		tipPregledaRepository.save(tipPregleda);
		
		TipPregledaDTO tipDTO=new TipPregledaDTO(tipPregleda);
		return tipDTO;
	}
	
	public void remove(Long id) {
		tipPregledaRepository.deleteById(id);
	}
	
	public void izmeniTipPregleda(TipPregledaDTO tipPregledaDTO) {
		TipPregleda tipPregleda = tipPregledaRepository.findById(tipPregledaDTO.getId()).orElse(null);
		
		if(tipPregleda == null) {
			throw new ValidationException("Tip pregleda sa zadatim id-jem nepostoji");
		}
		try {
			tipPregleda = tipPregledaRepository.getOne(tipPregledaDTO.getId());
			tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
			tipPregleda.setOznaka(tipPregledaDTO.getOznaka());
			tipPregledaRepository.save(tipPregleda);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Tip pregleda sa tim idijem nepostoji");
		}
	}
	

	public List<TipPregleda> findAll() {
		return tipPregledaRepository.findAll();
	}
}
