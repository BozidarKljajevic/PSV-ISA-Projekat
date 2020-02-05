package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Karton;

public interface KartonRepository extends JpaRepository<Karton, Long>{

	Karton findByPacijentId(Long id);

}
