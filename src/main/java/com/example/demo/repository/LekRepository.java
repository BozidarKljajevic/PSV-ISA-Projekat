package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Lek;
import com.example.demo.model.NeaktivanPacijent;
import com.example.demo.model.Pacijent;

public interface LekRepository extends JpaRepository<Lek, Long>{
	
}
