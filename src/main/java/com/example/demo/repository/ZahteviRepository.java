package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Zahtev;

public interface ZahteviRepository extends JpaRepository<Zahtev, Long>{

}
