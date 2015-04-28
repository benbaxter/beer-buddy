package com.beerbuddy.core.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserBeerRank;
import com.beerbuddy.core.model.UserProfile;
import com.beerbuddy.core.service.impl.UserAlreadyExistsException;


public interface UserService {

	public User createUser(String username, String password) throws UserAlreadyExistsException;
	
	public User setUserProfile(User user, UserProfile profile);
	
	public Page<User> findAll(Pageable pageable);
	
	public Set<UserBeerRank> findBeerRankingsForUser(Long id);
	
	public Page<UserBeerRank> findBeerRankingsUser(String username, Pageable page);
	
	public void addBeerToUserRankings(User user, Long beerId);


}
