package com.beerbuddy.core.service.impl;

import java.util.Date;

import com.beerbuddy.core.model.BeerSync;
import com.beerbuddy.core.model.SyncStatus;
import com.beerbuddy.core.repository.BeerSyncRepository;
import com.beerbuddy.core.service.BeerStoreSyncService;

public class BeerStoreSyncServiceMonitor implements BeerStoreSyncService{

	protected BeerSyncRepository beerSyncRepository;
	
	protected BeerStoreSyncService realSyncService;
	
	public BeerStoreSyncServiceMonitor( 
			BeerStoreSyncService realSyncService,
			BeerSyncRepository beerSyncRepository) {
		this.realSyncService = realSyncService;
		this.beerSyncRepository = beerSyncRepository;
	}
	
	@Override
	public boolean sync() {
		BeerSync syncMonitor = new BeerSync();
		syncMonitor.setStatus(SyncStatus.STARTED);
		beerSyncRepository.save(syncMonitor);
		
		try {
			boolean success = realSyncService.sync();
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
		
}