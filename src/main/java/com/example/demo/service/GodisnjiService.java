package com.example.demo.service;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.GodisnjiDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.Godisnji;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Zahtev;
import com.example.demo.repository.GodisnjiRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.MedicinskaSestraRepository;
import com.example.demo.repository.ZahteviRepository;

@Service
public class GodisnjiService {

	@Autowired
	private GodisnjiRepository godisnjiRepository;
	
	@Autowired
	private LekarRepository lekarRepository;
	
	@Autowired
	private MedicinskaSestraRepository sestraRepository;
	
	public List<Godisnji> findAll() {
		return godisnjiRepository.findAll();
	}
	
	public Godisnji findOne(Long id) {
		return godisnjiRepository.findById(id).orElseGet(null);
		
	}
	
	public void remove(Godisnji god) {
		godisnjiRepository.delete(god);
	}
	
	public void dodajZahtevGodisnjiLekar(GodisnjiDTO godisnjiDTO) {

		Godisnji godisnji = new Godisnji();

		godisnji.setLekar(lekarRepository.findById(godisnjiDTO.getLekar().getId()).orElse(null));
		
		godisnji.setDatumOd(godisnjiDTO.getDatumOd());
		godisnji.setDatumDo(godisnjiDTO.getDatumDo());
		godisnji.setOdobren(false);
		

		godisnjiRepository.save(godisnji);

	}

	public void dodajZahtevGodisnjiSestra(GodisnjiDTO godisnjiDTO) {
		
		Godisnji godisnji = new Godisnji();

		godisnji.setSestra(sestraRepository.findById(godisnjiDTO.getSestra().getId()).orElse(null));
		
		godisnji.setDatumOd(godisnjiDTO.getDatumOd());
		godisnji.setDatumDo(godisnjiDTO.getDatumDo());
		godisnji.setOdobren(false);
		

		godisnjiRepository.save(godisnji);
		
	}

	
}

