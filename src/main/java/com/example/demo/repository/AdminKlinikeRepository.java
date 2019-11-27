package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AdminKlinike;
import com.example.demo.model.MedicinskoOsoblje;


public interface AdminKlinikeRepository extends JpaRepository<AdminKlinike, Long> {
	AdminKlinike findByMail( String mail );
}
