package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.model.IzvestajOPregledu;
import com.example.demo.model.Lek;

public class IzvestajOPregleduDTO {


		private Long id;
		private PacijentDTO pacijent;
		private LekarDTO lekar;
		private BolestiDTO bolest;
		private List<LekDTO> lekovi;
		private ReceptDTO recept;
		
		
		
		public IzvestajOPregleduDTO() {
			super();
			lekovi = new ArrayList<LekDTO>();
		}

		public IzvestajOPregleduDTO(IzvestajOPregledu izvestaj) {
			super();
			this.id = izvestaj.getId();
			this.pacijent = new PacijentDTO(izvestaj.getPacijenta());
			this.lekar = new LekarDTO(izvestaj.getLekara());
			this.bolest = new BolestiDTO(izvestaj.getBolest());
			this.recept= new ReceptDTO(izvestaj.getRecept());
			this.lekovi = new ArrayList<LekDTO>();
			for (Lek lek : izvestaj.getLekoviIzvestaja()) {
				lekovi.add(new LekDTO(lek));
			}
		}

		public IzvestajOPregleduDTO(Long id, PacijentDTO pacijent, LekarDTO lekar, BolestiDTO bolest, List<LekDTO> lekovi, ReceptDTO recept) {
			super();
			this.id = id;
			this.pacijent = pacijent;
			this.lekar = lekar;
			this.bolest = bolest;
			this.lekovi = lekovi;
			this.recept = recept;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		

		public ReceptDTO getRecept() {
			return recept;
		}

		public void setRecept(ReceptDTO recept) {
			this.recept = recept;
		}

		public PacijentDTO getPacijent() {
			return pacijent;
		}

		public void setPacijent(PacijentDTO pacijent) {
			this.pacijent = pacijent;
		}

		public LekarDTO getLekar() {
			return lekar;
		}

		public void setLekar(LekarDTO lekar) {
			this.lekar = lekar;
		}

		public BolestiDTO getBolest() {
			return bolest;
		}

		public void setBolest(BolestiDTO bolest) {
			this.bolest = bolest;
		}

		public List<LekDTO> getLekovi() {
			return lekovi;
		}

		public void setLekovi(List<LekDTO> lekovi) {
			this.lekovi = lekovi;
		}
		

		

	
	
}
