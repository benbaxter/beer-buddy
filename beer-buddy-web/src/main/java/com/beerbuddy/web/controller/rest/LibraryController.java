package com.beerbuddy.web.controller.rest;


import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beerbuddy.core.service.BookService;
import com.beerbuddy.web.controller.ui.model.BookDTO;
import com.beerbuddy.web.controller.ui.model.BookMapper;

@RestController
@RequestMapping("/library")
public class LibraryController implements BookMapper {

	@Autowired
	BookService service;
	
	@Description("Returns a list of all of the books")
	@RequestMapping(value={"", "/" }, method=GET)
	public List<BookDTO> getBooks() {
		return map(service.getBooks());
	}
	
	@Description("Returns a list of all of the books when searching by {criteria}")
	@RequestMapping(value="/{criteria}", method=GET)
	public List<BookDTO> find(@PathVariable String criteria) {
		return map(service.findBookContextually(criteria));
	}
	
	@Description("Adds the book, then returns a list of all of the books")
	@RequestMapping(value={"", "/" }, method=POST)
	public List<BookDTO> addBook(@RequestBody BookDTO dto) {
		service.save(mapTheOtherWay(dto));
		return getBooks();
	}
	
	@Description("Deletes the book, then returns a list of all of the books")
	@RequestMapping(value={"/{id}" }, method=DELETE)
	public List<BookDTO> deleteBook(@PathVariable Long id) {
		service.delete(id);
		return getBooks();
	}
	
}