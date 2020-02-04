package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Pacijent extends User{

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<OcenaLekar> ocene = new HashSet<OcenaLekar>();
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	private Set<OcenaKlinika> oceneKlinike = new HashSet<OcenaKlinika>();

	public Set<OcenaLekar> getOcene() {
		return ocene;
	}

	public void setOcene(Set<OcenaLekar> ocene) {
		this.ocene = ocene;
	}

	public Set<OcenaKlinika> getOceneKlinike() {
		return oceneKlinike;
	}

	public void setOceneKlinike(Set<OcenaKlinika> oceneKlinike) {
		this.oceneKlinike = oceneKlinike;
	}
}
