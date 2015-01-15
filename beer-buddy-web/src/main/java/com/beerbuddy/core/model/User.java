package com.beerbuddy.core.model;

import java.util.Date;

public interface User {

	public String getUsername();
	
	public Date getLastLogin();
	
	public String getName();
	
	public String getEmail();
	
	public void setProfile(UserProfile profile);
}