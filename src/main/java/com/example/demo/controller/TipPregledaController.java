package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.service.TipPregledaService;

@RestController
@RequestMapping(value = "tipPregleda")

public class TipPregledaController {

	@Autowired
	private TipPregledaService tipPregledaService;

}