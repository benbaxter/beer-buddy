package com.beerbuddy.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beerbuddy.core.model.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long> {
	
	@Query("select distinct (b.type) from Beer b order by b.type asc")
	public List<String> findTypes();
	
	//TODO: write the query and declare some params to use with it 
	//@Query("select from Beer b where b.type = ?1 order by b.name asc")
	public Page<Beer> findByType( String type, Pageable page);
	
	//TODO: write the query for search
	
	///@Query("select b from Beer where b.name like %:nameString%")
	public Page<Beer> findByNameContaining(String name, Pageable page);
	
	public Page<Beer> findByNameContainingAndType(String name, String type, Pageable page);
}
