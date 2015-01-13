package com.beerbuddy.core.model;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserWrapper;

public class UnsecureUserWrapper extends UserWrapper {

	public UnsecureUserWrapper(User user) {
		super(user);
	}
	
	public User getUser() {
		if( user instanceof UserWrapper ) {
			return ((UserWrapper) user).getUser();
		}
		return user;
	}
}