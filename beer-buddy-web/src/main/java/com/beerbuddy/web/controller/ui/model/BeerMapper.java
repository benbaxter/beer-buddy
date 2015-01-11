package com.beerbuddy.web.controller.ui.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.beerbuddy.core.model.Beer;

public interface BeerMapper extends Function<Beer, BeerDTO> {

	@Override
	public default BeerDTO apply(Beer b) {
		BeerDTO dto = new BeerDTO();
//		dto.setAuthor(b.getAuthor());
//		dto.setDeweyDecimal(b.getDeweyDecimal());
//		dto.setId(b.getId());
//		dto.setName(b.getName());
//		dto.setState(b.getState().getDisplay());
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
//		b.setAuthor(dto.getAuthor());
//		b.setDeweyDecimal(dto.getDeweyDecimal());
//		b.setId(dto.getId());
//		b.setName(dto.getName());
//		b.setState(BookState.fromDisplay(dto.getState()));
		return b;
	}
	
}