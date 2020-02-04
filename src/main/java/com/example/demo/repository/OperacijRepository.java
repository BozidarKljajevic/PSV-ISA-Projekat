package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Operacija;


public interface OperacijRepository extends JpaRepository<Operacija, Long>{

	List<Operacija> findByIdPacijenta(Long id);

}
