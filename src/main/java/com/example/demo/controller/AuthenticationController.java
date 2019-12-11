package com.example.demo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dto.JwtAuthenticationRequest;
import com.example.demo.dto.PacijentDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserTokenState;
import com.example.demo.model.Authority;
import com.example.demo.model.Pacijent;
import com.example.demo.model.User;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.PacijentService;
import com.example.demo.service.UserService;

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

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(),
						authenticationRequest.getSifra()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) authentication.getPrincipal();
		Pacijent pacijent = pacijentService.findOne(user.getId());

		if (pacijent == null) {
			Collection<?> roles = user.getAuthorities();
			String jwt = tokenUtils.generateToken(user, (Authority) roles.iterator().next());
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
		} else if (user.isEnabled()) {
			Collection<?> roles = user.getAuthorities();
			String jwt = tokenUtils.generateToken(user, (Authority) roles.iterator().next());
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
		}
		return (ResponseEntity<?>) ResponseEntity.notFound();
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody RegisterDTO pacijent) throws Exception {

		User existUser = this.userService.findOne(pacijent.getMail());
		if (existUser != null) {
			throw new Exception("Alredy exist");
		}

		Pacijent neaktivanPacijent = this.pacijentService.save(pacijent);
		PacijentDTO pacijentDTO = new PacijentDTO(neaktivanPacijent);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<PacijentDTO>(pacijentDTO, HttpStatus.CREATED);
	}


	@RequestMapping(value = "/activate/{code}", method = RequestMethod.POST)
	public ResponseEntity<?> aktivirajPacijenta(@PathVariable String code) throws Exception {

		Long id = Long.parseLong(code);
		Pacijent exisPacijent = this.pacijentService.findOne(id);
		if (exisPacijent == null) {
			throw new Exception("Alredy exist");
		}

		this.pacijentService.aktivirajPacijenta(exisPacijent);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
