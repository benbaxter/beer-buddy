package com.beerbuddy.web.listener;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.beerbuddy.core.service.BeerStoreSyncService;

public class BeerStoreSyncListener {

	private final Logger log = LoggerFactory.getLogger(getClass());

	Environment environment;
	
	BeerStoreSyncService syncService;

	@Autowired
	public BeerStoreSyncListener(Environment environment, BeerStoreSyncService syncService) {
		this.environment = environment;
		this.syncService = syncService;
	}
	
	@PostConstruct
	public void runSync() {
		boolean syncAtStartup = Boolean.valueOf(environment.getProperty("sync-at-startup", "false"));
		log.debug("syncing at starting? " + syncAtStartup);
		if( syncAtStartup ) {
			boolean success = syncService.sync();
			if( ! success ) {
				log.error("Something went wrong with syncing... should we notify someone?");
			}
		}
	}
}