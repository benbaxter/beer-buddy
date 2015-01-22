package com.beerbuddy.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.beerbuddy.core.config.CoreConfig;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.core.repository.BeerSyncRepository;
import com.beerbuddy.core.repository.UserProfileRepository;
import com.beerbuddy.core.repository.UserRepository;
import com.beerbuddy.core.service.BeerStoreSyncService;
import com.beerbuddy.core.service.UserService;
import com.beerbuddy.core.service.impl.BeerStoreSyncServiceMonitor;
import com.beerbuddy.core.service.impl.DefaultBeerStoreSyncService;
import com.beerbuddy.core.service.impl.DefaultUserService;
import com.beerbuddy.web.listener.BeerStoreSyncListener;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import(CoreConfig.class)
public class AppConfig {

	@Autowired
	@Qualifier("beerRepository")
	private BeerRepository beerRepository;
	
	@Autowired
	@Qualifier("beerSyncRepository")
	private BeerSyncRepository beerSyncRepository;
	
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	Environment environment;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@Autowired
	public BeerStoreSyncListener beerStoreSyncListener(Environment environment,
			@Qualifier("beerStoreSyncServiceMonitor") BeerStoreSyncService syncService) {
		return new BeerStoreSyncListener(environment, syncService);
	}
	
	@Bean(name="beerStoreSyncService")
	@Autowired
	public BeerStoreSyncService beerStoreSyncService(RestTemplate restTemplate) { 
		return new DefaultBeerStoreSyncService(restTemplate, beerRepository);
	}
	
	@Bean(name="beerStoreSyncServiceMonitor")
	@Autowired
	public BeerStoreSyncService beerStoreSyncServiceMonitor(
			@Qualifier("beerStoreSyncService") BeerStoreSyncService syncService) { 
		return new BeerStoreSyncServiceMonitor(syncService, beerSyncRepository);
	}
	
	@Bean
	public UserService userService() { 
		return new DefaultUserService(userRepository, userProfileRepository);
	}
}