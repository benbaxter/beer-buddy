package com.beerbuddy.web.controller.ui;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beerbuddy.core.model.User;
import com.beerbuddy.core.security.BeerBuddySecurityContextHelper;

@Controller
public class HomePageController {

	@Description("Takes you to the home page. ")
	@RequestMapping({"", "/", "/index.html", "/home.html"})
	public String home(HttpSession session, Model model) {
		User user = BeerBuddySecurityContextHelper.getUser();
		if( user != null ) {
			model.addAttribute("user", user);
		}
		
		return "index";
	}
}
