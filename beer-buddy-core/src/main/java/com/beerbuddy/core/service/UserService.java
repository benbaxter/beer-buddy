package com.beerbuddy.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserProfile;
import com.beerbuddy.core.service.impl.UserAlreadyExistsException;


public interface UserService {

	public User createUser(String username, String password) throws UserAlreadyExistsException;
	
	public User setUserProfile(User user, UserProfile profile);
	
	public Page<User> findAll(Pageable pageable);

}
