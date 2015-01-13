package com.beerbuddy.web.controller.rest;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.beerbuddy.web.controller.ui.model.NewUserRequest;
import com.google.common.collect.ImmutableMap;

@RestController
public class LoginController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("beerBuddyAuthenticationManager")
	AuthenticationManager beerBuddyAuthenticationManager;
	
	@Description("This will simulate logging in a user...")
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody NewUserRequest request) {
		//clear context to be safe
		SecurityContextHolder.clearContext();
		
		Authentication auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		try {
			auth = beerBuddyAuthenticationManager.authenticate(auth);
			return new ResponseEntity<>(auth.getDetails(), HttpStatus.OK);
		} catch(InsufficientAuthenticationException e) {
			log.error("Something wrong with the authentication...", e);
			Map<String, String> error = ImmutableMap.
					of("error", "we_screwed_something_up_oopsy", 
						"message", "Something bad happened on our side, sorry. Please try again later.");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		} catch(BadCredentialsException e) {
			log.error("Bad password...", e);
			Map<String, String> error = ImmutableMap.
					of("error", "invalid_creds", 
						"message", "Invalid Username/Password");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		} catch(UsernameNotFoundException e) {
			log.error("Bad username...", e);
			Map<String, String> error = ImmutableMap.
					of("error", "invalid_creds", 
						"message", "Invalid Username/Password");
			return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@Description("This will simulate logging out a user...")
	@RequestMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		//clear context to be safe
		SecurityContextHolder.clearContext();
		session.invalidate();
		Map<String, String> success = ImmutableMap.
				of("success", "true");
		return new ResponseEntity<>(success, HttpStatus.OK);
	}
}