package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ZahteviService;



@RestController
@RequestMapping(value = "zahtevi")

public class ZahteviController {

	@Autowired
	private ZahteviService zahteviService;
	
}
