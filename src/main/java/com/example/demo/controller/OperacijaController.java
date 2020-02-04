package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DogadjajDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.OperacijaDTO;
import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;

@RestController
@RequestMapping(value = "/operacija")
public class OperacijaController {
	
	@Autowired
	private OperacijaService operacijaService;

	@GetMapping(value = "/operacijePacijenta/{id}")
	public ResponseEntity<List<OperacijaDTO>> getOperacijePacijenta(@PathVariable Long id) {
		
		List<Operacija> operacije = operacijaService.getOperacijePacijenta(id);
		List<OperacijaDTO> operacijeDTO = new ArrayList<OperacijaDTO>();
		
		for (Operacija operacija : operacije) {
			operacijeDTO.add(new OperacijaDTO(operacija));
		}

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}
	
}
