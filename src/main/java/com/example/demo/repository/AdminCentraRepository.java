package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AdminCentra;

public interface AdminCentraRepository extends JpaRepository<AdminCentra, Long> {
	AdminCentra findByMail( String mail );
}
