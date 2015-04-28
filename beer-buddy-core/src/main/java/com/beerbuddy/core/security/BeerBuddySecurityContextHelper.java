package com.beerbuddy.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.beerbuddy.core.model.User;

public class BeerBuddySecurityContextHelper {
	
	public static User getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		Object o = auth.getDetails();
		if( o instanceof User ) {
			User user = (User) o;
			return user;
		}
		return null;
	}
	
}