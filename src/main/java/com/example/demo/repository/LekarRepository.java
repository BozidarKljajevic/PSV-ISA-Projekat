package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Lekar;
import com.example.demo.model.Pacijent;

public interface LekarRepository extends JpaRepository< Lekar, Long>{

	Lekar findByMail( String mail );

	List<Lekar> findByTipPregledaId(Long id);
	
}