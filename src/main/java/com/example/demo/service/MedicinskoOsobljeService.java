package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.MedicinskoOsobljeDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.MedicinskoOsobljeRepository;

@Service
public class MedicinskoOsobljeService {

	@Autowired
	private MedicinskoOsobljeRepository medicinskoOsobljeRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	public MedicinskoOsoblje findOne(Long id) {
		return medicinskoOsobljeRepository.findById(id).orElseGet(null);
	}
	
	public List<MedicinskoOsoblje> findAll() {
		return medicinskoOsobljeRepository.findAll();
	}
	
	public MedicinskoOsobljeDTO dodajMedicinskoOsoblje(MedicinskoOsobljeDTO medicinskoOsobljeDTO) {
		MedicinskoOsoblje medOsoblje = new MedicinskoOsoblje();
		
		medOsoblje.setIme(medicinskoOsobljeDTO.getIme());
		medOsoblje.setPrezime(medicinskoOsobljeDTO.getPrezime());
		medOsoblje.setMail(medicinskoOsobljeDTO.getMail());
		medOsoblje.setAdresa(medicinskoOsobljeDTO.getAdresa());
		medOsoblje.setGrad(medicinskoOsobljeDTO.getGrad());
		medOsoblje.setDrzava(medicinskoOsobljeDTO.getDrzava());
		medOsoblje.setBrojTelefona(medicinskoOsobljeDTO.getBrojTelefona());
		medOsoblje.setSifra(medicinskoOsobljeDTO.getIme());
		medOsoblje.setRadnoDo(medicinskoOsobljeDTO.getRadnoDo());
		medOsoblje.setRadnoOd(medicinskoOsobljeDTO.getRadnoOd());
		medOsoblje.setSifra(medicinskoOsobljeDTO.getIme());
		medOsoblje.setLekar(medicinskoOsobljeDTO.getLekar());
		//medOsoblje.setKlinika(klinikaRepository.getOne(medicinskoOsobljeDTO.getIdKlinike().getId()));
		medicinskoOsobljeRepository.save(medOsoblje);
		
		MedicinskoOsobljeDTO medicinskoOsobljeDTO2 = new MedicinskoOsobljeDTO(medOsoblje);
		return medicinskoOsobljeDTO2;
	}
	
	public void izmeniMedicinskoOsoblje(MedicinskoOsobljeDTO medicinskoOsobljeDTO) {
		MedicinskoOsoblje medicinskoOsoblje = medicinskoOsobljeRepository.findById(medicinskoOsobljeDTO.getId()).orElse(null);
		
		if(medicinskoOsoblje == null) {
			throw new ValidationException("Medicinsko osoblje sa zadatim id-jem nepostoji");
		}
		try {
			medicinskoOsoblje = medicinskoOsobljeRepository.getOne(medicinskoOsobljeDTO.getId());
			medicinskoOsoblje.setIme(medicinskoOsobljeDTO.getIme());
			medicinskoOsoblje.setPrezime(medicinskoOsobljeDTO.getPrezime());
			medicinskoOsoblje.setAdresa(medicinskoOsobljeDTO.getAdresa());
			medicinskoOsoblje.setBrojTelefona(medicinskoOsobljeDTO.getBrojTelefona());
			medicinskoOsoblje.setGrad(medicinskoOsobljeDTO.getGrad());
			medicinskoOsoblje.setDrzava(medicinskoOsobljeDTO.getDrzava());
			medicinskoOsoblje.setRadnoOd(medicinskoOsobljeDTO.getRadnoOd());
			medicinskoOsoblje.setRadnoDo(medicinskoOsobljeDTO.getRadnoDo());
			medicinskoOsobljeRepository.save(medicinskoOsoblje);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Medicinsko osoblje sa tim idijem nepostoji");
		}
	}
	
	
	public void remove(Long id) {
		medicinskoOsobljeRepository.deleteById(id);
	}
	
	public void izmeniMedicinskoOsobljeAdmin(MedicinskoOsobljeDTO medicinskoOsobljeDTO) {
		MedicinskoOsoblje medicinskoOsoblje = medicinskoOsobljeRepository.findById(medicinskoOsobljeDTO.getId()).orElse(null);
		
		if(medicinskoOsoblje == null) {
			throw new ValidationException("Medicinsko osoblje sa zadatim id-jem nepostoji");
		}
		try {
			medicinskoOsoblje = medicinskoOsobljeRepository.getOne(medicinskoOsobljeDTO.getId());
			medicinskoOsoblje.setIme(medicinskoOsobljeDTO.getIme());
			medicinskoOsoblje.setPrezime(medicinskoOsobljeDTO.getPrezime());
			medicinskoOsoblje.setAdresa(medicinskoOsobljeDTO.getAdresa());
			medicinskoOsoblje.setBrojTelefona(medicinskoOsobljeDTO.getBrojTelefona());
			medicinskoOsoblje.setGrad(medicinskoOsobljeDTO.getGrad());
			medicinskoOsoblje.setDrzava(medicinskoOsobljeDTO.getDrzava());
			medicinskoOsoblje.setRadnoOd(medicinskoOsobljeDTO.getRadnoOd());
			medicinskoOsoblje.setRadnoDo(medicinskoOsobljeDTO.getRadnoDo());
			medicinskoOsobljeRepository.save(medicinskoOsoblje);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Medicinsko osoblje sa tim idijem nepostoji");
		}
	}
}
