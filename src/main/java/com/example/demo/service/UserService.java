package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findOne(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User findOne(String mail) {
		return userRepository.findByMail(mail);
	}
}
