package com.beerbuddy.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beerbuddy.core.model.DefaultUser;

public interface UserRepository extends JpaRepository<DefaultUser, Long> {
	
	public DefaultUser findByUsername(String username);
	
	//@Query("select u from User u where u.username = :username and u.password = password")
	public DefaultUser findByUsernameAndPassword(String username, String password);
	
	@Query("select u.salt from DefaultUser u where u.username = :username ")
	public String findSaltForUsername(@Param("username") String username);
}
