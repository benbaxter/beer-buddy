package com.beerbuddy.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.security.crypto.keygen.KeyGenerators;

import com.beerbuddy.core.model.DefaultUser;
import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserProfile;
import com.beerbuddy.core.model.UserWrapper;
import com.beerbuddy.core.repository.UserProfileRepository;
import com.beerbuddy.core.repository.UserRepository;
import com.beerbuddy.core.service.CrytpoFunctions;
import com.beerbuddy.core.service.UserService;

@Transactional
public class DefaultUserService implements UserService, CrytpoFunctions {

	protected UserRepository userRepository;
	protected UserProfileRepository userProfileRepository;
	
	public DefaultUserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
		this.userRepository = userRepository;
		this.userProfileRepository = userProfileRepository;
	}

	@Override
	public synchronized User createUser(String username, String password) throws UserAlreadyExistsException {
		DefaultUser userExists = userRepository.findByUsername(username);
		if( userExists != null ) {
			throw new UserAlreadyExistsException(username);
		}
		
		DefaultUser user = new DefaultUser();
		user.setUsername(username);
		
		String salt = KeyGenerators.string().generateKey();
		user.setSalt(salt);
		user.setPassword(encryptPassword(password, salt));
		
		userRepository.save(user);
		return new UserWrapper(user);
	}
	
	@Override
	public User setUserProfile(User user, UserProfile profile) {
		user.setProfile(profile);
		profile.setUser(user);
		userProfileRepository.save(profile);
		return new UserWrapper(user);
	}
}