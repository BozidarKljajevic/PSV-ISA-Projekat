package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.IzvestajOPregledu;
public interface IzvestajOPregleduRepository extends JpaRepository<IzvestajOPregledu, Long>{

	List<IzvestajOPregledu> findByKartonId(Long id);

}
