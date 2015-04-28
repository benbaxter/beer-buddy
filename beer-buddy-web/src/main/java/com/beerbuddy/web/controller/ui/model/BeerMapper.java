package com.beerbuddy.web.controller.ui.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.beerbuddy.core.model.Beer;

public interface BeerMapper extends Function<Beer, BeerDTO> {

	@Override
	public default BeerDTO apply(Beer b) {
		BeerDTO dto = new BeerDTO();
		dto.setAbv(b.getAbv());
		dto.setBrewer(b.getBrewer());
		dto.setCategory(b.getCategory());
		dto.setCountry(b.getCountry());
		dto.setId(b.getId());
		dto.setImageUrl(b.getImageUrl());
		dto.setName(b.getName());
		dto.setOnSale(b.isOnSale());
		dto.setType(b.getType());
		return dto;
	}
	
	public default List<BeerDTO> map(List<Beer> beers) {
		return beers.stream()
			.map(this)
			.sorted((a, b) -> a.getName().compareTo(b.getName()))
			.collect(Collectors.toList());		
	}
	
	public default Beer mapTheOtherWay(BeerDTO dto) {
		Beer b = new Beer();
		b.setAbv(dto.getAbv());
		b.setBrewer(dto.getBrewer());
		b.setCategory(dto.getCategory());
		b.setCountry(dto.getCountry());
		b.setId(dto.getId());
		b.setImageUrl(dto.getImageUrl());
		b.setName(dto.getName());
		b.setOnSale(dto.isOnSale());
		b.setType(dto.getType());
		return b;
	}
	
}