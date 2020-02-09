package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DogadjajDTO;
import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pregled;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.Zahtev;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PregledService;
import com.example.demo.service.SalaKlinikeService;
import com.example.demo.service.ZahteviService;

@RestController
@RequestMapping(value = "salaKLinike")
public class SalaKlinikeController {

	@Autowired
	private SalaKlinikeService salaKlinikeService;
	
	@Autowired
	private ZahteviService zahteviService2;

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private PregledService pregledService;
	
	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private ZahteviService zahteviService;

	@GetMapping(value = "/slobodniTermin/{idPregleda}")
	public ResponseEntity<String> getSlobodniTermini(@PathVariable Long idPregleda) {
		Zahtev pregled = zahteviService.findOne(idPregleda);

		int vreme = Integer.parseInt(pregled.getVreme().split(":")[0]) * 60 + Integer.parseInt(pregled.getVreme().split(":")[1]);
		double trajanje = pregled.getTrajanjePregleda() * 60;

		String[] yyyymmdd = pregled.getDatum().split("-");
		String datum = pregled.getDatum();

		String slobodniTermin = "";

		
		//List<Pregled> pregledi = pregledService.getPregledeOdLekara(pregled.getLekar().getId());
		List<Pregled> pregledi = pregledService.findAll();
		//List<Zahtev> zahtevi = zahteviService.getZahteveOdLekara(pregled.getLekar().getId());
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<SalaKlinike> sale = salaKlinikeService.findAll();
		List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
		
		for (SalaKlinike s : sale) {
			if(pregled.getLekar().getKlinika().getId() == s.getKlinika().getId()) {
				salaDTO.add(new SalaKlinikeDTO(s));
			}
		}
		
		for (int i = vreme; i < 1440 ; i += 30) {
			for(SalaKlinikeDTO s : salaDTO) {
			System.out.println("ovo je vreme" + i);
			
			boolean exist = false;
			for (Pregled p : pregledi) {
				if (datum.equals(p.getDatum()) && pregled.getLekar().getKlinika().getId() == p.getLekar().getKlinika().getId() && p.getSala().getId() == s.getId()) {
					System.out.println(datum);
					System.out.println(p.getDatum());
					int pregledOd = Integer.parseInt(p.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(p.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + p.getTrajanjePregleda() * 60);
					if(!((i < pregledOd && i+trajanje <= pregledOd) || (i >= pregledDo && i+trajanje > pregledDo)))
					 {
						System.out.println(exist);
						exist = true;
						break;
					}
				}
			}
			
			if(exist == true) {
				break;
			}
			
			for (Zahtev p : zahtevi) {
				if(p.getSala() != null) {
				if (datum.equals(p.getDatum()) && pregled.getId() != p.getId() && pregled.getLekar().getKlinika().getId() == p.getLekar().getKlinika().getId() && p.getSala().getId() == s.getId()) {
					System.out.println(datum);
					System.out.println(p.getDatum());
					int pregledOd = Integer.parseInt(p.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(p.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + p.getTrajanjePregleda() * 60);
					if(!((i < pregledOd && i+trajanje <= pregledOd) || (i >= pregledDo && i+trajanje > pregledDo)))
					 {
						System.out.println(exist);
						exist = true;
						break;
					}
				}
				}
			}
			
			if(exist == true) {
				break;
			}
			
			for (Operacija o : operacije) {
				if (datum.equals(o.getDatum()) && pregled.getLekar().getKlinika().getId() == o.getLekariKlinike().get(0).getKlinika().getId() && o.getSala().getId() == s.getId()) {
					System.out.println(datum);
					System.out.println(o.getDatum());
					int pregledOd = Integer.parseInt(o.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(o.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + o.getTrajanjeOperacije() * 60);
					if(!((i < pregledOd && i+trajanje <= pregledOd) || (i >= pregledDo && i+trajanje > pregledDo)))
					 {
						System.out.println(exist);
						exist = true;
						break;
					}
				}
			}
			
			
			
			
			if (!exist) {
				int terminOd = i;
			    
				System.out.println("usao u exist" + terminOd);

				String terminOdStr = (terminOd / 60) + ":"
						+ (((terminOd - (terminOd / 60) * 60) == 0) ? "00" : (terminOd - (terminOd / 60) * 60));
				

				slobodniTermin = terminOdStr;
				return new ResponseEntity<>(slobodniTermin, HttpStatus.OK);
			}
		}
		}

		return new ResponseEntity<>(slobodniTermin, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@GetMapping(value = "/kalendar/{idSale}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<DogadjajDTO> obavezeLekara(@PathVariable String idSale) {
		
		Long idLong = Long.parseLong(idSale);
		SalaKlinike sala = salaKlinikeService.findOne(idLong);
		
		List<Pregled> pregledi = pregledService.findAll();
		List<DogadjajDTO> datumi = new ArrayList<>();
		List<Operacija> operacije = operacijaService.findAll();
		
		for(Pregled preg : pregledi)
		{
			if((preg.getSala().getId()).equals(idLong)) {
				String[] datum = preg.getDatum().split("/");
				String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
				
				String[] vr = preg.getVreme().split(":");
				double sat = Double.parseDouble(vr[0]);
				double min = Double.parseDouble(vr[1]);
				
				double trajanjeMin = preg.getTrajanjePregleda() * 60;
				double trajanjeMinOstatak = trajanjeMin % 60;
				double trajanjeSat = trajanjeMin / 60;
				int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak)/60);
				double krajPregledaMin = min + trajanjeMinOstatak;
				
				System.out.println(sat+" "+krajPregledaSat+" "+min+" "+trajanjeMinOstatak);
				
				String minStr = "";
				if (krajPregledaMin == 0) {
					minStr = "00";
				}else {
					minStr = "30";
				}
				
				
				
				String start = datumStr+ ' ' +preg.getVreme();
				String end = datumStr +' '+ krajPregledaSat+":"+minStr;
				datumi.add(new DogadjajDTO(start, end, "", preg.getId(), preg.getIdPacijenta()));
			}
		};
		
		
		for(Operacija preg : operacije)
		{
			if((preg.getSala().getId()).equals(idLong)) {
				String[] datum = preg.getDatum().split("/");
				String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
				
				String[] vr = preg.getVreme().split(":");
				double sat = Double.parseDouble(vr[0]);
				double min = Double.parseDouble(vr[1]);
				
				double trajanjeMin = preg.getTrajanjeOperacije() * 60;
				double trajanjeMinOstatak = trajanjeMin % 60;
				double trajanjeSat = trajanjeMin / 60;
				int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak)/60);
				double krajPregledaMin = min + trajanjeMinOstatak;
				
				System.out.println(sat+" "+krajPregledaSat+" "+min+" "+trajanjeMinOstatak);
				
				String minStr = "";
				if (krajPregledaMin == 0) {
					minStr = "00";
				}else {
					minStr = "30";
				}
				
				
				
				String start = datumStr+ ' ' +preg.getVreme();
				String end = datumStr +' '+ krajPregledaSat+":"+minStr;
				datumi.add(new DogadjajDTO(start, end,"",(long) -1, (long) -1));
			}
		};
		
		return datumi;
	}
	
	
	
	@PostMapping(value = "/dodajSaluKlinike/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SalaKlinikeDTO> dodajSaluKlinike(@RequestBody SalaKlinikeDTO salaDTO,
			@PathVariable String id) {

		SalaKlinikeDTO salaDto = new SalaKlinikeDTO();
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<SalaKlinike> sale = salaKlinikeService.findAll();
		for (SalaKlinike s : sale) {
			if (s.getNaziv().equals(salaDTO.getNaziv()) && (s.getKlinika().getId() == adm.getKlinika().getId())) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (s.getBroj().equals(salaDTO.getBroj()) && (s.getKlinika().getId() == adm.getKlinika().getId())) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		try {
			salaDto = salaKlinikeService.dodajSaluKlinike(salaDTO, idLong);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(salaDto, HttpStatus.OK);
	}

	@PostMapping(value = "/izmeniPodatkeSaleKlinike", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SalaKlinikeDTO> izmeniPodatkeSaleKlinike(@RequestBody SalaKlinikeDTO salaKlinikeDTO) {

	
		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		boolean flag = false;
		
		
		try {
			
			for(Pregled p : pregledi) {
				if(p.getSala().getId() == salaKlinikeDTO.getId() && p.getZavrsen() == false) {
					flag = true;
				}
			}
			
			for(Zahtev z : zahtevi) {
				if(z.getSala() != null) {
				if(z.getSala().getId() == salaKlinikeDTO.getId()) {
					flag = true;
				}
				}
			}
			
			
			for(Operacija o : operacije) {
				if(o.getSala().getId() == salaKlinikeDTO.getId() && o.getZavrsen() == false) {
					flag = true;
				}
			}
			
			if(flag == false) {
				salaKlinikeService.izmeniSaluKlinike(salaKlinikeDTO);
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(salaKlinikeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/izbrisiSaluKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteSalu(@PathVariable Long id) {

		SalaKlinike salaKlinike = salaKlinikeService.findOne(id);
		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		boolean flag = false;
		
		List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
		
		if (salaKlinike != null) {
			
			for(Pregled p : pregledi) {
				if(p.getSala().getId() == salaKlinike.getId() && p.getZavrsen() == false) {
					flag = true;
				}
			}
			
			for(Zahtev z : zahtevi) {
				if(z.getSala() != null) {
				if(z.getSala().getId() == salaKlinike.getId()) {
					flag = true;
				}
				}
			}
			
			
			for(Operacija o : operacije) {
				if(o.getSala().getId() == salaKlinike.getId() && o.getZavrsen() == false) {
					flag = true;
				}
			}
			if(flag == false) {
				salaKlinikeService.remove(id);
				List<SalaKlinike> salaKli = salaKlinikeService.findAll();

				for (SalaKlinike sala : salaKli) {
					if (sala.getKlinika().getId() == salaKlinike.getKlinika().getId()) {
						salaDTO.add(new SalaKlinikeDTO(sala));
					}
				}
				return new ResponseEntity<>(salaDTO, HttpStatus.OK);
			}
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if(flag == true) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
	@GetMapping(value = "/SaleKlinike/{id}")
	public ResponseEntity<List<SalaKlinikeDTO>> getSveSale(@PathVariable String id) {

		List<SalaKlinike> sale = salaKlinikeService.findAll();

		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);

		List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
		for (SalaKlinike s : sale) {
			if (s.getKlinika().getId() == adm.getKlinika().getId()) {
				salaDTO.add(new SalaKlinikeDTO(s));
			}
		}

		return new ResponseEntity<>(salaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/SaleKlinike/{id}/{idPregleda}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<SalaKlinikeDTO>> getSveSalee(@PathVariable String id, @PathVariable String idPregleda) {


		
		
		 boolean flag = false;
		
		List<SalaKlinike> sale = salaKlinikeService.findAll();
		List<Pregled> pregledi = pregledService.findAll(); 
		Long idLong = Long.parseLong(id);
		
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		
		
		
		List<Zahtev> zahtevi = zahteviService.findAll();
		
		List<Operacija> operacije = operacijaService.findAll();
			
		Long idLongPregled = Long.parseLong(idPregleda);
		Zahtev zahtev =  zahteviService.findOne(idLongPregled);
		
		
		 if(zahtev.getDatum() != null) {
			//String[] datum = dat.split("-");
			String datumStr = zahtev.getDatum();
			//String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
			String[] vreme = zahtev.getVreme().split(":");
			double sat = Double.parseDouble(vreme[0]);
			double min = Double.parseDouble(vreme[1]);
			
			double trajanjeMin = zahtev.getTrajanjePregleda() * 60;
			double trajanjeMinOstatak = trajanjeMin % 60;
			double trajanjeSat = trajanjeMin / 60;
			int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak)/60);
			double krajPregledaMin = min + trajanjeMinOstatak;
			
			if (krajPregledaMin == 60) {
				krajPregledaMin = 0;
				krajPregledaSat++;
			}
			
			double minutiPocetak = sat*60 + min;
			double minutiKraj = krajPregledaSat*60 + krajPregledaMin;
			System.out.println(minutiPocetak);
			System.out.println(minutiKraj);
			List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
			for (SalaKlinike s : sale) {
				if (s.getKlinika().getId() == adm.getKlinika().getId()) {
					for(Pregled p : pregledi) {
						if(p.getDatum().equals(datumStr) && p.getSala().getId() == s.getId()) {
							String[] vremeP = p.getVreme().split(":");
							double satP = Double.parseDouble(vremeP[0]);
							double minP = Double.parseDouble(vremeP[1]);
							double trajanjeMinP = p.getTrajanjePregleda() * 60;
							double trajanjeMinOstatakP = trajanjeMinP % 60;
							double trajanjeSatP = trajanjeMinP / 60;
							int krajPregledaSatP = (int) (satP + (trajanjeMinP - trajanjeMinOstatakP)/60);
							double krajPregledaMinP = minP + trajanjeMinOstatakP;
							if (krajPregledaMinP == 60) {
								krajPregledaMinP = 0;
								krajPregledaSatP++;
							}
								double minutiPocetakP = satP*60 + minP;
								double minutiKrajP = krajPregledaSatP*60 + krajPregledaMinP;
								System.out.println(minutiPocetakP);
								System.out.println(minutiKrajP);
								if(!((minutiPocetak < minutiPocetakP && minutiKraj <= minutiPocetakP) || (minutiPocetak >= minutiKrajP && minutiKraj > minutiKrajP)))
								{
									flag = true;
								}
							
							
							
						} 
						
						
					}
					
					
					for(Operacija o : operacije) {
						if(o.getDatum().equals(datumStr) && o.getSala().getId() == s.getId()) {
							String[] vremeP = o.getVreme().split(":");
							double satP = Double.parseDouble(vremeP[0]);
							double minP = Double.parseDouble(vremeP[1]);
							double trajanjeMinP = o.getTrajanjeOperacije() * 60;
							double trajanjeMinOstatakP = trajanjeMinP % 60;
							double trajanjeSatP = trajanjeMinP / 60;
							int krajPregledaSatP = (int) (satP + (trajanjeMinP - trajanjeMinOstatakP)/60);
							double krajPregledaMinP = minP + trajanjeMinOstatakP;
							if (krajPregledaMinP == 60) {
								krajPregledaMinP = 0;
								krajPregledaSatP++;
							}
								double minutiPocetakP = satP*60 + minP;
								double minutiKrajP = krajPregledaSatP*60 + krajPregledaMinP;
								System.out.println(minutiPocetakP);
								System.out.println(minutiKrajP);
								if(!((minutiPocetak < minutiPocetakP && minutiKraj <= minutiPocetakP) || (minutiPocetak >= minutiKrajP && minutiKraj > minutiKrajP)))
								{
									flag = true;
								}
							
							
							
						} 
						
						
					}
					
					
					for(Zahtev z : zahtevi) {
						if(z.getSala() != null) {
						if(z.getDatum().equals(datumStr) && z.getSala().getId() == s.getId()  && z.getId() != zahtev.getId()) {
							String[] vremeZ = z.getVreme().split(":");
							double satZ = Double.parseDouble(vremeZ[0]);
							double minZ = Double.parseDouble(vremeZ[1]);
							double trajanjeMinZ = z.getTrajanjePregleda() * 60;
							double trajanjeMinOstatakZ = trajanjeMinZ % 60;
							double trajanjeSatZ = trajanjeMinZ / 60;
							int krajPregledaSatZ = (int) (satZ + (trajanjeMinZ - trajanjeMinOstatakZ)/60);
							double krajPregledaMinZ = minZ + trajanjeMinOstatakZ;
							if (krajPregledaMinZ == 60) {
								krajPregledaMinZ = 0;
								krajPregledaSatZ++;
							}
								double minutiPocetakZ = satZ*60 + minZ;
								double minutiKrajZ = krajPregledaSatZ*60 + krajPregledaMinZ;
								System.out.println(minutiPocetakZ);
								System.out.println(minutiKrajZ);
								if(!((minutiPocetak < minutiPocetakZ && minutiKraj <= minutiPocetakZ) || (minutiPocetak >= minutiKrajZ && minutiKraj > minutiKrajZ)))
								{
									flag = true;
								}
							
							
							
						} 
						
					}
					}
					
					
					if (flag == true) {
						flag = false;
					} else {
						salaDTO.add(new SalaKlinikeDTO(s));
					}
					
				}
			}

			return new ResponseEntity<>(salaDTO, HttpStatus.OK);

		}
		
		else {
			List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
			return new ResponseEntity<>(salaDTO, HttpStatus.OK);
		}
		
	}
	
	@GetMapping(value = "/SaleKlinikeOperacije/{id}/{idOperacije}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<SalaKlinikeDTO>> getSveSaleOperacije(@PathVariable String id, @PathVariable String idOperacije) {


		 boolean flag = false;
		 boolean flag2 = false;
		
		List<SalaKlinike> sale = salaKlinikeService.findAll();
		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		Long idLong = Long.parseLong(id);
		
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		
		Long idLongOperacija = Long.parseLong(idOperacije);
		Zahtev zahtev =  zahteviService.findOne(idLongOperacija);
		
		
		 if(zahtev.getDatum() != null) {
			//String[] datum = dat.split("-");
			String datumStr = zahtev.getDatum();
			//String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
			String[] vreme = zahtev.getVreme().split(":");
			double sat = Double.parseDouble(vreme[0]);
			double min = Double.parseDouble(vreme[1]);
			
			double trajanjeMin = zahtev.getTrajanjePregleda() * 60;
			double trajanjeMinOstatak = trajanjeMin % 60;
			double trajanjeSat = trajanjeMin / 60;
			int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak)/60);
			double krajPregledaMin = min + trajanjeMinOstatak;
			
			if (krajPregledaMin == 60) {
				krajPregledaMin = 0;
				krajPregledaSat++;
			}
			
			double minutiPocetak = sat*60 + min;
			double minutiKraj = krajPregledaSat*60 + krajPregledaMin;
			System.out.println(minutiPocetak);
			System.out.println(minutiKraj);
			List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
			for (SalaKlinike s : sale) {
				if (s.getKlinika().getId() == adm.getKlinika().getId()) {
					for(Pregled p : pregledi) {
						if(p.getDatum().equals(datumStr) && p.getSala().getId() == s.getId()) {
							String[] vremeP = p.getVreme().split(":");
							double satP = Double.parseDouble(vremeP[0]);
							double minP = Double.parseDouble(vremeP[1]);
							double trajanjeMinP = p.getTrajanjePregleda() * 60;
							double trajanjeMinOstatakP = trajanjeMinP % 60;
							double trajanjeSatP = trajanjeMinP / 60;
							int krajPregledaSatP = (int) (satP + (trajanjeMinP - trajanjeMinOstatakP)/60);
							double krajPregledaMinP = minP + trajanjeMinOstatakP;
							if (krajPregledaMinP == 60) {
								krajPregledaMinP = 0;
								krajPregledaSatP++;
							}
								double minutiPocetakP = satP*60 + minP;
								double minutiKrajP = krajPregledaSatP*60 + krajPregledaMinP;
								System.out.println(minutiPocetakP);
								System.out.println(minutiKrajP);
								if(!((minutiPocetak < minutiPocetakP && minutiKraj < minutiPocetakP) || (minutiPocetak > minutiKrajP && minutiKraj > minutiKrajP)))
								{
									flag = true;
								}
							
							
							
						} 
						
						
					}
					
					if (flag == true) {
						return new ResponseEntity<>(salaDTO, HttpStatus.OK);
					} else {
						for(Operacija o : operacije) {
							if(o.getDatum().equals(datumStr) && o.getSala().getId() == s.getId()) {
								String[] vremeP = o.getVreme().split(":");
								double satP = Double.parseDouble(vremeP[0]);
								double minP = Double.parseDouble(vremeP[1]);
								double trajanjeMinP = o.getTrajanjeOperacije() * 60;
								double trajanjeMinOstatakP = trajanjeMinP % 60;
								double trajanjeSatP = trajanjeMinP / 60;
								int krajPregledaSatP = (int) (satP + (trajanjeMinP - trajanjeMinOstatakP)/60);
								double krajPregledaMinP = minP + trajanjeMinOstatakP;
								if (krajPregledaMinP == 60) {
									krajPregledaMinP = 0;
									krajPregledaSatP++;
								}
									double minutiPocetakP = satP*60 + minP;
									double minutiKrajP = krajPregledaSatP*60 + krajPregledaMinP;
									System.out.println(minutiPocetakP);
									System.out.println(minutiKrajP);
									if(!((minutiPocetak < minutiPocetakP && minutiKraj < minutiPocetakP) || (minutiPocetak > minutiKrajP && minutiKraj > minutiKrajP)))
									{
										flag = true;
									}
								
								
								
							} 
							
							
						}
						
						if(flag==true) {
							flag=false;
						}else {
							salaDTO.add(new SalaKlinikeDTO(s));
						}
						
					}
					
				}
			}

			return new ResponseEntity<>(salaDTO, HttpStatus.OK);

		}
		
		else {
			List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
			return new ResponseEntity<>(salaDTO, HttpStatus.OK);
		}
		
	}
	
	@GetMapping(value = "/SaleKlinike/{id}/{dat}/{vr}/{idPregleda}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<SalaKlinikeDTO>> getSveSale(@PathVariable String id,@PathVariable String dat,@PathVariable String vr, @PathVariable String idPregleda) {


		
		
		 boolean flag = false;
		
		List<SalaKlinike> sale = salaKlinikeService.findAll();
		List<Pregled> pregledi = pregledService.findAll(); 
		Long idLong = Long.parseLong(id);
		
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		
		
		if(dat.equals("-") && vr.equals("-") && idPregleda.equals("-") ) {

			List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
			for (SalaKlinike s : sale) {
				if (s.getKlinika().getId() == adm.getKlinika().getId()) {
					salaDTO.add(new SalaKlinikeDTO(s));
				}
			}

			
			return new ResponseEntity<>(salaDTO, HttpStatus.OK);
			}
		
		
	
		Long idLongPregled = Long.parseLong(idPregleda);
		Zahtev zahtev =  zahteviService.findOne(idLongPregled);
		List<Zahtev> zahtevi = zahteviService.findAll();		
		 if(!dat.equals("-") && !vr.equals("-")) {
			String[] datum = dat.split("-");
			String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
			String[] vreme = vr.split(":");
			double sat = Double.parseDouble(vreme[0]);
			double min = Double.parseDouble(vreme[1]);
			
			double trajanjeMin = zahtev.getTrajanjePregleda() * 60;
			double trajanjeMinOstatak = trajanjeMin % 60;
			double trajanjeSat = trajanjeMin / 60;
			int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak)/60);
			double krajPregledaMin = min + trajanjeMinOstatak;
			
			if (krajPregledaMin == 60) {
				krajPregledaMin = 0;
				krajPregledaSat++;
			}
			
			double minutiPocetak = sat*60 + min;
			double minutiKraj = krajPregledaSat*60 + krajPregledaMin;
			System.out.println(minutiPocetak);
			System.out.println(minutiKraj);
			List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
			for (SalaKlinike s : sale) {
				if (s.getKlinika().getId() == adm.getKlinika().getId()) {
					for(Pregled p : pregledi) {
						if(p.getDatum().equals(datumStr) && p.getSala().getId() == s.getId()) {
							String[] vremeP = p.getVreme().split(":");
							double satP = Double.parseDouble(vremeP[0]);
							double minP = Double.parseDouble(vremeP[1]);
							double trajanjeMinP = p.getTrajanjePregleda() * 60;
							double trajanjeMinOstatakP = trajanjeMinP % 60;
							double trajanjeSatP = trajanjeMinP / 60;
							int krajPregledaSatP = (int) (satP + (trajanjeMinP - trajanjeMinOstatakP)/60);
							double krajPregledaMinP = minP + trajanjeMinOstatakP;
							if (krajPregledaMinP == 60) {
								krajPregledaMinP = 0;
								krajPregledaSatP++;
							}
								double minutiPocetakP = satP*60 + minP;
								double minutiKrajP = krajPregledaSatP*60 + krajPregledaMinP;
								System.out.println(minutiPocetakP);
								System.out.println(minutiKrajP);
								if(!((minutiPocetak < minutiPocetakP && minutiKraj <= minutiPocetakP) || (minutiPocetak >= minutiKrajP && minutiKraj > minutiKrajP)))
								{
									flag = true;
								}
							
							
							
						} 
						
						
					}
					
					
					for(Zahtev z : zahtevi) {
						if(z.getDatum().equals(datumStr) && z.getSala().getId() == s.getId() && z.getId() != zahtev.getId()) {
							String[] vremeZ = z.getVreme().split(":");
							double satZ = Double.parseDouble(vremeZ[0]);
							double minZ = Double.parseDouble(vremeZ[1]);
							double trajanjeMinZ = z.getTrajanjePregleda() * 60;
							double trajanjeMinOstatakZ = trajanjeMinZ % 60;
							double trajanjeSatZ = trajanjeMinZ / 60;
							int krajPregledaSatZ = (int) (satZ + (trajanjeMinZ - trajanjeMinOstatakZ)/60);
							double krajPregledaMinZ = minZ + trajanjeMinOstatakZ;
							if (krajPregledaMinZ == 60) {
								krajPregledaMinZ = 0;
								krajPregledaSatZ++;
							}
								double minutiPocetakZ = satZ*60 + minZ;
								double minutiKrajZ = krajPregledaSatZ*60 + krajPregledaMinZ;
								System.out.println(minutiPocetakZ);
								System.out.println(minutiKrajZ);
								if(!((minutiPocetak < minutiPocetakZ && minutiKraj <= minutiPocetakZ) || (minutiPocetak >= minutiKrajZ && minutiKraj > minutiKrajZ)))
								{
									flag = true;
								}
							
							
							
						} 
						
						
					}
					
					
					if (flag == true) {
						flag = false;
					} else {
						salaDTO.add(new SalaKlinikeDTO(s));
					}
					
				}
			}

			return new ResponseEntity<>(salaDTO, HttpStatus.OK);

		}
		
		else {
			List<SalaKlinikeDTO> salaDTO = new ArrayList<>();
			return new ResponseEntity<>(salaDTO, HttpStatus.OK);
		}
		
	}

}