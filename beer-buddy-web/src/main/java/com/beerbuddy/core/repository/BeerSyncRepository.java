package com.beerbuddy.core.repository;

import org.springframework.data.repository.CrudRepository;

import com.beerbuddy.core.model.BeerSync;

public interface BeerSyncRepository extends CrudRepository<BeerSync, Long> {
}
