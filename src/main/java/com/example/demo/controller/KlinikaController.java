package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminKlinikeDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Godisnji;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pregled;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.Zahtev;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.GodisnjiService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PregledService;
import com.example.demo.service.TipPregledaService;
import com.example.demo.service.ZahteviService;

@RestController
@RequestMapping(value = "klinika")
public class KlinikaController {

	@Autowired
	private KlinikaService klinikaService;
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private OperacijaService operacijaService;
	
	
	@Autowired
	private ZahteviService zahteviService;
	
	@Autowired
	private TipPregledaService tipPregledaService;
	
	@Autowired
	private GodisnjiService godisnjiService;
	
	@GetMapping(value = "/sveKlinike")
	//@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<List<KlinikaDTO>> getSveKlinike() {
		
		List<Klinika> klinike = klinikaService.findAll();

		List<KlinikaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika klinika : klinike) {
			klinikeDTO.add(new KlinikaDTO(klinika));
		}

		return new ResponseEntity<>(klinikeDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/dodajKliniku", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMINCENTRA')")
	public ResponseEntity<KlinikaDTO> dodajKliniku(@RequestBody KlinikaDTO klinikaDTO) {
		
		KlinikaDTO klinikadto = new KlinikaDTO();
		try {
			klinikadto = klinikaService.dodajKliniku(klinikaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(klinikadto, HttpStatus.OK);
	}

	
	@GetMapping(value = "/getOcena/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Double> getOcenaK(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Klinika klinika = klinikaService.findOne(adminKlinikeService.findOne(idLong).getKlinika().getId());
		
		KlinikaDTO klinikaDTO = new KlinikaDTO(klinika);
		Double ocena = klinikaDTO.getOcena();
		return new ResponseEntity<>(ocena, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPrihod/{id}/{datumOdd}/{datumDoo}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Double> getPrihod(@PathVariable String id,@PathVariable String datumOdd,@PathVariable String datumDoo) {
		
		Long idLong = Long.parseLong(id);
		String datumOd[] = datumOdd.split("-");
		String datumDo[] = datumDoo.split("-");
		int danOd =  Integer.parseInt(datumOd[2]);
		int mesecOd = Integer.parseInt(datumOd[1]);
		int godinaOd = Integer.parseInt(datumOd[0]);
		
		boolean flag = false;
		int danDo =  Integer.parseInt(datumDo[2]);
		int mesecDo = Integer.parseInt(datumDo[1]);
		int godinaDo = Integer.parseInt(datumDo[0]);
		
		Klinika klinika = klinikaService.findOne(adminKlinikeService.findOne(idLong).getKlinika().getId());
		List<Pregled> pregledi = pregledService.findAll(); 
		List<Operacija> operacije = operacijaService.findAll();
		Double prihod = 0.0;
		String pregledDatum[];
		for(Pregled p : pregledi) {
			pregledDatum = p.getDatum().split("/");
			int pregledDan = Integer.parseInt(pregledDatum[0]);
			int pregledMesec = Integer.parseInt(pregledDatum[1]);
			int pregledGodina = Integer.parseInt(pregledDatum[2]);
			if(p.getLekar().getKlinika().getId() == klinika.getId() && p.getZavrsen() == true  && pregledGodina >= godinaOd && pregledGodina <= godinaDo) {
				if(godinaOd != godinaDo && pregledGodina == godinaDo && pregledMesec <= mesecDo) {
					if(pregledMesec == mesecDo && pregledDan <= danDo) {
						flag = true;
					} 
					if(pregledMesec < mesecDo) {
						flag = true;
					}
				}
				else if(godinaOd != godinaDo && pregledGodina == godinaOd && pregledMesec >= mesecOd && pregledDan >= danOd) {
					if(pregledMesec == mesecOd && pregledDan >= danDo) {
						flag = true;
					} 
					if(pregledMesec > mesecOd) {
						flag = true;
					}
					
				}
				else if(godinaOd == godinaDo && pregledMesec >= mesecOd && pregledMesec <= mesecDo) {
					 if(mesecDo != mesecOd && pregledMesec == mesecDo && pregledDan <= danDo) {
						 flag = true;
					 }
					 else if(mesecDo != mesecOd && pregledMesec == mesecOd && pregledDan >= danOd) {
						 flag = true;
						 
					 }
					 else if(mesecDo == mesecOd && pregledDan >= danOd && pregledDan <= danDo) {
						 flag = true;
						 
					 }
				}
				
			}
			if(flag == true) {
				System.out.println(prihod);
				prihod += p.getCena();
				flag = false;
			}
		}
		
		for(Operacija o : operacije) {
			pregledDatum = o.getDatum().split("/");
			int pregledDan = Integer.parseInt(pregledDatum[0]);
			int pregledMesec = Integer.parseInt(pregledDatum[1]);
			int pregledGodina = Integer.parseInt(pregledDatum[2]);
			if(o.getLekariKlinike().get(0).getKlinika().getId() == klinika.getId() && o.getZavrsen() == true &&  pregledGodina >= godinaOd && pregledGodina <= godinaDo) {
				if(godinaOd != godinaDo && pregledGodina == godinaDo && pregledMesec <= mesecDo) {
					if(pregledMesec == mesecDo && pregledDan <= danDo) {
						flag = true;
					} 
					if(pregledMesec < mesecDo) {
						flag = true;
					}
				}
				else if(godinaOd != godinaDo && pregledGodina == godinaOd && pregledMesec >= mesecOd && pregledDan >= danOd) {
					if(pregledMesec == mesecOd && pregledDan >= danDo) {
						flag = true;
					} 
					if(pregledMesec > mesecOd) {
						flag = true;
					}
					
				}
				else if(godinaOd == godinaDo && pregledMesec >= mesecOd && pregledMesec <= mesecDo) {
					 if(mesecDo != mesecOd && pregledMesec == mesecDo && pregledDan <= danDo) {
						 flag = true;
					 }
					 else if(mesecDo != mesecOd && pregledMesec == mesecOd && pregledDan >= danOd) {
						 flag = true;
						 
					 }
					 else if(mesecDo == mesecOd && pregledDan >= danOd && pregledDan <= danDo) {
						 flag = true;
						 
					 }
				}
				
			}
			
			if(flag == true) {
				flag = false;
				prihod += o.getCena();
			}
		}
		
		
		return new ResponseEntity<>(prihod, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getPrihodUkupni/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Double> getPrihodUkupni(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Klinika klinika = klinikaService.findOne(adminKlinikeService.findOne(idLong).getKlinika().getId());
		List<Pregled> pregledi = pregledService.findAll(); 
		List<Operacija> operacije = operacijaService.findAll();
		Double prihod = 0.0;
		for(Pregled p : pregledi) {
			if(p.getLekar().getKlinika().getId() == klinika.getId() && p.getZavrsen() == true) {
				prihod += p.getCena();
			}
		}
		
		for(Operacija o : operacije) {
			if(o.getLekariKlinike().get(0).getKlinika().getId() == klinika.getId() && o.getZavrsen() == true) {
				prihod += o.getCena();
			}
		}
		
		
		return new ResponseEntity<>(prihod, HttpStatus.OK);
	} 
	
	@GetMapping(value = "/postojecaKlinika/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<KlinikaDTO> getPostojecaKlinika(@PathVariable String id) {
		
		Long idLong = Long.parseLong(id);
		Klinika klinika = klinikaService.findOne(adminKlinikeService.findOne(idLong).getKlinika().getId());
		
		KlinikaDTO klinikaDTO = new KlinikaDTO(klinika);
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmeniPodatkeKlinike", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<KlinikaDTO> izmeniPodatkeKlinike(@RequestBody KlinikaDTO klinikaDTO){
		
		try { 
			klinikaService.izmeniKliniku(klinikaDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(klinikaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciLekariKlinike/{id}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<List<LekarDTO>> getPostojeciLekariKlinikePacijent(@PathVariable Long id) {

		List<Lekar> lekari = lekarService.findAll();
		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar lekar : lekari) {
			if (lekar.getKlinika().getId() == id) {
				lekarDTO.add(new LekarDTO(lekar));
			}
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/drzaveKlinika")
	public ResponseEntity<List<String>> getDrzaveKlinika() {
		
		List<String> drzave = klinikaService.getDrzave();
		
		return new ResponseEntity<>(drzave, HttpStatus.OK);
	}
	
	@GetMapping(value = "/gradoviKlinika/{drzava}")
	public ResponseEntity<List<String>> getDrzaveKlinika(@PathVariable String drzava) {
		
		List<String> gradovi = new ArrayList<String>();
		
		if(drzava.equals("none")) {
			gradovi = klinikaService.getGradovi();
		}else {
			gradovi = klinikaService.getGradovi(drzava);
		}
		
		return new ResponseEntity<>(gradovi, HttpStatus.OK);
	}
	
	@PostMapping(value = "/pretraziKlinike/{datumPregleda}/{tipPregleda}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<Collection<KlinikaDTO>> pretraziKlinike(@PathVariable String datumPregleda, @PathVariable Long tipPregleda) throws ParseException {
		
		List<Klinika> klinike = klinikaService.findAll();
		String[] yyyymmdd = datumPregleda.split("-");
		String datum = yyyymmdd[2]+"/"+yyyymmdd[1]+"/"+yyyymmdd[0];
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datumPregledaDate = sdf.parse(datumPregleda);
		
		TipPregleda tipPregledaPretraga = tipPregledaService.findOne(tipPregleda);

		Map<Long, KlinikaDTO> klinikeDTO = new HashMap();
		for (Klinika klinika : klinike) {
			List<Lekar> lekari = lekarService.sviLekariKlinike(klinika.getId());
			for (Lekar lekar : lekari) {
				boolean imaPregledZaZadatiDatum = false;
				int trajanjePregledaMin = 0;
				if (lekar.getTipPregleda().getNaziv().toLowerCase().equals(tipPregledaPretraga.getNaziv().toLowerCase())) {
					
					List<Godisnji> godisnji = godisnjiService.getGodisnjiOdLekara(lekar.getId());
					boolean imaGodisnji = false;
					for (Godisnji godisnjiOdmor : godisnji) {
				        Date datumOd = sdf.parse(godisnjiOdmor.getDatumOd());
				        Date datumDo = sdf.parse(godisnjiOdmor.getDatumDo());
				        if (datumPregledaDate.compareTo(datumOd) >= 0 && datumPregledaDate.compareTo(datumDo) <= 0) {
				        	imaGodisnji = true;
						}
					}
					
					if (imaGodisnji) {
						break;
					}
					
					List<Pregled> pregledi = pregledService.getPregledeOdLekara(lekar.getId());
					for (Pregled pregled : pregledi) {
						if (datum.equals(pregled.getDatum())) {
							imaPregledZaZadatiDatum = true;
							trajanjePregledaMin += pregled.getTrajanjePregleda()*60;
						}
					}
					
					List<Zahtev> zahtevi = zahteviService.getZahteveOdLekara(lekar.getId());
					for (Zahtev zahtev : zahtevi) {
						if (datum.equals(zahtev.getDatum())) {
							imaPregledZaZadatiDatum = true;
							trajanjePregledaMin += zahtev.getTrajanjePregleda()*60;
						}
					}
					
					List<Operacija> operacije = operacijaService.getOperacijeOdLekara(lekar.getId());
					for (Operacija operacija : operacije) {
						if (datum.equals(operacija.getDatum())) {
							imaPregledZaZadatiDatum = true;
							trajanjePregledaMin += operacija.getTrajanjeOperacije()*60;
						}
					}
					
					if (imaPregledZaZadatiDatum) {
						String[] hhmmOd = lekar.getRadnoOd().split(":");
						int hOd = Integer.parseInt(hhmmOd[0]);
						int mOd = Integer.parseInt(hhmmOd[1]);
						
						String[] hhmmDo = lekar.getRadnoDo().split(":");
						int hDo = Integer.parseInt(hhmmDo[0]);
						int mDo = Integer.parseInt(hhmmDo[1]);
						
						int slobodnoVremeLekaraMin = Math.abs((hDo - hOd))*60 - (mDo - mOd);
						
						if (slobodnoVremeLekaraMin - trajanjePregledaMin > 0) {
							if (!klinikeDTO.containsKey(klinika.getId())) {
								klinikeDTO.put(klinika.getId() , new KlinikaDTO(klinika));
							}
						}
						
					} else {
						if (!klinikeDTO.containsKey(klinika.getId())) {
							klinikeDTO.put(klinika.getId() , new KlinikaDTO(klinika));
						}
					}
				}
			}
		}

		return new ResponseEntity<>(klinikeDTO.values(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/slobodniLekari/{datumPregleda}/{tipPregleda}/{idKlinike}")
	public ResponseEntity<List<LekarDTO>> slobodniLekariKlinike(@PathVariable String datumPregleda, @PathVariable Long tipPregleda, @PathVariable Long idKlinike) throws ParseException{
		List<Lekar> lekari = lekarService.sviLekariKlinike(idKlinike);
		List<LekarDTO> lekariDTO = new ArrayList<LekarDTO>();
		
		String datum = "NONE";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datumPregledaDate = sdf.parse("0000-00-00");
		
		if (!datumPregleda.equals("NONE")) {
			String[] yyyymmdd = datumPregleda.split("-");
			datum = yyyymmdd[2]+"/"+yyyymmdd[1]+"/"+yyyymmdd[0];
			datumPregledaDate = sdf.parse(datumPregleda);
		}
		
		
		for (Lekar lekar : lekari) {
			boolean imaPregledZaZadatiDatum = false;
			int trajanjePregledaMin = 0;
			if (lekar.getTipPregleda().getId() == tipPregleda || tipPregleda == -1) {
				
				List<Godisnji> godisnji = godisnjiService.getGodisnjiOdLekara(lekar.getId());
				boolean imaGodisnji = false;
				for (Godisnji godisnjiOdmor : godisnji) {
			        Date datumOd = sdf.parse(godisnjiOdmor.getDatumOd());
			        Date datumDo = sdf.parse(godisnjiOdmor.getDatumDo());
			        if (datumPregledaDate.compareTo(datumOd) >= 0 && datumPregledaDate.compareTo(datumDo) <= 0) {
			        	imaGodisnji = true;
					}
				}
				
				if (imaGodisnji) {
					break;
				}
				
				List<Pregled> pregledi = pregledService.getPregledeOdLekara(lekar.getId());
				for (Pregled pregled : pregledi) {
					if (datum.equals(pregled.getDatum())) {
						imaPregledZaZadatiDatum = true;
						trajanjePregledaMin += pregled.getTrajanjePregleda()*60;
					}
				}
				
				List<Zahtev> zahtevi = zahteviService.getZahteveOdLekara(lekar.getId());
				for (Zahtev zahtev : zahtevi) {
					if (datum.equals(zahtev.getDatum())) {
						imaPregledZaZadatiDatum = true;
						trajanjePregledaMin += zahtev.getTrajanjePregleda()*60;
					}
				}
				
				List<Operacija> operacije = operacijaService.getOperacijeOdLekara(lekar.getId());
				for (Operacija operacija : operacije) {
					if (datum.equals(operacija.getDatum())) {
						imaPregledZaZadatiDatum = true;
						trajanjePregledaMin += operacija.getTrajanjeOperacije()*60;
					}
				}
				
				if (imaPregledZaZadatiDatum) {
					String[] hhmmOd = lekar.getRadnoOd().split(":");
					int hOd = Integer.parseInt(hhmmOd[0]);
					int mOd = Integer.parseInt(hhmmOd[1]);
					
					String[] hhmmDo = lekar.getRadnoDo().split(":");
					int hDo = Integer.parseInt(hhmmDo[0]);
					int mDo = Integer.parseInt(hhmmDo[1]);
					
					int slobodnoVremeLekaraMin = Math.abs((hDo - hOd))*60 - (mDo - mOd);
					
					if (slobodnoVremeLekaraMin - trajanjePregledaMin > 0) {
						lekariDTO.add(new LekarDTO(lekar));
					}
					
				}else {
					lekariDTO.add(new LekarDTO(lekar));
				}
			}
		}
		
		return new ResponseEntity<>(lekariDTO, HttpStatus.OK);
	}
	
}
