package com.beerbuddy.core.config;


import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.beerbuddy.core.model.Beer;
import com.beerbuddy.core.repository.BeerRepository;

@EnableJpaRepositories(basePackageClasses={BeerRepository.class})
@EntityScan(basePackageClasses=Beer.class)
public class RepositoryConfig {

}
