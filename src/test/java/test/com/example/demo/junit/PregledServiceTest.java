package test.com.example.demo.junit;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import com.example.demo.repository.LekarRepository;
import com.example.demo.repository.PregledRepository;
import com.example.demo.repository.SalaKlinikeRepository;
import com.example.demo.repository.TipPregledaRepository;
import com.example.demo.repository.ZahteviRepository;
import com.example.demo.service.PregledService;

import test.com.example.demo.TestBase;

public class PregledServiceTest extends TestBase<PregledService>{

	
	protected final PregledRepository pregledRepository = Mockito.mock(PregledRepository.class);

	protected final ZahteviRepository zahteviRepository = Mockito.mock(ZahteviRepository.class);

	protected final LekarRepository lekarRepository = Mockito.mock(LekarRepository.class);

	protected final SalaKlinikeRepository salaKlinikeRepository = Mockito.mock(SalaKlinikeRepository.class);

	protected final TipPregledaRepository tipPregledaRepository = Mockito.mock(TipPregledaRepository.class);
	
	@Override
	@BeforeEach
	protected void init() {
		service = new PregledService(pregledRepository, zahteviRepository, lekarRepository, salaKlinikeRepository, tipPregledaRepository);
	}
}
