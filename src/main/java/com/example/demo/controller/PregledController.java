package com.example.demo.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GodisnjiDTO;
import com.example.demo.dto.KlinikaDTO;
import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.MedicinskaSestraDTO;
import com.example.demo.dto.PregledDTO;
import com.example.demo.dto.SalaKlinikeDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.repository.KlinikaRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.model.AdminKlinike;
import com.example.demo.model.Godisnji;
import com.example.demo.model.MedicinskaSestra;
import com.example.demo.model.Operacija;
import com.example.demo.model.Pregled;
import com.example.demo.service.AdminKlinikeService;
import com.example.demo.service.EmailService;
import com.example.demo.service.GodisnjiService;
import com.example.demo.service.KlinikaService;
import com.example.demo.service.OperacijaService;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.User;
import com.example.demo.model.Zahtev;
import com.example.demo.service.PregledService;
import com.example.demo.service.ZahteviService;

@RestController
@RequestMapping(value = "/pregled")
public class PregledController {

	@Autowired
	private PregledService pregledService;

	@Autowired
	private ZahteviService zahteviService;
	@Autowired
	private OperacijaService operacijaService;
	
	@Autowired
	private GodisnjiService godisnjiService;

	@Autowired
	private AdminKlinikeService adminKlinikeService;

	@Autowired
	private EmailService emailService;

	@PostMapping(value = "/dodajPregled", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<PregledDTO> dodajPregled(@RequestBody PregledDTO pregledDTO) throws ParseException {

		PregledDTO pregleddto = new PregledDTO();
		boolean flag = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] datum = pregledDTO.getDatum().split("/");
		Date datumPregledaDate = sdf.parse(datum[2] + "-" + datum[1] + "-" + datum[0]);
		
		String[] radnoOd = pregledDTO.getLekar().getRadnoOd().split(":");
		double satOd = Double.parseDouble(radnoOd[0]);
		double minOd = Double.parseDouble(radnoOd[1]);
		double minutiRadnoOd = satOd * 60 + minOd;

		String[] radnoDo = pregledDTO.getLekar().getRadnoDo().split(":");
		double satDo = Double.parseDouble(radnoDo[0]);
		double minDo = Double.parseDouble(radnoDo[1]);
		double minutiRadnoDo = satDo * 60 + minDo;

		List<Pregled> pregledi = pregledService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		List<Operacija> operacije = operacijaService.findAll();
		String datumStr = pregledDTO.getDatum();
		// String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
		String[] vreme = pregledDTO.getVreme().split(":");
		double sat = Double.parseDouble(vreme[0]);
		double min = Double.parseDouble(vreme[1]);

		double trajanjeMin = pregledDTO.getTrajanjePregleda() * 60;
		double trajanjeMinOstatak = trajanjeMin % 60;
		double trajanjeSat = trajanjeMin / 60;
		int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak) / 60);
		double krajPregledaMin = min + trajanjeMinOstatak;

		if (krajPregledaMin == 60) {
			krajPregledaMin = 0;
			krajPregledaSat++;
		}

		
		double minutiPocetak = sat*60 + min;
		double minutiKraj = krajPregledaSat*60 + krajPregledaMin;
		
		 if(minutiPocetak < minutiRadnoOd || minutiKraj > minutiRadnoDo)
			{
				flag = true;
				System.out.println("pocetka pregleda" + minutiPocetak);
				System.out.println("kraj pregleda" + minutiKraj);
				System.out.println("radno do" + minutiRadnoOd);
				System.out.println("radno od" +minutiRadnoDo);
				
			}
		
		 
		 List<Godisnji> godisnji = godisnjiService.getGodisnjiOdLekara(pregledDTO.getLekar().getId());
		 
		 
		 for (Godisnji godisnjiOdmor : godisnji) {
		        Date datumOd = sdf.parse(godisnjiOdmor.getDatumOd());
		        Date datumDo = sdf.parse(godisnjiOdmor.getDatumDo());
		        if (datumPregledaDate.compareTo(datumOd) >= 0 && datumPregledaDate.compareTo(datumDo) <= 0) {
		        	flag = true;
				}
			}
		 
		
			for (Pregled p : pregledi) {
				if (pregledDTO.getDatum().equals(p.getDatum())
						&& (p.getSala().getId() == pregledDTO.getSala().getId() || p.getLekar().getId() == pregledDTO.getLekar().getId())) {
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
			
			
			for (Operacija o : operacije) {
				if (pregledDTO.getDatum().equals(o.getDatum())
						&& (o.getSala().getId() == pregledDTO.getSala().getId() || o.getLekariKlinike().contains(pregledDTO.getLekar()))) {
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
					
				if(pregledDTO.getDatum().equals(z.getDatum()) && (pregledDTO.getLekar().getId() == z.getLekar().getId() || pregledDTO.getSala().getId() == z.getSala().getId() )) {

					String[] vremeZ = z.getVreme().split(":");
					double satZ = Double.parseDouble(vremeZ[0]);
					double minZ = Double.parseDouble(vremeZ[1]);

					double trajanjeMinZ = z.getTrajanjePregleda() * 60;
					double trajanjeMinOstatakZ = trajanjeMinZ % 60;
					double trajanjeSatZ = trajanjeMinZ / 60;
					int krajPregledaSatZ = (int) (satZ + (trajanjeMinZ - trajanjeMinOstatakZ) / 60);
					double krajPregledaMinZ = minZ + trajanjeMinOstatakZ;

					if (krajPregledaMinZ == 60) {
						krajPregledaMinZ = 0;
						krajPregledaSatZ++;
					}

					double minutiPocetakZ = satZ * 60 + minZ;
					double minutiKrajZ = krajPregledaSatZ * 60 + krajPregledaMinZ;
					System.out.println(minutiPocetakZ);
					System.out.println(minutiKrajZ);
					if (!((minutiPocetak < minutiPocetakZ && minutiKraj <= minutiPocetakZ)
							|| (minutiPocetak >= minutiKrajZ && minutiKraj > minutiKrajZ))) {
						flag = true;
					}
				}
			} else if (pregledDTO.getDatum().equals(z.getDatum())
					&& pregledDTO.getLekar().getId() == z.getLekar().getId()) {
				String[] vremeZ = z.getVreme().split(":");
				double satZ = Double.parseDouble(vremeZ[0]);
				double minZ = Double.parseDouble(vremeZ[1]);

				double trajanjeMinZ = z.getTrajanjePregleda() * 60;
				double trajanjeMinOstatakZ = trajanjeMinZ % 60;
				double trajanjeSatZ = trajanjeMinZ / 60;
				int krajPregledaSatZ = (int) (satZ + (trajanjeMinZ - trajanjeMinOstatakZ) / 60);
				double krajPregledaMinZ = minZ + trajanjeMinOstatakZ;

				if (krajPregledaMinZ == 60) {
					krajPregledaMinZ = 0;
					krajPregledaSatZ++;
				}

				double minutiPocetakZ = satZ * 60 + minZ;
				double minutiKrajZ = krajPregledaSatZ * 60 + krajPregledaMinZ;
				System.out.println(minutiPocetakZ);
				System.out.println(minutiKrajZ);
				if (!((minutiPocetak < minutiPocetakZ && minutiKraj <= minutiPocetakZ)
						|| (minutiPocetak >= minutiKrajZ && minutiKraj > minutiKrajZ))) {
					flag = true;
				}
			}
		}

		if (flag == false) {
			pregleddto = pregledService.dodajPregled(pregledDTO);

		}

		if (flag == true) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(pregleddto, HttpStatus.OK);
	}

	@GetMapping(value = "/preglediKlinike/{id}")
	public ResponseEntity<?> getPreglediKlinike(@PathVariable Long id) {

		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping(value = "/predefinisaniPregledi")
	public ResponseEntity<?> getPredefinisaniPregledi() {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();

		for (Pregled pregled : pregledi) {
			if (pregled.getIdPacijenta() == null) {
				preglediDTO.add(new PregledDTO(pregled));
			}
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	
	
	@GetMapping(value = "/slobodniPregledi/{id}")
	public ResponseEntity<?> getSlobodniPregledi(@PathVariable Long id) {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();
		AdminKlinike admin = adminKlinikeService.findOne(id);
		for (Pregled pregled : pregledi) {
			if (pregled.getIdPacijenta() == null && admin.getKlinika().getId() == pregled.getLekar().getKlinika().getId()) {
				preglediDTO.add(new PregledDTO(pregled));
			}
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}
	

	@GetMapping(value = "/PreglediLekar/{id}/{idPac}")
	public ResponseEntity<?> getPreglediLekar(@PathVariable Long id, @PathVariable Long idPac) {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();

		for (Pregled pregled : pregledi) {
			if (pregled.getIdPacijenta() != null && pregled.getLekar().getId() == id
					&& pregled.getIdPacijenta() == idPac && pregled.getZavrsen() == false) {
				preglediDTO.add(new PregledDTO(pregled));
			}
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/predefinisaniPreglediKlinike/{id}")
	public ResponseEntity<?> getPredefinisaniPreglediKlinike(@PathVariable Long id) {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();

		for (Pregled pregled : pregledi) {
			if (pregled.getIdPacijenta() == null && pregled.getLekar().getKlinika().getId() == id) {
				preglediDTO.add(new PregledDTO(pregled));
			}
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/zakzaniPregledi/{id}")
	public ResponseEntity<?> getZakazaniPregledi(@PathVariable Long id) {

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();

		for (Pregled pregled : pregledi) {
			if (pregled.getIdPacijenta() == id) {
				preglediDTO.add(new PregledDTO(pregled));
			}
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/zakaziPregled/{idPregleda}/{idPacijenta}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> zakaziPregled(@PathVariable Long idPregleda, @PathVariable Long idPacijenta)
			throws MailException, InterruptedException {

		Pregled zakaziPregled = pregledService.findOne(idPregleda);
		pregledService.zakaziPregled(zakaziPregled, idPacijenta);

		Long idKlinike = zakaziPregled.getLekar().getKlinika().getId();

		List<AdminKlinike> adminiKlinike = adminKlinikeService.findAll();

		List<Pregled> pregledi = pregledService.findAll();
		List<PregledDTO> preglediDTO = new ArrayList<>();

		for (Pregled pregled : pregledi) {
			if (pregled.getIdPacijenta() == null) {
				preglediDTO.add(new PregledDTO(pregled));
			}
		}

		for (AdminKlinike admin : adminiKlinike) {
			if (admin.getKlinika().getId() == idKlinike) {
				emailService.sendNotificaitionAsync((User) admin,
						"Jedan od predefinisanih pregleda je zakazan od strane pacijenta sa jedinstvenom oznakom"
								+ idPacijenta);
			}
		}

		return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/podnesiZahtev")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> podnesiZahtev(@RequestBody ZahtevDTO zahtevDTO)
			throws MailException, InterruptedException {

		zahtevDTO.setIzbor(true);
		zahtevDTO.setTipPregleda(zahtevDTO.getLekar().getTipPregleda());

		pregledService.dodajZahtev(zahtevDTO);

		List<AdminKlinike> adminiKlinika = adminKlinikeService.findAll();

		for (AdminKlinike adminKlinike : adminiKlinika) {
			if (adminKlinike.getKlinika().getId() == zahtevDTO.getLekar().getKlinika().getId()) {
				String message = "Podnesen je zahtev za pregle na Vasoj klinici za lekara "
						+ zahtevDTO.getLekar().getIme() + " " + zahtevDTO.getLekar().getPrezime();
				emailService.sendNotificaitionAsync((User) adminKlinike, message);
			}
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping(value = "/podnesiZahtevLekar/{id}")
	@PreAuthorize("hasAuthority('LEKAR')")
	public ResponseEntity<?> podnesiZahtevLekar(@RequestBody ZahtevDTO zahtevDTO, @PathVariable Long id)
			throws MailException, InterruptedException {

		Pregled pregled = pregledService.findOne(id);
		boolean flag = false;
		zahtevDTO.setLekar(new LekarDTO(pregled.getLekar()));
		zahtevDTO.setTipPregleda(new TipPregledaDTO(pregled.getLekar().getTipPregleda()));
		zahtevDTO.setIdPacijenta(pregled.getIdPacijenta());
		zahtevDTO.setCena(Double.parseDouble(pregled.getTipPregleda().getCena()));

		String[] radnoOd = pregled.getLekar().getRadnoOd().split(":");
		double satOd = Double.parseDouble(radnoOd[0]);
		double minOd = Double.parseDouble(radnoOd[1]);
		double minutiRadnoOd = satOd * 60 + minOd;

		String[] radnoDo = pregled.getLekar().getRadnoDo().split(":");
		double satDo = Double.parseDouble(radnoDo[0]);
		double minDo = Double.parseDouble(radnoDo[1]);
		double minutiRadnoDo = satDo * 60 + minDo;

		List<Pregled> pregledi = pregledService.findAll();
		List<Zahtev> zahtevi = zahteviService.findAll();
		String datumStr = zahtevDTO.getDatum();
		// String datumStr = datum[2]+"/"+datum[1]+"/"+datum[0];
		String[] vreme = zahtevDTO.getVreme().split(":");
		double sat = Double.parseDouble(vreme[0]);
		double min = Double.parseDouble(vreme[1]);

		double trajanjeMin = zahtevDTO.getTrajanjePregleda() * 60;
		double trajanjeMinOstatak = trajanjeMin % 60;
		double trajanjeSat = trajanjeMin / 60;
		int krajPregledaSat = (int) (sat + (trajanjeMin - trajanjeMinOstatak) / 60);
		double krajPregledaMin = min + trajanjeMinOstatak;

		if (krajPregledaMin == 60) {
			krajPregledaMin = 0;
			krajPregledaSat++;
		}

		
		double minutiPocetak = sat*60 + min;
		double minutiKraj = krajPregledaSat*60 + krajPregledaMin;
		
		 if(minutiPocetak < minutiRadnoOd || minutiKraj > minutiRadnoDo)
			{
				flag = true;
				System.out.println("pocetka pregleda" + minutiPocetak);
				System.out.println("kraj pregleda" + minutiKraj);
				System.out.println("radno do" + minutiRadnoOd);
				System.out.println("radno od" +minutiRadnoDo);
			}
		
		
		

		for (Pregled p : pregledi) {
			if (zahtevDTO.getDatum().equals(p.getDatum()) && p.getLekar().getId() == zahtevDTO.getLekar().getId()) {
				String[] vremeP = p.getVreme().split(":");
				double satP = Double.parseDouble(vremeP[0]);
				double minP = Double.parseDouble(vremeP[1]);
				double trajanjeMinP = p.getTrajanjePregleda() * 60;
				double trajanjeMinOstatakP = trajanjeMinP % 60;
				double trajanjeSatP = trajanjeMinP / 60;
				int krajPregledaSatP = (int) (satP + (trajanjeMinP - trajanjeMinOstatakP) / 60);
				double krajPregledaMinP = minP + trajanjeMinOstatakP;
				if (krajPregledaMinP == 60) {
					krajPregledaMinP = 0;
					krajPregledaSatP++;
				}
				double minutiPocetakP = satP * 60 + minP;
				double minutiKrajP = krajPregledaSatP * 60 + krajPregledaMinP;
				System.out.println(minutiPocetakP);
				System.out.println(minutiKrajP);
				if (!((minutiPocetak < minutiPocetakP && minutiKraj <= minutiPocetakP)
						|| (minutiPocetak >= minutiKrajP && minutiKraj > minutiKrajP))) {
					flag = true;
				}
			}

		}

		for (Zahtev z : zahtevi) {
			if (zahtevDTO.getDatum().equals(z.getDatum()) && zahtevDTO.getLekar().getId() == z.getLekar().getId()) {
				String[] vremeZ = z.getVreme().split(":");
				double satZ = Double.parseDouble(vremeZ[0]);
				double minZ = Double.parseDouble(vremeZ[1]);

				double trajanjeMinZ = z.getTrajanjePregleda() * 60;
				double trajanjeMinOstatakZ = trajanjeMinZ % 60;
				double trajanjeSatZ = trajanjeMinZ / 60;
				int krajPregledaSatZ = (int) (satZ + (trajanjeMinZ - trajanjeMinOstatakZ) / 60);
				double krajPregledaMinZ = minZ + trajanjeMinOstatakZ;

				if (krajPregledaMinZ == 60) {
					krajPregledaMinZ = 0;
					krajPregledaSatZ++;
				}

				double minutiPocetakZ = satZ * 60 + minZ;
				double minutiKrajZ = krajPregledaSatZ * 60 + krajPregledaMinZ;
				System.out.println(minutiPocetakZ);
				System.out.println(minutiKrajZ);
				if (!((minutiPocetak < minutiPocetakZ && minutiKraj <= minutiPocetakZ)
						|| (minutiPocetak >= minutiKrajZ && minutiKraj > minutiKrajZ))) {
					flag = true;
				}
			}
		}

		if (flag == true) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (flag == false) {
			pregledService.dodajZahtev(zahtevDTO);

			List<AdminKlinike> adminiKlinika = adminKlinikeService.findAll();

			for (AdminKlinike adminKlinike : adminiKlinika) {
				if (adminKlinike.getKlinika().getId() == zahtevDTO.getLekar().getKlinika().getId()) {
					String message = "Podnesen je zahtev za pregled na Vasoj klinici za lekara "
							+ zahtevDTO.getLekar().getIme() + " " + zahtevDTO.getLekar().getPrezime();
					emailService.sendNotificaitionAsync((User) adminKlinike, message);
				}
			}

		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping(value = "/otkaziPregledPacijent/{idPregled}/{idPacijent}")
	@PreAuthorize("hasAuthority('PACIJENT')")
	public ResponseEntity<?> otkaziZahtevPacijent(@PathVariable Long idPregled, @PathVariable Long idPacijent)
			throws ParseException {
		Pregled pregled = pregledService.findOne(idPregled);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date vremePregleda = formatter.parse(pregled.getDatum() + " " + pregled.getVreme());
		Date vremeTrenutno = new Date();

		if (Math.abs((vremePregleda.getTime() - vremeTrenutno.getTime()) / 1000 / 60 / 60) > 23 && pregled.getZavrsen() == false
				&& pregled.getIdPacijenta() == idPacijent) {
			pregledService.izrisiPregled(pregled);
			
			List<Pregled> pregledi = pregledService.findAll();
			List<PregledDTO> preglediDTO = new ArrayList<>();

			for (Pregled pregledTemp : pregledi) {
				if (pregledTemp.getIdPacijenta() == idPacijent) {
					preglediDTO.add(new PregledDTO(pregledTemp));
				}
			}

			return new ResponseEntity<>(preglediDTO, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>("Ne mozete otkazati pregled, izvrsava se za manje od 24h", HttpStatus.BAD_REQUEST);
		}
	}
}
