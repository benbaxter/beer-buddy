package com.beerbuddy.core.service.impl;


public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String username){
		super("User already exists: " + username);
	}
	
	public UserAlreadyExistsException(String username, Throwable throwable) {
		super("User already exists: " + username, throwable);
	}
	
}