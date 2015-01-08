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

import com.beerbuddy.core.function.BeerStoreMapperFunction;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.core.repository.BeerSyncRepository;
import com.beerbuddy.core.repository.BookRepository;
import com.beerbuddy.core.service.BeerStoreSyncService;
import com.beerbuddy.core.service.BookService;
import com.beerbuddy.core.service.impl.BeerStoreSyncServiceImpl;
import com.beerbuddy.core.service.impl.BookRepositoryService;
import com.beerbuddy.web.listener.BeerStoreSyncListener;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import(RepositoryConfig.class)
public class AppConfig {

	@Autowired
	@Qualifier("bookRepository")
	private BookRepository bookRepository;
	
	@Autowired
	@Qualifier("beerRepository")
	private BeerRepository beerRepository;
	
	@Autowired
	@Qualifier("beerSyncRepository")
	private BeerSyncRepository beerSyncRepository;
	
	@Autowired
	Environment environment;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@Autowired
	public BeerStoreSyncListener beerStoreSyncListener(Environment environment,
			@Qualifier("beerStoreSyncService") BeerStoreSyncService syncService) {
		return new BeerStoreSyncListener(environment, syncService);
	}
	
	@Bean(name="beerStoreSyncService")
	@Autowired
	public BeerStoreSyncService beerStoreSyncService(RestTemplate restTemplate) { 
		return new BeerStoreSyncServiceImpl(restTemplate, beerRepository, beerSyncRepository);
	}
	
	@Bean(name="bookService")
	public BookService bookService() {
		return new BookRepositoryService(bookRepository);
	}
	
}