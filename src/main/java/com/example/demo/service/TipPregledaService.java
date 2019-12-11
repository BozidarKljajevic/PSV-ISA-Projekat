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
	
	
	public List<TipPregleda> findAll() {
		return tipPregledaRepository.findAll();
	}
	
}
