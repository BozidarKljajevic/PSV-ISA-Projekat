package test.com.example.demo.junit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.dto.LekarDTO;
import com.example.demo.dto.TipPregledaDTO;
import com.example.demo.dto.ZahtevDTO;
import com.example.demo.model.Lekar;
import com.example.demo.model.TipPregleda;
import com.example.demo.model.Zahtev;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

public class DodajZahtevTests extends PregledServiceTest{
	
	private Long LEKAR_ID = (long) 10;
	private Long TIPPREGLEDA_ID = (long) 20;
	
	@Test
	public void trebaPozvatiLekarRepository_kadaSeMetodaPoziva() {
		Mockito.doReturn(getLekar()).when(lekarRepository).findById(any(Long.class));
		Mockito.doReturn(getTipPregleda()).when(tipPregledaRepository).findById(any(Long.class));
		
		ZahtevDTO zahtevDTO = createZahtevDTO();
		
		service.dodajZahtev(zahtevDTO);
		
		Mockito.verify(lekarRepository, Mockito.times(1)).findById(any(Long.class));
	}
	
	@Test
	public void trebaPozvatiTipPregledaRepository_kadaSeMetodaPoziva() {
		Mockito.doReturn(getLekar()).when(lekarRepository).findById(any(Long.class));
		Mockito.doReturn(getTipPregleda()).when(tipPregledaRepository).findById(any(Long.class));
		
		ZahtevDTO zahtevDTO = createZahtevDTO();
		
		service.dodajZahtev(zahtevDTO);
		
		Mockito.verify(tipPregledaRepository, Mockito.times(1)).findById(any(Long.class));
	}
	
	@Test
	public void trebaPozvatiZahteviRepository_kadaSeMetodaPoziva() {
		Mockito.doReturn(getLekar()).when(lekarRepository).findById(any(Long.class));
		Mockito.doReturn(getTipPregleda()).when(tipPregledaRepository).findById(any(Long.class));
		
		ZahtevDTO zahtevDTO = createZahtevDTO();
		
		service.dodajZahtev(zahtevDTO);
		
		Mockito.verify(zahteviRepository, Mockito.times(1)).save(any(Zahtev.class));
	}
	
	@Test
	public void daLiJeProsledjenDobarArgumentZaLekarRepository_kadaSeMetodaPoziva() {
		Mockito.doReturn(getLekar()).when(lekarRepository).findById(Mockito.eq(LEKAR_ID));
		Mockito.doReturn(getTipPregleda()).when(tipPregledaRepository).findById(any(Long.class));
		
		ZahtevDTO zahtevDTO = createZahtevDTO();
		
		service.dodajZahtev(zahtevDTO);
		
		Mockito.verify(lekarRepository, Mockito.times(1)).findById(Mockito.eq(LEKAR_ID));
	}
	
	@Test
	public void daLiJeProsledjenDobarArgumentZaZahteviRepository_kadaSeMetodaPoziva() {
		Mockito.doReturn(getLekar()).when(lekarRepository).findById(any(Long.class));
		Mockito.doReturn(getTipPregleda()).when(tipPregledaRepository).findById(Mockito.eq(TIPPREGLEDA_ID));
		
		ZahtevDTO zahtevDTO = createZahtevDTO();
		
		service.dodajZahtev(zahtevDTO);
		
		Mockito.verify(tipPregledaRepository, Mockito.times(1)).findById(Mockito.eq(TIPPREGLEDA_ID));
	}
	

	private Optional<Lekar> getLekar() {
		Lekar lekar = new Lekar();
		
		lekar.setId(LEKAR_ID);
		lekar.setAdresa("Danila Kisa 5");
		lekar.setBrojTelefona("+381/65-029128");
		lekar.setDrzava("Srbija");
		lekar.setEnabled(true);
		lekar.setGrad("Novi Sad");
		lekar.setIme("Damjan");
		lekar.setMail("panticdamjan@gmail.com");
		lekar.setPrezime("Pantic");
		lekar.setPromenjenaSifra(true);
		
		return Optional.of(lekar);
	}
	
	private Optional<TipPregleda> getTipPregleda() {
		TipPregleda tipPregleda = new TipPregleda();
		
		tipPregleda.setId(TIPPREGLEDA_ID);
		tipPregleda.setNaziv("Ocni");
		tipPregleda.setCena("2000");
		
		return Optional.of(tipPregleda);
	}
	
	private ZahtevDTO createZahtevDTO() {
		ZahtevDTO zahtevDTO = new ZahtevDTO();
		
		zahtevDTO.setDatum("22/08/2020");
		LekarDTO lekarDTO = new LekarDTO();
		lekarDTO.setId(LEKAR_ID);
		zahtevDTO.setLekar(lekarDTO);
		zahtevDTO.setIdPacijenta((long) 2);
		zahtevDTO.setCena((double) 2000);
		zahtevDTO.setVreme("12:00");
		zahtevDTO.setTrajanjePregleda((double) 1.5);
		zahtevDTO.setIzbor(true);
		TipPregledaDTO tipPregledaDTO = new TipPregledaDTO();
		tipPregledaDTO.setId(TIPPREGLEDA_ID);
		zahtevDTO.setTipPregleda(tipPregledaDTO);
		
		return zahtevDTO;
	}
}
