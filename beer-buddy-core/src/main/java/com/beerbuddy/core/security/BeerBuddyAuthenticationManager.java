package com.beerbuddy.core.security;

import java.util.Collections;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.beerbuddy.core.model.DefaultUser;
import com.beerbuddy.core.model.UserWrapper;
import com.beerbuddy.core.repository.UserRepository;
import com.beerbuddy.core.service.CrytpoFunctions;

public class BeerBuddyAuthenticationManager implements AuthenticationManager, CrytpoFunctions {

	protected UserRepository userRepository;
	
	public BeerBuddyAuthenticationManager(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		if( authentication instanceof UsernamePasswordAuthenticationToken ) {
		
			String username = authentication.getName();
			DefaultUser guess = userRepository.findByUsername(username);
			if( guess == null ) {
				throw new UsernameNotFoundException("Could not find user with username: " + username);
			}
			String salt = guess.getSalt();
			String password = (String) authentication.getCredentials();
			
			password = encryptPassword(password, salt);
			
			DefaultUser user = userRepository.findByUsernameAndPassword(username, password);
			if( user == null ) {
				throw new BadCredentialsException("Cannot find user given username/password");
			}
			//update last login
			user.setLastLogin(new Date());
			userRepository.save(user);
			
			//reset authentication with the authenticated flag to true
			//TODO: figure out which granted authorities we want to use... 
			//TODO: define roles/authorization access
			authentication = new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
			((UsernamePasswordAuthenticationToken) authentication).setDetails(new UserWrapper(user));
			
			//store in the security context
			SecurityContext context = new SecurityContextImpl();
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
			
		} else {
			throw new InsufficientAuthenticationException("Authentication must be of type UsernamePasswordAuthenticationToken");
		}
		
		return authentication;
	}
}