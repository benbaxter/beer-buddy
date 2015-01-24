package com.beerbuddy.core.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.keygen.KeyGenerators;

import com.beerbuddy.core.model.Beer;
import com.beerbuddy.core.model.DefaultUser;
import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserBeerRank;
import com.beerbuddy.core.model.UserProfile;
import com.beerbuddy.core.model.UserWrapper;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.core.repository.UserBeerRankRepository;
import com.beerbuddy.core.repository.UserProfileRepository;
import com.beerbuddy.core.repository.UserRepository;
import com.beerbuddy.core.service.CrytpoFunctions;
import com.beerbuddy.core.service.UserService;

@Transactional
public class DefaultUserService implements UserService, CrytpoFunctions {

	protected UserRepository userRepository;
	protected UserProfileRepository userProfileRepository;
	protected UserBeerRankRepository rankRepository;
	protected BeerRepository beerRepository;
	
	public DefaultUserService(
			UserRepository userRepository, 
			UserProfileRepository userProfileRepository,
			UserBeerRankRepository rankRepository,
			BeerRepository beerRepository) {
		this.userRepository = userRepository;
		this.userProfileRepository = userProfileRepository;
		this.rankRepository = rankRepository;
		this.beerRepository = beerRepository;
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
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		Page<UserProfile> page = userProfileRepository.findAll(pageable);
		List<User> userContent = page.getContent().stream()
				.map(profile -> new UserWrapper(profile))
				.collect(Collectors.toList());
		return new PageImpl<>(userContent); 
	}
	
	@Override
	@Transactional
	public Set<UserBeerRank> findBeerRankingsForUser(Long id) {
		UserProfile profile = userProfileRepository.findOne(id);
		Set<UserBeerRank> ranking = profile.getBeerRankings();
		ranking.size();
		return ranking;
	}
	
	@Override
	public Page<UserBeerRank> findBeerRankingsUser(String username, Pageable page){
		return rankRepository.findByUser(username, page);
	}
	
	@Override
	@Transactional
	public void addBeerToUserRankings(User user, Long beerId) {
		UserProfile profile = userProfileRepository.findOne(user.getProfileId());
		Beer beer = beerRepository.findOne(beerId);
		
		UserBeerRank rank = new UserBeerRank();
		rank.setUser(profile);
		rank.setBeer(beer);
		rank.setRank(Integer.MAX_VALUE);
		rankRepository.save(rank);
		
		profile.getBeerRankings().add(rank);
	}
}