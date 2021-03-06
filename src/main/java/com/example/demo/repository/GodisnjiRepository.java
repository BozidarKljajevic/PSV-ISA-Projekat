package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Godisnji;
import com.example.demo.model.Zahtev;

public interface GodisnjiRepository extends JpaRepository<Godisnji, Long> {

	List<Godisnji> findByLekarId(Long id);

}
