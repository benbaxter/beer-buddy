package com.beerbuddy.web.controller.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserProfile;
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
			UserProfile profile = new UserProfile();
			profile.setEmail(request.getEmail());
			profile.setName(request.getName());
			user = userService.setUserProfile(user, profile);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch(UserAlreadyExistsException e) {
			Map<String, String> error = ImmutableMap.
				of("error", "username_exits", 
					"message", "Username is invalid: " + request.getUsername());
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		//TODO: add an exception for bad passwords...
	}
	
	@Description("This will return a pageable list of all the users")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getUsers(
		@RequestParam(defaultValue="0", required=false) int page,
		@RequestParam(defaultValue="10", required=false) int size) {
		Order order = new Order(Direction.ASC, "name");
		Sort sort = new Sort(order);
		Pageable pageable = new PageRequest(page, size, sort);
		Page<User> userPage = userService.findAll(pageable);
		return new ResponseEntity<>(userPage, HttpStatus.OK);
	}

}