package com.beerbuddy.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.beerbuddy.core.function.BeerStoreMapperFunction;
import com.beerbuddy.core.model.Beer;
import com.beerbuddy.core.model.BeerSync;
import com.beerbuddy.core.model.SyncStatus;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.core.repository.BeerSyncRepository;
import com.beerbuddy.core.service.BeerStoreSyncService;

public class BeerStoreSyncServiceImpl implements BeerStoreSyncService, BeerStoreMapperFunction {

	
	private final static String API = "http://ontariobeerapi.ca:80/beers/";
	
	protected RestTemplate restTemplate;

	protected BeerRepository beerRepository;

	protected BeerSyncRepository beerSyncRepository;
	
	public BeerStoreSyncServiceImpl(RestTemplate restTemplate, 
			BeerRepository beerRepository,
			BeerSyncRepository beerSyncRepository) {
		this.restTemplate = restTemplate;
		this.beerRepository = beerRepository;
		this.beerSyncRepository = beerSyncRepository;
	}
	
	@Override
	public boolean sync() {
		BeerSync syncMonitor = new BeerSync();
		syncMonitor.setStatus(SyncStatus.STARTED);
		beerSyncRepository.save(syncMonitor);
		
		try {
			boolean success = doSync();
			syncMonitor.setTimestamp(new Date());
			syncMonitor.setStatus(SyncStatus.ENDED);
			return success;
		} catch(Exception e) {
			syncMonitor.setTimestamp(new Date());
			syncMonitor.setStatus(SyncStatus.ERRORED);
		} finally {
			syncMonitor.setTimestamp(new Date());
			beerSyncRepository.save(syncMonitor);
		}
		
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean doSync() {
		ResponseEntity<List> response = restTemplate.getForEntity(API, List.class);
		if( response.getStatusCode() == HttpStatus.OK ) {
			List<Map<String, Object>> json = response.getBody();
			log.trace("about to sync beers");
			List<Beer> beers = json.stream()
					.map(this)
					.collect(Collectors.toList());
			log.trace("beers have been mapped to model");
			
			beers.stream()
			//takes too long to run.. come back later and multi thread this...
//				.filter(b -> {
//					//if we cannot get a valid image for a beer, we do not want it in our app
//					try {
//						URL url = new URL(b.getImageUrl());
//						url.openStream();
//					} catch(Exception e) {
//						return false;
//					}
//					return true;
//				})
				//sort by name before saving them just for fun so 
				//that we do not have to make a fancy db query later
				//laziness +1
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.forEach(b -> beerRepository.save(b));
			
			log.trace("beers have been synces");
		}
		return true;
	}
		
}