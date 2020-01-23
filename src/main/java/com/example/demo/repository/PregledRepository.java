package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Pregled;


public interface PregledRepository extends JpaRepository<Pregled, Long>{

	List<Pregled> findByLekarId(Long id);

}
