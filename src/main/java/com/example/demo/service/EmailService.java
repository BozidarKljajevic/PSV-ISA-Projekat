package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	
	@Autowired
	private Environment env;

	@Async
	public void sendNotificaitionAsync(User user, String poruka) throws MailException, InterruptedException {


		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Klinički centar");
		mail.setText(poruka);
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}


}
