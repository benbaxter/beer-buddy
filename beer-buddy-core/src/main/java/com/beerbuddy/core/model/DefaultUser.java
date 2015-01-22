package com.beerbuddy.core.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="beer_buddy_user")
public class DefaultUser implements User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    protected String username;
    
    protected String password;
    
    protected String salt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_login")
    protected Date lastLogin;
    
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    protected UserProfile profile;
 
    @Override
    public String getEmail() {
    	return profile.getEmail();
    }
    
    @Override
    public String getName() {
    	return profile.getName();
    }
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public UserProfile getProfile() {
		return profile;
	}
	@Override
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}