package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AdminCentraService;

@RestController
@RequestMapping(value = "adminCentra")
@CrossOrigin(origins = "http://localhost:8081")
public class AdminCentraContreoller {
	
	@Autowired
	private AdminCentraService adminCentraService;
	
}
