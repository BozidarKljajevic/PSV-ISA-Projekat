package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User findOne(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User findOne(String mail) {
		return userRepository.findByMail(mail);
	}

	public void izmeniSifru(User user, String sifra) {
		
		user.setSifra(passwordEncoder.encode(sifra));
		user.setPromenjenaSifra(true);
		
		this.userRepository.save(user);
	}

	public void aktiviran(User user) {
		User user1 = userRepository.findById(user.getId()).orElse(null);
		
		user1 = userRepository.getOne(user.getId());
		user1.setEnabled(true);
		userRepository.save(user1);
	}
	
	
}
