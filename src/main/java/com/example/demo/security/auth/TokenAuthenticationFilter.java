package com.example.demo.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.security.TokenUtils;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	private TokenUtils tokenUtils;
	
	private UserDetailsService userDetailsService;
	
	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String mail;
		String authToken = tokenUtils.getToken(request);

		if (authToken != null) {
			mail = tokenUtils.getMailFromToken(authToken);
			
			if (mail != null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(mail);

				if (tokenUtils.validateToken(authToken, userDetails)) {
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		
		chain.doFilter(request, response);
	}

}
