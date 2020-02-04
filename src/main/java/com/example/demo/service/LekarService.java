package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Authority;
import com.example.demo.model.Lekar;
import com.example.demo.model.OcenaLekar;
import com.example.demo.model.Pacijent;
import com.example.demo.repository.AdminKlinikeRepository;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.OcenaLekarRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.TipPregledaRepository;

@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepository;
	
	@Autowired
	private AdminKlinikeRepository adminKlinikeRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepository;
	
	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	@Autowired
	private PacijentRepository pacijentRepository;
	
	@Autowired
	private OcenaLekarRepository ocenaLekarRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public Lekar findOne(Long id) {
		return lekarRepository.findById(id).orElse(null);
	}

	public Lekar findOne(String mail) {
		return lekarRepository.findByMail(mail);
	}
	
	
	public List<Lekar> findAll() {
		return lekarRepository.findAll();
	}
	
	public void remove(Long id) {
		lekarRepository.deleteById(id);
	}
	
	public Lekar dodaj(LekarDTO lekarDTO,Long id) {
		Lekar lekar = new Lekar();
		AdminKlinike admin = adminKlinikeRepository.findById(id).orElse(null);
		lekar.setAdresa(lekarDTO.getAdresa());
		Authority auth = this.authorityRepository.findByName("LEKAR");
		List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
	    lekar.setAuthorities(auths);
		lekar.setBrojTelefona(lekarDTO.getBrojTelefona());
		lekar.setDrzava(lekarDTO.getDrzava());
		lekar.setEnabled(true);
		lekar.setPromenjenaSifra(false);
		lekar.setGrad(lekarDTO.getGrad());
		lekar.setIme(lekarDTO.getIme());
		lekar.setMail(lekarDTO.getMail());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setSifra(passwordEncoder.encode("123"));
		lekar.setKlinika(admin.getKlinika());
		lekar.setRadnoDo(lekarDTO.getRadnoDo());
		lekar.setRadnoOd(lekarDTO.getRadnoOd());
		lekar.setTipPregleda(tipPregledaRepository.getOne(lekarDTO.getTipPregleda().getId()));
		lekar.setOcena("0");
		
		this.lekarRepository.save(lekar);
		
		return lekar;
	}

	
	public void izmeni(LekarDTO lekarDTO) {
		Lekar lekar = lekarRepository.findById(lekarDTO.getId()).orElse(null);
		
		if(lekar == null) {
			throw new ValidationException("Medicinsko osoblje sa zadatim id-jem nepostoji");
		}
		try {
			lekar = lekarRepository.getOne(lekarDTO.getId());
			lekar.setIme(lekarDTO.getIme());
			lekar.setPrezime(lekarDTO.getPrezime());
			lekar.setAdresa(lekarDTO.getAdresa());
			lekar.setBrojTelefona(lekarDTO.getBrojTelefona());
			lekar.setGrad(lekarDTO.getGrad());
			lekar.setDrzava(lekarDTO.getDrzava());
			lekar.setRadnoOd(lekarDTO.getRadnoOd());
			lekar.setRadnoDo(lekarDTO.getRadnoDo());
			lekarRepository.save(lekar);
		} catch (EntityNotFoundException e) {
			throw new ValidationException("Medicinsko osoblje sa tim idijem nepostoji");
		}
	}

	public List<Lekar> sviLekariKlinike(Long id) {
		return lekarRepository.findByKlinikaId(id);
	}

	public void oceniLekara(Long idPacijent, Long idLekar, Integer ocena) {
		Pacijent pacijent = pacijentRepository.findById(idPacijent).orElse(null);
		Lekar lekar = lekarRepository.findById(idLekar).orElse(null);
		
		List<OcenaLekar> sveOceneLekara = ocenaLekarRepository.findAll();
		OcenaLekar ocenaLekara = new OcenaLekar();
		
		for (OcenaLekar ocenaLekar : sveOceneLekara) {
			if (ocenaLekar.getLekar().getId() == idLekar && ocenaLekar.getPacijent().getId() == idPacijent) {
				ocenaLekara = ocenaLekar;
				break;
			}
		}
		
		ocenaLekara.setLekar(lekar);
		ocenaLekara.setPacijent(pacijent);
		ocenaLekara.setOcena(ocena);
		
		ocenaLekarRepository.save(ocenaLekara);
		
		Double prosecna_ocena = 0.0;
		int br_ocena = 0;
		
		sveOceneLekara = ocenaLekarRepository.findAll();
		
		for (OcenaLekar ocenaLekar : sveOceneLekara) {
			if (ocenaLekar.getLekar().getId() == idLekar) {
				prosecna_ocena += ocenaLekar.getOcena();
				br_ocena++;
			}
		}
		
		if (br_ocena != 0) {
			prosecna_ocena /= br_ocena;
		}
		
		lekar.setOcena(prosecna_ocena+"");
		lekarRepository.save(lekar);
		
	}

	public OcenaLekar getOcena(Long idPacijent, Long idLekar) {
		
		List<OcenaLekar> sveOceneLekara = ocenaLekarRepository.findAll();
		
		for (OcenaLekar ocenaLekar : sveOceneLekara) {
			if (ocenaLekar.getLekar().getId() == idLekar && ocenaLekar.getPacijent().getId() == idPacijent) {
				return ocenaLekar;
			}
		}
		
		return null;
	}
	
}
