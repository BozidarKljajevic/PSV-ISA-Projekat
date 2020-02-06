package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;
import javax.websocket.server.PathParam;

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
import com.example.demo.dto.DogadjajDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.OperacijaDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SifraDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Godisnji;
import com.example.demo.model.Klinika;
import com.example.demo.model.Lekar;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pacijent;
import com.example.demo.model.Pregled;
import com.example.demo.model.User;
import com.example.demo.model.Zahtev;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.GodisnjiService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.LekarService;
import com.example.demo.service.OperacijaService;
import com.example.demo.service.PregledService;
import com.example.demo.service.UserService;
import com.example.demo.service.ZahteviService;

@RestController
@RequestMapping(value = "lekar")
public class LekarController {

	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PregledService pregledService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private GodisnjiService godisnjiService;
	
	@Autowired
	private ZahteviService zahteviService;
	
	@Autowired
	private OperacijaService operacijaService;

	@PostMapping(value = "/izmeniGenerickuSifru/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> izmeniGenerickuSifru(@RequestBody SifraDTO sifra, @PathVariable Long id) {

		User user = userService.findOne(id);

		System.out.println(sifra.getSifra());
		System.out.println(id);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		userService.izmeniSifru(user, sifra.getSifra());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/obavezeLekara/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public List<DogadjajDTO> obavezeLekara(@PathVariable String id) {

		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);

		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<DogadjajDTO> datumi = new ArrayList<>();
		List<Lekar> lekari = new ArrayList<>();

		for (Pregled preg : pregledi) {
			if ((preg.getLekar().getId()).equals(idLong) && preg.getZavrsen()==false) {
				String[] datum = preg.getDatum().split("/");
				String datumStr = datum[2] + "/" + datum[1] + "/" + datum[0];
				
				String[] vr = preg.getVreme().split(":");
				double sat = Double.parseDouble(vr[0]);
				double min = Double.parseDouble(vr[1]);

				double trajanjeMin = preg.getTrajanjePregleda() * 60;
				double trajanjeMinOstatak = trajanjeMin % 60;
				double trajanjeSat = trajanjeMin / 60;
				int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak) / 60);
				double krajPregledaMin = min + trajanjeMinOstatak;

				System.out.println(sat + " " + krajPregledaSat + " " + min + " " + trajanjeMinOstatak);

				String minStr = "";
				if (krajPregledaMin == 60) {
					minStr = "00";
					krajPregledaSat++;
				}else if (krajPregledaMin == 0)
				{
					minStr = "00";
					krajPregledaSat++;
				} else {
					minStr = "30";
				}

				String start = datumStr + ' ' + preg.getVreme();
				String end = datumStr + ' ' + krajPregledaSat + ":" + minStr;
				datumi.add(new DogadjajDTO(start, end, "pregled", preg.getId()));
			}
		};
		for (Operacija o : operacije) {
			lekari=o.getLekariKlinike();
			System.out.println(lekari.size());
			for(int i=0; i<lekari.size(); i++) {
				if ((lekari.get(i).getId()).equals(idLong)) {
					String[] datum = o.getDatum().split("/");
					String datumStr = datum[2] + "/" + datum[1] + "/" + datum[0];

					String[] vr = o.getVreme().split(":");
					double sat = Double.parseDouble(vr[0]);
					double min = Double.parseDouble(vr[1]);

					double trajanjeMin = o.getTrajanjeOperacije() * 60;
					double trajanjeMinOstatak = trajanjeMin % 60;
					double trajanjeSat = trajanjeMin / 60;
					int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak) / 60);
					double krajPregledaMin = min + trajanjeMinOstatak;

					System.out.println(sat + " " + krajPregledaSat + " " + min + " " + trajanjeMinOstatak);

					String minStr = "";
					if (krajPregledaMin == 60) {
						minStr = "00";
						krajPregledaSat++;
					}else if (krajPregledaMin == 0)
					{
						minStr = "00";
						krajPregledaSat++;
					} else {
						minStr = "30";
					}

					String start = datumStr + ' ' + o.getVreme();
					String end = datumStr + ' ' + krajPregledaSat + ":" + minStr;
					datumi.add(new DogadjajDTO(start, end, "operacija", o.getId()));
				}
				
			}
			
		};
		
		return datumi;
	}

	
	
	@GetMapping(value = "/obavezeKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<DogadjajDTO> obavezeKlinike(@PathVariable String id) {

		Long idLong = Long.parseLong(id);
		AdminKlinike adminKlinike = adminKlinikeService.findOne(idLong);

		List<Pregled> pregledi = pregledService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		List<DogadjajDTO> datumi = new ArrayList<>();
		List<Lekar> lekari = new ArrayList<>();

		for (Pregled preg : pregledi) {
			if ((preg.getLekar().getKlinika().getId()).equals(adminKlinike.getKlinika().getId()) && preg.getZavrsen() == true) {
				String[] datum = preg.getDatum().split("/");
				String datumStr = datum[2] + "/" + datum[1] + "/" + datum[0];

				String[] vr = preg.getVreme().split(":");
				double sat = Double.parseDouble(vr[0]);
				double min = Double.parseDouble(vr[1]);

				double trajanjeMin = preg.getTrajanjePregleda() * 60;
				double trajanjeMinOstatak = trajanjeMin % 60;
				double trajanjeSat = trajanjeMin / 60;
				int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak) / 60);
				double krajPregledaMin = min + trajanjeMinOstatak;

				System.out.println(sat + " " + krajPregledaSat + " " + min + " " + trajanjeMinOstatak);

				String minStr = "";
				if (krajPregledaMin == 60) {
					minStr = "00";
					krajPregledaSat++;
				}else if (krajPregledaMin == 0)
				{
					minStr = "00";
					krajPregledaSat++;
				} else {
					minStr = "30";
				}

				String start = datumStr + ' ' + preg.getVreme();
				String end = datumStr + ' ' + krajPregledaSat + ":" + minStr;
				datumi.add(new DogadjajDTO(start, end,"",(long) -1));
			}
		};
		for (Operacija o : operacije) {
			lekari=o.getLekariKlinike();
			System.out.println(lekari.size());
			for(int i=0; i<lekari.size(); i++) {
				if ((lekari.get(i).getKlinika().getId()).equals(adminKlinike.getKlinika().getId()) && o.getZavrsen() == true) {
					String[] datum = o.getDatum().split("/");
					String datumStr = datum[2] + "/" + datum[1] + "/" + datum[0];

					String[] vr = o.getVreme().split(":");
					double sat = Double.parseDouble(vr[0]);
					double min = Double.parseDouble(vr[1]);

					double trajanjeMin = o.getTrajanjeOperacije() * 60;
					double trajanjeMinOstatak = trajanjeMin % 60;
					double trajanjeSat = trajanjeMin / 60;
					int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak) / 60);
					double krajPregledaMin = min + trajanjeMinOstatak;

					System.out.println(sat + " " + krajPregledaSat + " " + min + " " + trajanjeMinOstatak);

					String minStr = "";
					if (krajPregledaMin == 60) {
						minStr = "00";
						krajPregledaSat++;
					}else if (krajPregledaMin == 0)
					{
						minStr = "00";
						krajPregledaSat++;
					} else {
						minStr = "30";
					}

					String start = datumStr + ' ' + o.getVreme();
					String end = datumStr + ' ' + krajPregledaSat + ":" + minStr;
					datumi.add(new DogadjajDTO(start, end,"",(long) -1));
				}
				
			}
			
		};
		
		return datumi;
	}
	
	
	@PostMapping(value = "/dodajLekara/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LekarDTO> dodajLekara(@RequestBody LekarDTO lekarDTO1, @PathVariable String id)
			throws Exception {
		LekarDTO lekarDTO2 = new LekarDTO();

		User existUser = this.userService.findOne(lekarDTO1.getMail());
		if (existUser != null) {
			throw new Exception("Alredy exist");
		}

		Long idLong = Long.parseLong(id);
		lekarDTO2 = new LekarDTO(lekarService.dodaj(lekarDTO1, idLong));
		return new ResponseEntity<>(lekarDTO2, HttpStatus.OK);
	}

	@PostMapping(value = "/izmeniLekara", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<LekarDTO> izmeniPodatkeLekara(@RequestBody LekarDTO lekarDTO) {

		try {
			lekarService.izmeni(lekarDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/izmeniLekaraAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LekarDTO> izmeniPodatkeLekaraAdmin(@RequestBody LekarDTO lekarDTO) {

		try {
			lekarService.izmeni(lekarDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/postojeciLekar/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<LekarDTO> getLekar(@PathVariable String id) {

		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);

		LekarDTO lekarDTO = new LekarDTO(lekar);

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/zahteviLekar/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> getZahtevi(@PathVariable String id) {

		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);
		List<PregledDTO> pregleddto = new ArrayList<>();
		List<Pregled> pregledi = pregledService.findAll();
		
		for(Pregled p : pregledi) {
			if(p.getLekar().getId() == lekar.getId() && p.getZavrsen() == false) {
				pregleddto.add(new PregledDTO(p));
			}
		}
		

		return new ResponseEntity<>(pregleddto, HttpStatus.OK);
	}

	
	
	@GetMapping(value = "/operacijeLekar/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> getOperacije(@PathVariable String id) {

		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);
		List<OperacijaDTO> operacijeDTO = new ArrayList<>();
		List<Operacija> operacije = operacijaService.findAll();
		
		for(Operacija o : operacije) {
			if(o.getLekariKlinike().contains(lekar)  && o.getZavrsen() == false) {
				operacijeDTO.add(new OperacijaDTO(o));
			}
		}
		

		return new ResponseEntity<>(operacijeDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/postojeciLekarAdmin/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<LekarDTO> getLekarAdmin(@PathVariable String id) {

		Long idLong = Long.parseLong(id);
		Lekar lekar = lekarService.findOne(idLong);

		LekarDTO lekarDTO = new LekarDTO(lekar);

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/izbrisiLekara/{id}")
	public ResponseEntity<?> deleteExam(@PathVariable Long id) {

		Lekar lekar = lekarService.findOne(id);
		List<LekarDTO> lekarDTO = new ArrayList<>();
		boolean flag = false;
		if (lekar != null) {
			List<Pregled> pregledi = pregledService.findAll();
			List<Operacija> operacije = operacijaService.findAll();
			List<Zahtev> zahtevi = zahteviService.findAll();
			for (Pregled pr : pregledi) {
				if (pr.getLekar().getId() == id && pr.getZavrsen() == false) {
					flag = true;
					break;
				}
			}
			
			for (Operacija o : operacije) {
				if (o.getLekariKlinike().contains(lekar)&& o.getZavrsen() == false) {
					flag = true;
					break;
				}
			}

			for (Zahtev z : zahtevi) {
				if (z.getLekar().getId() == id) {
					flag = true;
					break;
				}
			}
			
			if (flag == false) {
				lekarService.remove(id);
			}

			List<Lekar> lekari = lekarService.findAll();

			for (Lekar le : lekari) {
				if (le.getKlinika().getId() == lekar.getKlinika().getId()) {
					lekarDTO.add(new LekarDTO(le));
				}
			}
			return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/postojeciSviLekari")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<LekarDTO>> getPostojeciNeaktivanPacijent() {

		List<Lekar> lekar = lekarService.findAll();

		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar le : lekar) {
			lekarDTO.add(new LekarDTO(le));
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/postojeciLekariKlinike/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<LekarDTO>> getPostojeciLekariKlinike(@PathVariable String id) {

		List<Lekar> lekar = lekarService.findAll();
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<LekarDTO> lekarDTO = new ArrayList<>();
		for (Lekar le : lekar) {
			if (le.getKlinika().getId() == adm.getKlinika().getId()) {
				lekarDTO.add(new LekarDTO(le));
			}
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/moguciLekariZaPregled/{id}/{idPregleda}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<LekarDTO>> getMoguciLekariZaPregled(@PathVariable String id,@PathVariable String idPregleda) throws ParseException {
		
		List<Lekar> lekar = lekarService.findAll();
		Long idd = Long.parseLong(idPregleda);
		Zahtev zahtev = zahteviService.findOne(idd);
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<Pregled> pregledi = pregledService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
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
		
		boolean flag = false;
		List<LekarDTO> lekarDTO = new ArrayList<>();
		
		
		for (Lekar le : lekar) {
			 if(le.getKlinika().getId() == adm.getKlinika().getId() && le.getTipPregleda() == zahtev.getTipPregleda()) {
			lekarDTO.add(new LekarDTO(le));
			
			 }
		}
		
		
		
		for (Lekar le : lekar) {
			String[] radnoOd = le.getRadnoOd().split(":");
			double satOd = Double.parseDouble(radnoOd[0]);
			double minOd = Double.parseDouble(radnoOd[1]);
			double minutiRadnoOd = satOd*60 + minOd;
			
			String[] radnoDo = le.getRadnoDo().split(":");
			double satDo = Double.parseDouble(radnoDo[0]);
			double minDo = Double.parseDouble(radnoDo[1]);
			double minutiRadnoDo = satDo*60 + minDo;
			
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String[] datum = zahtev.getDatum().split("/");
			Date datumPregledaDate = sdf.parse(datum[2] + "-" + datum[1] + "-" + datum[0]);
			
			
			List<Godisnji> godisnji = godisnjiService.getGodisnjiOdLekara(le.getId());
			 
			 
			 for (Godisnji godisnjiOdmor : godisnji) {
			        Date datumOd = sdf.parse(godisnjiOdmor.getDatumOd());
			        Date datumDo = sdf.parse(godisnjiOdmor.getDatumDo());
			        if (datumPregledaDate.compareTo(datumOd) >= 0 && datumPregledaDate.compareTo(datumDo) <= 0) {
			        	flag = true;
					}
				}
			
			
			 if(le.getKlinika().getId() == adm.getKlinika().getId() && le.getTipPregleda() == zahtev.getTipPregleda()) {
				 if(minutiPocetak < minutiRadnoOd || minutiKraj > minutiRadnoDo)
					{
						flag = true;
						System.out.println("pocetka pregleda" + minutiPocetak);
						System.out.println("kraj pregleda" + minutiKraj);
						System.out.println("radno do" + minutiRadnoOd);
						System.out.println("radno od" +minutiRadnoDo);
					}
				 
				 
				 
				 for(Zahtev z : zahtevi) {
						if(zahtev.getId() != z.getId() && z.getLekar().equals(le) && z.getDatum().equals(datumStr)) {
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
				 
				 
				 
				 
				for(Pregled p : pregledi ) {
					if(p.getLekar().equals(le) && p.getDatum().equals(datumStr)) {
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
				
				
				
				for(Operacija o : operacije ) {
					List<Lekar> lekariOperacije= new ArrayList<>();
					lekariOperacije= o.getLekariKlinike();
					for(int i=0; i<lekariOperacije.size(); i++) {
						if(lekariOperacije.get(i).equals(le) && o.getDatum().equals(datumStr)) {
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
				}
				
				if(flag == true) {
					boolean flag2 = false;
					int index = 0;
					for(int i = 0; i <lekarDTO.size(); i++) {
						if(lekarDTO.get(i).getId() == le.getId()) {
							flag2 = true;
							index = i;
						}
					}
					if(flag2 == true) {
						lekarDTO.remove(index);
						flag = false;
						flag2 = false;
					}
					
					for(LekarDTO ld : lekarDTO) {
						System.out.println(ld.getIme());
					}
				}
					
			 }
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/moguciLekariZaOperaciju/{id}/{idOperacije}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<LekarDTO>> getMoguciLekariZaOperaciju(@PathVariable String id,@PathVariable String idOperacije) {
		
		List<Lekar> lekar = lekarService.findAll();
		Long idd = Long.parseLong(idOperacije);
		Zahtev zahtev = zahteviService.findOne(idd);
		Long idLong = Long.parseLong(id);
		AdminKlinike adm = adminKlinikeService.findOne(idLong);
		List<Pregled> pregledi = pregledService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
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
		
		boolean flag = false;
		List<LekarDTO> lekarDTO = new ArrayList<>();
		
		
		for (Lekar le : lekar) {
			 if(le.getKlinika().getId() == adm.getKlinika().getId()  && le.getTipPregleda() == zahtev.getTipPregleda()) {
			lekarDTO.add(new LekarDTO(le));
			
			 }
		}
		
		
		
		for (Lekar le : lekar) {
			String[] radnoOd = le.getRadnoOd().split(":");
			double satOd = Double.parseDouble(radnoOd[0]);
			double minOd = Double.parseDouble(radnoOd[1]);
			double minutiRadnoOd = satOd*60 + minOd;
			
			String[] radnoDo = le.getRadnoDo().split(":");
			double satDo = Double.parseDouble(radnoDo[0]);
			double minDo = Double.parseDouble(radnoDo[1]);
			double minutiRadnoDo = satDo*60 + minDo;
			
			
			
			
			
			
			 if(le.getKlinika().getId() == adm.getKlinika().getId() && le.getTipPregleda() == zahtev.getTipPregleda()) {
				 if(minutiPocetak < minutiRadnoOd || minutiKraj > minutiRadnoDo)
					{
						flag = true;
						System.out.println("pocetka pregleda" + minutiPocetak);
						System.out.println("kraj pregleda" + minutiKraj);
						System.out.println("radno do" + minutiRadnoOd);
						System.out.println("radno od" +minutiRadnoDo);
					}
				 
				 
				 
				 for(Zahtev z : zahtevi) {
						if(zahtev.getId() != z.getId() && z.getLekar().equals(le) && z.getDatum().equals(datumStr)) {
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
				 
				 
				 
				 
				for(Pregled p : pregledi ) {
					if(p.getLekar().equals(le) && p.getDatum().equals(datumStr)) {
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
				
				for(Operacija o : operacije ) {
					List<Lekar> lekariOperacije= new ArrayList<>();
					lekariOperacije= o.getLekariKlinike();
					for(int i=0; i<lekariOperacije.size(); i++) {
						if(lekariOperacije.get(i).equals(le) && o.getDatum().equals(datumStr)) {
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
				}
				
				if(flag == true) {
					boolean flag2 = false;
					int index = 0;
					for(int i = 0; i <lekarDTO.size(); i++) {
						if(lekarDTO.get(i).getId() == le.getId()) {
							flag2 = true;
							index = i;
						}
					}
					if(flag2 == true) {
						lekarDTO.remove(index);
						flag = false;
						flag2 = false;
					}
					
					for(LekarDTO ld : lekarDTO) {
						System.out.println(ld.getIme());
					}
				}
					
			 }
		}

		return new ResponseEntity<>(lekarDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/dostupniTermini/{trajanjePregleda}/{datumPregleda}/{idLekara}")
	public ResponseEntity<?> getSlobodniTerminiLekar(
			@PathVariable String trajanjePregleda,
			@PathVariable String datumPregleda,@PathVariable Long idLekara) throws ParseException {
		Lekar lekar = lekarService.findOne(idLekara);

		boolean flagGodisnji = false;
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date datumPregledaDate = sdf.parse(datumPregleda);
		
		List<Godisnji> godisnji = godisnjiService.getGodisnjiOdLekara(idLekara);
		 
		 
		 for (Godisnji godisnjiOdmor : godisnji) {
		        Date datumOd = sdf.parse(godisnjiOdmor.getDatumOd());
		        Date datumDo = sdf.parse(godisnjiOdmor.getDatumDo());
		        if (datumPregledaDate.compareTo(datumOd) >= 0 && datumPregledaDate.compareTo(datumDo) <= 0) {
		        	flagGodisnji = true;
				}
			}
		 
		 if(flagGodisnji == true) {
			 System.out.println("prvi if");
			 return new ResponseEntity<>("morate promeniti datum posto ste tad na godisnjem", HttpStatus.INTERNAL_SERVER_ERROR); 
		 }
		
		
			if(trajanjePregleda.equals("NONE")) {
				 System.out.println("drugi if");
				return new ResponseEntity<>("morate uneti trajanje pregleda da bi ste dobili moguca vremena", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		 
		int odH = Integer.parseInt(lekar.getRadnoOd().split(":")[0]) * 60;
		int doH = Integer.parseInt(lekar.getRadnoDo().split(":")[0]) * 60;

		String[] yyyymmdd = datumPregleda.split("-");
		String datum = yyyymmdd[2] + "/" + yyyymmdd[1] + "/" + yyyymmdd[0];

		double trajanje = Double.parseDouble(trajanjePregleda) * 60;
		
		System.out.println(trajanjePregleda);
		List<String> slobodniTermini = new ArrayList<String>();

		List<Pregled> pregledi = pregledService.getPregledeOdLekara(lekar.getId());
		List<Operacija> operacije = operacijaService.findAll();
		List<Zahtev> zahtevi = zahteviService.getZahteveOdLekara(lekar.getId());

		for (int i = odH; i < doH; i +=trajanje ) {
	System.out.println("ovo je vreme" + i);
			
			boolean exist = false;
			for (Pregled p : pregledi) {
				if (datum.equals(p.getDatum())) {
					System.out.println(datum);
					System.out.println(p.getDatum());
					int pregledOd = Integer.parseInt(p.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(p.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + p.getTrajanjePregleda() * 60);
					if(!((i < pregledOd && i+trajanje <= pregledOd) || (i >= pregledDo && i+trajanje > pregledDo)))
					 {
						System.out.println(exist);
						
						i = pregledDo- (int) trajanje ;
						exist = true;
						break;
					}
				}
			}
			
			
			for (Zahtev p : zahtevi) {
				if (datum.equals(p.getDatum())) {
					System.out.println(datum);
					System.out.println(p.getDatum());
					int pregledOd = Integer.parseInt(p.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(p.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + p.getTrajanjePregleda() * 60);
					if(!((i < pregledOd && i+trajanje <= pregledOd) || (i >= pregledDo && i+trajanje > pregledDo)))
					 {
						System.out.println(exist);
						i = pregledDo- (int) trajanje ;
						exist = true;
						break;
					}
				}
			}
			
			for (Operacija o : operacije) {
				if (datum.equals(o.getDatum()) && o.getLekariKlinike().contains(lekar) ) {
					System.out.println(datum);
					System.out.println(o.getDatum());
					int pregledOd = Integer.parseInt(o.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(o.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + o.getTrajanjeOperacije() * 60);
					if(!((i < pregledOd && i+trajanje <= pregledOd) || (i >= pregledDo && i+trajanje > pregledDo)))
					 {
						System.out.println(exist);
						i = pregledDo- (int) trajanje ;
						exist = true;
						break;
					}
				}
			}
			
			if (!exist) {
				int terminOd = i;
				double terminDo = i + Double.parseDouble(trajanjePregleda) *60;

				String terminOdStr = (terminOd / 60) + ":"
						+ (((terminOd - (terminOd / 60) * 60) == 0) ? "00" : (terminOd - (terminOd / 60) * 60));
			/*	String terminDoStr = (terminDo / 60) + ":"
						+ (((terminDo - (terminDo / 60) * 60) == 0) ? "00" : (terminDo - (terminDo / 60) * 60)); */

				slobodniTermini.add(terminOdStr);
			}
		}

		return new ResponseEntity<>(slobodniTermini, HttpStatus.OK);
		
		
	}

	
	@GetMapping(value = "/slobodniTermini/{idLekara}/{datumPregleda}")
	public ResponseEntity<List<String>> getSlobodniTermini(@PathVariable Long idLekara,
			@PathVariable String datumPregleda) {
		Lekar lekar = lekarService.findOne(idLekara);

		int odH = Integer.parseInt(lekar.getRadnoOd().split(":")[0]) * 60;
		int doH = Integer.parseInt(lekar.getRadnoDo().split(":")[0]) * 60;

		String[] yyyymmdd = datumPregleda.split("-");
		String datum = yyyymmdd[2] + "/" + yyyymmdd[1] + "/" + yyyymmdd[0];

		List<String> slobodniTermini = new ArrayList<String>();

		List<Pregled> pregledi = pregledService.getPregledeOdLekara(lekar.getId());
		List<Zahtev> zahtevi = zahteviService.getZahteveOdLekara(lekar.getId());
		List<Operacija> operacije = operacijaService.getOperacijeOdLekara(lekar.getId());

		for (int i = odH; i < doH; i += 30) {
			boolean exist = false;
			for (Pregled pregled : pregledi) {
				if (datum.equals(pregled.getDatum())) {
					int pregledOd = Integer.parseInt(pregled.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(pregled.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + pregled.getTrajanjePregleda() * 60);
					if ((i == pregledOd || i + 30 == pregledDo)
							|| ((i > pregledOd && i < pregledDo) && (i + 30 > pregledOd && i + 30 < pregledDo))) {
						exist = true;
						break;
					}
				}
			}
			
			for (Zahtev zahtev : zahtevi) {
				if (datum.equals(zahtev.getDatum())) {
					int pregledOd = Integer.parseInt(zahtev.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(zahtev.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + zahtev.getTrajanjePregleda() * 60);
					if ((i == pregledOd || i + 30 == pregledDo)
							|| ((i > pregledOd && i < pregledDo) && (i + 30 > pregledOd && i + 30 < pregledDo))) {
						exist = true;
						break;
					}
				}
			}
			
			for (Operacija operacija : operacije) {
				if (datum.equals(operacija.getDatum())) {
					int pregledOd = Integer.parseInt(operacija.getVreme().split(":")[0]) * 60
							+ Integer.parseInt(operacija.getVreme().split(":")[1]);
					int pregledDo = (int) (pregledOd + operacija.getTrajanjeOperacije() * 60);
					if ((i == pregledOd || i + 30 == pregledDo)
							|| ((i > pregledOd && i < pregledDo) && (i + 30 > pregledOd && i + 30 < pregledDo))) {
						exist = true;
						break;
					}
				}
			}
			
			if (!exist) {
				int terminOd = i;
				int terminDo = i + 30;

				String terminOdStr = (terminOd / 60) + ":"
						+ (((terminOd - (terminOd / 60) * 60) == 0) ? "00" : (terminOd - (terminOd / 60) * 60));
				String terminDoStr = (terminDo / 60) + ":"
						+ (((terminDo - (terminDo / 60) * 60) == 0) ? "00" : (terminDo - (terminDo / 60) * 60));

				slobodniTermini.add(terminOdStr + "-" + terminDoStr);
			}
		}

		return new ResponseEntity<>(slobodniTermini, HttpStatus.OK);
		
	}
	

	@PostMapping(value = "/otkaziPregledLekar/{idPregled}/{idLekar}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> otkaziZahtevPacijent(@PathVariable Long idPregled,@PathVariable Long idLekar)
			throws ParseException {
		Pregled pregled = pregledService.findOne(idPregled);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date vremePregleda = formatter.parse(pregled.getDatum() + " " + pregled.getVreme());
		Date vremeTrenutno = new Date();

		if (Math.abs((vremePregleda.getTime() - vremeTrenutno.getTime()) / 1000 / 60 / 60) > 23 && pregled.getZavrsen() == false
				) {
			pregledService.izrisiPregled(pregled);
			
			List<Pregled> pregledi = pregledService.findAll();
			List<PregledDTO> preglediDTO = new ArrayList<>();

			for (Pregled pregledTemp : pregledi) {
				if (pregledTemp.getLekar().getId() == idLekar && pregledTemp.getZavrsen() == false) {
					preglediDTO.add(new PregledDTO(pregledTemp));
				}
			}

			return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>("Ne mozete otkazati pregled, izvrsava se za manje od 24h", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/otkaziOperacijuLekar/{idOperacije}/{idLekar}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> otkaziOperacijuPacijent(@PathVariable Long idOperacije,@PathVariable Long idLekar)
			throws ParseException {
		Operacija operacija = operacijaService.findOne(idOperacije);
		Lekar lekar = lekarService.findOne(idLekar);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date vremePregleda = formatter.parse(operacija.getDatum() + " " + operacija.getVreme());
		Date vremeTrenutno = new Date();

		if (Math.abs((vremePregleda.getTime() - vremeTrenutno.getTime()) / 1000 / 60 / 60) > 23 && operacija.getZavrsen() == false
				) {
			operacijaService.izbrisiOperaciju(operacija);
			
			List<Operacija> operacije = operacijaService.findAll();
			List<OperacijaDTO> operacijaDTO = new ArrayList<>();

			for (Operacija operacijaTemp : operacije) {
				if (operacijaTemp.getLekariKlinike().contains(lekar) && operacijaTemp.getZavrsen() == false) {
					operacijaDTO.add(new OperacijaDTO(operacijaTemp));
				}
			}

			return new ResponseEntity<>(operacijaDTO, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>("Ne mozete otkazati operaciju, izvrsava se za manje od 24h", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/lekarPregleda/{id}")
	public ResponseEntity<LekarDTO> lekarPregleda(@PathVariable Long id){
		
		Pregled pregled = pregledService.findOne(id);
		
		LekarDTO lekarDTO = new LekarDTO(pregled.getLekar());
		
		return new ResponseEntity<>(lekarDTO ,HttpStatus.OK);

	}
}
