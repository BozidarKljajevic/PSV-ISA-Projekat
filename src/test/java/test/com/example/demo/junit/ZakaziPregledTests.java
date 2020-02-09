package test.com.example.demo.junit;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.Lekar;
import com.example.demo.model.Pregled;
import com.example.demo.model.SalaKlinike;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.Zahtev;

public class ZakaziPregledTests extends PregledServiceTest {
	
	private Long LEKAR_ID = (long) 10;
	private Long TIPPREGLEDA_ID = (long) 20;
	private Long PREGLED_ID = (long) 5;
	private Long SALA_ID = (long) 8;
	private Long PACIJENT_ID = (long) 3;
	
	
	@Test
	public void trebaPozvatiPreglediRepository_kadaSeMetodaPoziva() {
		
		Pregled pregled = createPregled();
		
		service.zakaziPregled(pregled, PACIJENT_ID);
		
		Mockito.verify(pregledRepository, Mockito.times(1)).save(any(Pregled.class));
	}
	
	
	@Test
	public void daLiJeProsledjenDobarArgumentZaPregledRepository_kadaSeMetodaPoziva() {
		
		Pregled pregled = createPregled();
		Pregled expected = new Pregled();
		expected.setId(PREGLED_ID);
		service.zakaziPregled(pregled, PACIJENT_ID);
		
		Assertions.assertEquals(expected, pregled);
	}
	
	private Pregled createPregled() {
		Pregled pregled = new Pregled();
		
		pregled.setId(PREGLED_ID);
		pregled.setDatum("14/11/2020");
		Lekar lekar = new Lekar();
		lekar.setId(LEKAR_ID);
		pregled.setLekar(lekar);
		pregled.setIdPacijenta(PACIJENT_ID);
		pregled.setCena((double) 2000);
		pregled.setVreme("12:00");
		pregled.setTrajanjePregleda((double) 1.5);
		pregled.setZavrsen(false);
		SalaKlinike sala = new SalaKlinike();
		sala.setId(SALA_ID);
		pregled.setSala(sala);
		TipPregleda tipPregleda = new TipPregleda();
		tipPregleda.setId(TIPPREGLEDA_ID);
		pregled.setTipPregleda(tipPregleda);
		
		return pregled;
	}

}
