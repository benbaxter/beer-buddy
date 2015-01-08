package com.beerbuddy.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.beerbuddy.core.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

	@Query("select b from Book b "
    		+ " where upper(b.name) like upper(concat ('%', :criteria, '%')) "
    		+ " or upper(b.name) like upper(concat ('%', :criteria, '%')) "
    		+ " or upper(b.deweyDecimal) like upper(concat ('%', :criteria, '%')) "
    		+ " or upper(b.author) like upper(concat ('%', :criteria, '%')) ")
	public List<Book> findContainingString(@Param("criteria") String criteria);
}
