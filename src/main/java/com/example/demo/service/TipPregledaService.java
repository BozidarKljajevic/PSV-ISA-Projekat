package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.model.TipPregleda;
import com.example.demo.repository.AdminKlinikeRepository;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.TipPregledaRepository;

@Service
public class TipPregledaService {

	
	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	public List<TipPregleda> findAll() {
		return tipPregledaRepository.findAll();
	}
	
	public TipPregleda findOne(Long id) {
		return tipPregledaRepository.findById(id).orElse(null);
		}
	
	
	public void remove(Long id) {
		tipPregledaRepository.deleteById(id);
	}
	
	public TipPregledaDTO dodajTipPregleda(TipPregledaDTO tipPregledaDTO,Long id) {
		TipPregleda tipPregleda = new TipPregleda();
		AdminKlinike admin = adminKlinikeRepository.findById(id).orElse(null);
		tipPregleda.setNaziv(tipPregledaDTO.getNaziv());
		tipPregleda.setOznaka(tipPregledaDTO.getOznaka());
		tipPregleda.setCena(tipPregledaDTO.getCena());
		tipPregleda.setKlinika(admin.getKlinika());
		tipPregledaRepository.save(tipPregleda);
		
		TipPregledaDTO tipDTO=new TipPregledaDTO(tipPregleda);
		return tipDTO;
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
			tipPregleda.setCena(tipPregledaDTO.getCena());
			tipPregledaRepository.save(tipPregleda);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Tip pregleda sa tim idijem nepostoji");
		}
	}
}
