package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.IzmenaSifreDTO;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.Authority;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Karton;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.KartonRepository;
import com.example.demo.repository.PacijentRepository;

@Service
public class PacijentService {

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private KartonRepository kartonRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Pacijent findOne(Long id) {
		return pacijentRepository.findById(id).orElse(null);
	}

	public Pacijent findOne(String mail) {
		return pacijentRepository.findByMail(mail);
	}

	public List<Pacijent> findAll() {
		return pacijentRepository.findAll();
	}

	public void remove(Pacijent pac) {
		pacijentRepository.delete(pac);
	}

	public Pacijent save(RegisterDTO pacijent) {
		Pacijent neaktivanPacijent = new Pacijent();

		neaktivanPacijent.setAdresa(pacijent.getAdresa());
		Authority auth = this.authorityRepository.findByName("PACIJENT");
		List<Authority> auths = new ArrayList<>();
		auths.add(auth);
		neaktivanPacijent.setAuthorities(auths);
		neaktivanPacijent.setBrojTelefona(pacijent.getBrojTelefona());
		neaktivanPacijent.setDrzava(pacijent.getDrzava());
		neaktivanPacijent.setEnabled(false);
		neaktivanPacijent.setPromenjenaSifra(true);
		neaktivanPacijent.setGrad(pacijent.getGrad());
		neaktivanPacijent.setIme(pacijent.getIme());
		neaktivanPacijent.setMail(pacijent.getMail());
		neaktivanPacijent.setPrezime(pacijent.getPrezime());
		neaktivanPacijent.setSifra(passwordEncoder.encode(pacijent.getSifra()));

		this.pacijentRepository.save(neaktivanPacijent);

		return neaktivanPacijent;
	}

	public void aktivirajPacijenta(Pacijent exisPacijent) {

		exisPacijent.setEnabled(true);
		this.pacijentRepository.save(exisPacijent);

		Karton karton = new Karton();
		karton.setPacijent(exisPacijent);
		karton.setDatumRodjenja("Potrebno uneti");
		karton.setDioptrija(0.0);
		karton.setKrvnaGrupa("Potreno uneti");
		karton.setPol("Potreno uneti");
		karton.setTezina(0.0);
		karton.setVisina(0.0);
		karton.setIzvestajiOPregledima(new HashSet<IzvestajOPregledu>());
		this.kartonRepository.save(karton);

	}

	public Pacijent izmeni(Pacijent pacijent, PacijentDTO pacijentDTO) {

		pacijent.setIme(pacijentDTO.getIme());
		pacijent.setPrezime(pacijentDTO.getPrezime());
		pacijent.setAdresa(pacijentDTO.getAdresa());
		pacijent.setGrad(pacijentDTO.getGrad());
		pacijent.setDrzava(pacijentDTO.getDrzava());
		pacijent.setBrojTelefona(pacijentDTO.getBrojTelefona());

		this.pacijentRepository.save(pacijent);

		return pacijent;
	}

	public boolean izmeniSifru(Pacijent pacijent, IzmenaSifreDTO sifra) {

		if (sifra.getNova().equals(sifra.getPotvrda())) {
			pacijent.setSifra(passwordEncoder.encode(sifra.getNova()));
			pacijentRepository.save(pacijent);
			return true;
		}
		return false;
	}
}
