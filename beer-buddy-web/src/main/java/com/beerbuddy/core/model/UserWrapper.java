package com.beerbuddy.core.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserWrapper implements User {

	protected User user;

	public UserWrapper(User user) {
		this.user = user;
	}

    @JsonProperty("lastLoginInMillis")
    public long getLastLoginInMillis() {
    	if( user.getLastLogin() != null ) {
    		return user.getLastLogin().getTime();
    	}
    	return -1;
	}
    
    @Override
    public String getUsername() {
    	return user.getUsername();
    }
    
    @Override
    public Date getLastLogin() {
    	return user.getLastLogin();
    }
 
    /**
     * This is set to protected so that only those in this package have access 
     * to the real user object. It makes testing easier; however it could lead to 
     * a leak in security by exposing the real user object...
     * 
     * This seems like a hack to get a test case to work, but we need a back door
     * @return
     */
    protected User getUser() {
    	return user;
    }
}