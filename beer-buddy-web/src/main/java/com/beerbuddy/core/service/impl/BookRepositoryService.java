package com.beerbuddy.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.beerbuddy.core.model.Book;
import com.beerbuddy.core.repository.BookRepository;
import com.beerbuddy.core.service.BookService;
import com.google.common.collect.ImmutableList;

public class BookRepositoryService implements BookService {

	BookRepository bookRepository;
	
	@Autowired
	public BookRepositoryService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Book> getBooks() {
		return ImmutableList.<Book>
				builder()
				.addAll(bookRepository.findAll())
				.build();
	}
	
	@Override
	@Transactional
	public void save(Book book) {
		bookRepository.save(book);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		bookRepository.delete(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Book findById(Book book) {
		return bookRepository.findOne(book.getId());
	}
	
	@Override
	public List<Book> findBookContextually(String criteria) {
		return bookRepository.findContainingString(criteria);
	}
	
}