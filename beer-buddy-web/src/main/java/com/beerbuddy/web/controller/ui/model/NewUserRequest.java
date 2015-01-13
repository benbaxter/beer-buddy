package com.beerbuddy.web.controller.ui.model;


public class NewUserRequest {

    protected String username;
    protected String password;

    public String getPassword() {
		return password;
	}
    public void setPassword(String password) {
		this.password = password;
	}
    public String getUsername() {
		return username;
	}
    public void setUsername(String username) {
		this.username = username;
	}
}
