package com.beerbuddy.core.service;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserProfile;
import com.beerbuddy.core.service.impl.UserAlreadyExistsException;


public interface UserService {

	public User createUser(String username, String password) throws UserAlreadyExistsException;
	
	public User setUserProfile(User user, UserProfile profile);

}
