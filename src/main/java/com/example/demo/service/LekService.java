package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LekDTO;
import com.example.demo.model.Lek;
import com.example.demo.repository.LekRepository;


@Service
public class LekService {

	@Autowired
	private LekRepository lekRepository;
	
	
}
