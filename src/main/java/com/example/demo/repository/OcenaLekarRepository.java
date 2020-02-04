package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.OcenaLekar;

public interface OcenaLekarRepository extends JpaRepository< OcenaLekar, Long>{

}
