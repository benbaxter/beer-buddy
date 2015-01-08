package com.beerbuddy.core.service;

import java.util.List;

import com.beerbuddy.core.model.Book;


public interface BookService {

	public List<Book> getBooks();
	
	public void save(Book book);
	
	public void delete(Long id);
	
	public Book findById(Book book);
	
	public List<Book> findBookContextually(String criteria);
}
