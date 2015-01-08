package com.beerbuddy.web.controller.ui.model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.beerbuddy.core.model.Book;
import com.beerbuddy.core.model.BookState;

public interface BookMapper extends Function<Book, BookDTO> {

	@Override
	public default BookDTO apply(Book b) {
		BookDTO dto = new BookDTO();
		dto.setAuthor(b.getAuthor());
		dto.setDeweyDecimal(b.getDeweyDecimal());
		dto.setId(b.getId());
		dto.setName(b.getName());
		dto.setState(b.getState().getDisplay());
		return dto;
	}
	
	public default List<BookDTO> map(List<Book> books) {
		return books.stream()
			.map(this)
			.sorted((a, b) -> a.getName().compareTo(b.getName()))
			.collect(Collectors.toList());		
	}
	
	public default Book mapTheOtherWay(BookDTO dto) {
		Book b = new Book();
		b.setAuthor(dto.getAuthor());
		b.setDeweyDecimal(dto.getDeweyDecimal());
		b.setId(dto.getId());
		b.setName(dto.getName());
		b.setState(BookState.fromDisplay(dto.getState()));
		return b;
	}
	
}