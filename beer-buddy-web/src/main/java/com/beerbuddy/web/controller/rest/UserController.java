package com.beerbuddy.web.controller.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.service.UserService;
import com.beerbuddy.core.service.impl.UserAlreadyExistsException;
import com.beerbuddy.web.controller.ui.model.NewUserRequest;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Description("This will create a user. If a username already exists, then a bad request response will be thrown")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody NewUserRequest request) {
		try {
			User user = userService.createUser(request.getUsername(), request.getPassword());
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch(UserAlreadyExistsException e) {
			Map<String, String> error = ImmutableMap.
				of("error", "username_exits", 
					"message", "Username is invalid: " + request.getUsername());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		//TODO: add an exception for bad passwords...
	}

}