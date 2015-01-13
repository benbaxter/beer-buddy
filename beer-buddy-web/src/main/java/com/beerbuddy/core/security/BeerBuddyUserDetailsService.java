package com.beerbuddy.core.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.beerbuddy.core.repository.UserRepository;

public class BeerBuddyUserDetailsService implements UserDetailsService {

	protected UserRepository userRepository;
	
	public BeerBuddyUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return null;
	}
}