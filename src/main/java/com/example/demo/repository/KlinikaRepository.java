package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Klinika;

public interface KlinikaRepository extends JpaRepository< Klinika, Long>{

	@Query("select distinct drzava from Klinika klinika")
    List<String> getDrzave();
	
	@Query("select distinct grad from Klinika klinika")
	List<String> getGradovi();

	@Query("select distinct grad from Klinika klinika where klinika.drzava = ?1")
	List<String> getGradovi(String drzava);
}