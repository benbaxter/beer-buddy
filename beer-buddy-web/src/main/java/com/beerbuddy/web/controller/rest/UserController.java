package com.beerbuddy.web.controller.rest;

import java.util.Map;
import java.util.Set;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.model.UserBeerRank;
import com.beerbuddy.core.model.UserProfile;
import com.beerbuddy.core.security.BeerBuddySecurityContextHelper;
import com.beerbuddy.core.service.UserService;
import com.beerbuddy.core.service.impl.UserAlreadyExistsException;
import com.beerbuddy.web.controller.ui.model.BeerDTO;
import com.beerbuddy.web.controller.ui.model.NewUserRequest;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/users")
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

	@Description("Return a list of all the user's favorite beers")
	@RequestMapping(value="/beers", method=RequestMethod.GET)
	public ResponseEntity<?> getUsersBeers() {
		User user = BeerBuddySecurityContextHelper.getUser();
		if( user != null ) {
			Set<UserBeerRank> userPage = userService.findBeerRankingsForUser(user.getProfileId());
			return new ResponseEntity<>(userPage, HttpStatus.OK);
		}
		return new ResponseEntity<>("You must be logged in to see your favorite beers", 
				HttpStatus.FORBIDDEN);
	}
	
	@Description("Add a beer to the end of the ranking list")
	@RequestMapping(value="/beers", method=RequestMethod.POST)
	public ResponseEntity<?> addBeerToRank(@RequestBody BeerDTO dto) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		Object o = auth.getDetails();
		if( o instanceof User ) {
			User user = (User) o;
			
			userService.addBeerToUserRankings(user, dto.getId());
			return new ResponseEntity<>(ImmutableMap.of("message", "Beer added!"), HttpStatus.OK);
		}
		return new ResponseEntity<>("You must be logged in to see your favorite beers", 
				HttpStatus.FORBIDDEN);
	}
}