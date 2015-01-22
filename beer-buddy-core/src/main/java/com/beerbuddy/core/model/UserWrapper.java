package com.beerbuddy.core.model;

import java.util.Date;

public class UserWrapper implements User {

	protected User user;

	public UserWrapper(User user) {
		//avoid the potential endless wrapping
		if( user instanceof UserWrapper ) {
			this.user = ((UserWrapper) user).getUser();
		} else {
			this.user = user;
		}
	}

	//todo: move to dto
    //@JsonProperty("lastLoginInMillis")
    public long getLastLoginInMillis() {
    	if( user.getLastLogin() != null ) {
    		return user.getLastLogin().getTime();
    	}
    	return -1;
	}
    
    @Override
    public void setProfile(UserProfile profile) {
    	user.setProfile(profile);
    }
    
    @Override
    public String getUsername() {
    	return user.getUsername();
    }
    
    @Override
    public Date getLastLogin() {
    	return user.getLastLogin();
    }
 
    @Override
    public String getEmail() {
    	return user.getEmail();
    }
    
    @Override
    public String getName() {
    	return user.getName();
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