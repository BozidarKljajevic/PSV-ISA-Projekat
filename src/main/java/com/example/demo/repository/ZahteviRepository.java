package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Zahtev;

public interface ZahteviRepository extends JpaRepository<Zahtev, Long>{

	List<Zahtev> findByLekarId(Long id);

}
