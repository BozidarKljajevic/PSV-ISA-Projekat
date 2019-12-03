package com.example.demo.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.NeaktivanPacijent;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.NeaktivanPacijentRepository;
import com.example.demo.repository.PacijentRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;
	
	@Autowired
	private NeaktivanPacijentRepository neaktivanPacijentRepository;
	
	public Pacijent findOne(Long id) {
		return pacijentRepository.findById(id).orElseGet(null);
	}
	
	public NeaktivanPacijent findOneN(Long id) {
		return neaktivanPacijentRepository.findById(id).orElseGet(null);
	}
	
	public NeaktivanPacijent findOneN(String mail) {
		return neaktivanPacijentRepository.findByMail(mail);
	}
	
	public List<NeaktivanPacijent> findAll() {
		return neaktivanPacijentRepository.findAll();
	}
	
	public void izmeniPacijenta(PacijentDTO pacijentDTO) {
		Pacijent pacijent = pacijentRepository.findById(pacijentDTO.getId()).orElseGet(null);
		
		if (pacijent == null) {
			throw new ValidationException("Pacijent sa zadatim ID-jem Zdravsvenog Osiguranika ne postoji");
		}
		
		try {
			pacijent = pacijentRepository.getOne(pacijentDTO.getId());
			pacijent.setIme(pacijentDTO.getIme());
			pacijent.setPrezime(pacijentDTO.getPrezime());
			pacijent.setAdresa(pacijentDTO.getAdresa());
			pacijent.setGrad(pacijentDTO.getGrad());
			pacijent.setDrzava(pacijentDTO.getDrzava());
			pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());
			
			pacijentRepository.save(pacijent);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Pacijent sa zadatim ID-jem Zdravsvenog Osiguranika ne postoji");
		}
	}
	
	public void remove(Long id) {
		neaktivanPacijentRepository.deleteById(id);
	}
	
	public void aktivirajPacijenta(NeaktivanPacijent neaktivanPacijent) {
		
		Pacijent pacijent = new Pacijent();
		
		pacijent.setMail(neaktivanPacijent.getMail());
		pacijent.setSifra(neaktivanPacijent.getSifra());
		pacijent.setIme(neaktivanPacijent.getIme());
		pacijent.setPrezime(neaktivanPacijent.getPrezime());
		pacijent.setAdresa(neaktivanPacijent.getAdresa());
		pacijent.setGrad(neaktivanPacijent.getGrad());
		pacijent.setDrzava(neaktivanPacijent.getDrzava());
		pacijent.setBrojTelefona(neaktivanPacijent.getBrojTelefona());
		
		neaktivanPacijentRepository.deleteById(neaktivanPacijent.getId());
		pacijentRepository.save(pacijent);
	}
}
