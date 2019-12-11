package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.model.SalaKlinike;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.SalaKlinikeRepository;

@Service
public class SalaKlinikeService {

	@Autowired
	private SalaKlinikeRepository salaKlinikeRepository;

}