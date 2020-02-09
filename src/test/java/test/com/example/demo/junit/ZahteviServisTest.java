package test.com.example.demo.junit;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import com.example.demo.repository.OperacijRepository;
import com.example.demo.repository.SalaKlinikeRepository;
import com.example.demo.repository.TipPregledaRepository;
import com.example.demo.repository.ZahteviRepository;
import com.example.demo.service.PregledService;
import com.example.demo.service.ZahteviService;

import test.com.example.demo.TestBase;

public class ZahteviServisTest extends TestBase<ZahteviService> {

	protected final ZahteviRepository zahteviRepository = Mockito.mock(ZahteviRepository.class);

	protected final SalaKlinikeRepository salaKlinikeRepository= Mockito.mock(SalaKlinikeRepository.class);

	protected final TipPregledaRepository tipPregledaRepository = Mockito.mock(TipPregledaRepository.class);

	protected final OperacijRepository operacijaRepository = Mockito.mock(OperacijRepository.class);

	@Override
	@BeforeEach
	protected void init() {
		service = new ZahteviService(zahteviRepository, salaKlinikeRepository, tipPregledaRepository, operacijaRepository);
	}
}
