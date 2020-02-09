package test.com.example.demo.junit;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.model.Lekar;
import com.example.demo.model.Pregled;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.Zahtev;

public class DodajRezervisanuSaluTests extends ZahteviServisTest{

	private Long LEKAR_ID = (long) 8;
	private Long TIPPREGLEDA_ID = (long) 5;
	private Long ZAHTEV_ID = (long) 15;
	private Long SALA_ID = (long) 10;
	private Long PACIJENT_ID = (long) 20;
	
	@Test
	public void trebaPozvatiZahteviRepository_kadaSeMetodaPoziva() {
		
		Zahtev zahtev = createZahtev();
		
		service.dodajRezervisanuSalu(zahtev);
		
		Mockito.verify(zahteviRepository, Mockito.times(1)).save(any(Zahtev.class));
	}
	
	
	@Test
	public void daLiJeProsledjenDobarArgumentZaZahtevRepository_kadaSeMetodaPoziva() {
		
		Zahtev zahtev = createZahtev();
		Zahtev expected = new Zahtev();
		expected.setId(ZAHTEV_ID);
		service.dodajRezervisanuSalu(zahtev);
		
		Assertions.assertEquals(expected, zahtev);
	}
	
	private Zahtev createZahtev() {
		Zahtev zahtev = new Zahtev();
		
		zahtev.setId(ZAHTEV_ID);
		zahtev.setDatum("14/11/2020");
		zahtev.setCena((double) 5000);
		zahtev.setIdPacijenta(PACIJENT_ID);
		Lekar lekar = new Lekar();
		lekar.setId(LEKAR_ID);
		zahtev.setLekar(lekar);
		zahtev.setLekar1(null);
		zahtev.setLekar2(null);
		SalaKlinike sala = new SalaKlinike();
		sala.setId(SALA_ID);
		zahtev.setSala(sala);
		TipPregleda tip = new TipPregleda();
		tip.setId(TIPPREGLEDA_ID);
		tip.setCena("5000");
		zahtev.setTipPregleda(tip);
		zahtev.setTrajanjePregleda(1.0);
		zahtev.setVreme("12:00");
		
		return zahtev;
	}
}
