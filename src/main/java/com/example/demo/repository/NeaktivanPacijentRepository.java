package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.NeaktivanPacijent;
import com.example.demo.model.Pacijent;

public interface NeaktivanPacijentRepository extends JpaRepository<NeaktivanPacijent, Long>{
	NeaktivanPacijent findByMail( String mail );
}
