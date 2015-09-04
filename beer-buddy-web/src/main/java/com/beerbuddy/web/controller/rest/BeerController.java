package com.beerbuddy.web.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beerbuddy.core.model.Beer;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.web.controller.ui.model.BeerMapper;

@RestController
@RequestMapping("/beers")
public class BeerController implements BeerMapper {

	@Autowired
	protected BeerRepository beerRepository;
	
	@Description("Returns a list of all of the beers")
	@RequestMapping(value={"", "/" }, method=GET)
	public Page<Beer> getBeers(@RequestParam(defaultValue="0", required=false) int page,
			@RequestParam(defaultValue="10", required=false) int size) {
		Order order = new Order(Direction.ASC, "name");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(page, size, sort);
		return beerRepository.findAll(pageable);
		//TODO: convert to a dto pager...
//		List<BeerDTO> dtos = beer.getContent().stream()
//					.map(this)
//					.collect(Collectors.toList());
//		return new PageImpl<BeerDTO>(dtos, beer.nextPageable(), beer.getTotalElements());
	}
	
	@Description("Returns a list of all of the beers for a particular type")
	@RequestMapping(value={"/types" }, method=GET)
	public List<String> getTypes() {
		return beerRepository.findTypes();
	}
	
	@Description("Returns a list of all of the beers from ontario beer api")
	@RequestMapping(value={"/types/{type}" }, method=GET)
	public Page<Beer> getBeersOfType(
			@PathVariable String type,
			@RequestParam(defaultValue="0", required=false) int page,
			@RequestParam(defaultValue="10", required=false) int size) {
		Order order = new Order(Direction.ASC, "name");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(page, size, sort);
		return beerRepository.findByType(type, pageable);
//		List<BeerDTO> dtos = beer.getContent().stream()
//				.map(this)
//				.collect(Collectors.toList());
//		return new PageImpl<BeerDTO>(dtos);
	}
	
	@Description("Returns a list of all of the beers from ontario beer api")
	@RequestMapping(value={"/search/{nameString}" }, method=GET)
	public Page<Beer> getBeersBySearch(
			@PathVariable String nameString,
			@RequestParam(defaultValue="0", required=false) int page,
			@RequestParam(defaultValue="10", required=false)int size){
		Order order = new Order(Direction.ASC, "name");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(page, size, sort);
		return beerRepository.findByNameContaining( nameString, pageable);
	}
	
	@Description("Returns a list of all of the beers from ontario beer api")
	@RequestMapping(value={"/search/{filter}/{nameString}" }, method=GET)
	public Page<Beer> getBeersBySearch(
			@PathVariable String nameString,
			@PathVariable String type,
			@RequestParam(defaultValue="0", required=false) int page,
			@RequestParam(defaultValue="10", required=false)int size){
		Order order = new Order(Direction.ASC, "name");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(page, size, sort);
		return beerRepository.findByNameContainingAndType( nameString, type, pageable);
	}
}