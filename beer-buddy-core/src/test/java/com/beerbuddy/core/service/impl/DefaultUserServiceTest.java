package com.beerbuddy.core.service.impl;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.beerbuddy.core.model.DefaultUser;
import com.beerbuddy.core.model.UnsecureUserWrapper;
import com.beerbuddy.core.model.User;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.core.repository.UserBeerRankRepository;
import com.beerbuddy.core.repository.UserProfileRepository;
import com.beerbuddy.core.repository.UserRepository;

public class DefaultUserServiceTest {

	@Mock
	protected UserRepository repository;
	
	@Mock
	protected UserProfileRepository profileRepository;
	
	@Mock
	protected UserBeerRankRepository rankRepository;
	
	@Mock
	protected BeerRepository beerRepository;
	
	protected DefaultUserService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		service = new DefaultUserService(repository, profileRepository, rankRepository, beerRepository);
	}
	
	@Test
	public void createUser_samePasswordEncryptsUniquely() throws Exception {
		
		String username = "123";
		String password = "abc";
		
		User firstUser = service.createUser(username, password);
		User secondUser = service.createUser(username, password);
		
		UnsecureUserWrapper unsecureFirst = new UnsecureUserWrapper(firstUser);
		UnsecureUserWrapper unsecureSecond = new UnsecureUserWrapper(secondUser);
		assertThat(unsecureFirst.getUser(), is(instanceOf(DefaultUser.class)));
		assertThat(unsecureSecond.getUser(), is(instanceOf(DefaultUser.class)));
		
		assertThat(((DefaultUser) unsecureFirst.getUser()).getPassword(), 
				is(not(equalTo(((DefaultUser) unsecureSecond.getUser()).getPassword()))));
	}
	
}