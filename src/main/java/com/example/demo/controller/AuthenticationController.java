package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dto.JwtAuthenticationRequest;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.UserTokenState;
import com.example.demo.model.Authority;
import com.example.demo.model.Pacijent;
import com.example.demo.model.User;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.PacijentService;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private PacijentService pacijentService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),
						authenticationRequest.getSifra()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) authentication.getPrincipal();
		if (user.isEnabled()) {
			Collection<?> roles = user.getAuthorities();
			String jwt = tokenUtils.generateToken(user, (Authority)roles.iterator().next());
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
		}
		return (ResponseEntity<?>) ResponseEntity.notFound();
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody PacijentDTO pacijent) throws Exception {

		/*User existUser = this.pacijentService.findOne(pacijent.getMail());
		if (existUser != null) {
			throw new Exception("Alredy exist");
		}*/

		Pacijent neaktivanPacijent = this.pacijentService.save(pacijent);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<User>(neaktivanPacijent, HttpStatus.CREATED);
	}
}
