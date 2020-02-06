package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.Klinika;
import com.example.demo.model.Pregled;
import com.example.demo.model.Zahtev;
import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.PacijentRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.repository.SalaKlinikeRepository;
import com.example.demo.repository.TipPregledaRepository;
import com.example.demo.repository.ZahteviRepository;

@Service
public class PregledService {

	@Autowired
	private PregledRepository pregledRepository;

	@Autowired
	private ZahteviRepository zahteviRepository;

	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private SalaKlinikeRepository salaKlinikeRepository;

	@Autowired
	private TipPregledaRepository tipPregledaRepository;

	

	
	public PregledDTO dodajPregled(PregledDTO pregledDTO) {
		Pregled pregled = new Pregled();

		pregled.setDatum(pregledDTO.getDatum());
		pregled.setVreme(pregledDTO.getVreme());
		Double cenaP = Double.parseDouble(pregledDTO.getTipPregleda().getCena());
		pregled.setCena(cenaP);
		pregled.setLekar(lekarRepository.findById(pregledDTO.getLekar().getId()).orElse(null));
		pregled.setSala(salaKlinikeRepository.getOne(pregledDTO.getSala().getId()));
		pregled.setTipPregleda(tipPregledaRepository.getOne(pregledDTO.getTipPregleda().getId()));
		pregled.setTrajanjePregleda(pregledDTO.getTrajanjePregleda());
		pregled.setIdPacijenta(null);
		pregled.setZavrsen(false);
		pregledRepository.save(pregled);

		PregledDTO pregleddto = new PregledDTO(pregled);
		return pregleddto;
	}

	public void potvrdiZahtevZaPregled(Zahtev zahtev) {
		Pregled pregled = new Pregled();
		
		pregled.setDatum(zahtev.getDatum());
		pregled.setVreme(zahtev.getVreme());
		pregled.setCena(zahtev.getCena());
		pregled.setLekar(lekarRepository.findById(zahtev.getLekar().getId()).orElse(null));
		pregled.setSala(salaKlinikeRepository.getOne(zahtev.getSala().getId()));
		pregled.setTipPregleda(tipPregledaRepository.getOne(zahtev.getTipPregleda().getId()));
		pregled.setTrajanjePregleda(zahtev.getTrajanjePregleda());
		pregled.setIdPacijenta(zahtev.getIdPacijenta());
		pregled.setZavrsen(false);
		
		pregledRepository.save(pregled);
	}

	public Pregled findOne(Long id) {
		return pregledRepository.findById(id).orElseGet(null);
	}

	public List<Pregled> findAll() {
		return pregledRepository.findAll();
	}

	public void zakaziPregled(Pregled pregled, Long idPacijenta) {

		pregled.setIdPacijenta(idPacijenta);
		pregledRepository.save(pregled);
	}

	public void dodajZahtev(ZahtevDTO zahtevDTO) {

		Zahtev zahtev = new Zahtev();

		String[] yyyymmdd = zahtevDTO.getDatum().split("-");
		String datum = yyyymmdd[2] + "/" + yyyymmdd[1] + "/" + yyyymmdd[0];
		zahtev.setLekar(lekarRepository.findById(zahtevDTO.getLekar().getId()).orElse(null));
		zahtev.setIdPacijenta(zahtevDTO.getIdPacijenta());
		zahtev.setCena(zahtevDTO.getCena());
		zahtev.setDatum(datum);
		zahtev.setVreme(zahtevDTO.getVreme());
		zahtev.setTrajanjePregleda(zahtevDTO.getTrajanjePregleda());
		zahtev.setIzbor(zahtevDTO.isIzbor());
		zahtev.setTipPregleda(tipPregledaRepository.findById(zahtevDTO.getTipPregleda().getId()).orElse(null));

		zahteviRepository.save(zahtev);

	}

	public List<Pregled> getPregledeOdLekara(Long id) {
		return pregledRepository.findByLekarId(id);
	}


	public void izrisiPregled(Pregled pregled) {
		pregledRepository.delete(pregled);		
	}

	public void zavrsiPregled(Pregled pregled) {
		pregled.setZavrsen(true);
		pregledRepository.save(pregled);
	}


}
