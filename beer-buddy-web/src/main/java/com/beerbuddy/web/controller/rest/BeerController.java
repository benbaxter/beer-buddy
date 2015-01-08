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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beerbuddy.core.model.Beer;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.web.controller.ui.model.BookMapper;

@RestController
@RequestMapping("/beers")
public class BeerController implements BookMapper {

	@Autowired
	protected BeerRepository beerRepository;
	
	@Description("Returns a list of all of the beers from ontario beer api")
	@RequestMapping(value={"", "/" }, method=GET)
	public Page<Beer> getBeers(@RequestParam(defaultValue="0", required=false) int page,
			@RequestParam(defaultValue="10", required=false) int size) {
		Order order = new Order(Direction.ASC, "name");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(page, size, sort);
		return beerRepository.findAll(pageable);
	}
	
	@RequestMapping(value={"/types" }, method=GET)
	public List<String> getTypes() {
		return beerRepository.findTypes();
	}
	
}