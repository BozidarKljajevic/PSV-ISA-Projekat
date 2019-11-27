package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MedicinskoOsoblje;
import com.example.demo.model.Pacijent;

public interface MedicinskoOsobljeRepository extends JpaRepository<MedicinskoOsoblje,Long> {
	MedicinskoOsoblje findByMail( String mail );
}
