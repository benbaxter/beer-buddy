package com.beerbuddy.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    protected String name;
    
    protected String email;
    
    @OneToOne
    @JoinColumn(name="user_id")
    protected DefaultUser user;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setUser(User user) {
		if( user instanceof DefaultUser ) {
			this.user = (DefaultUser) user;
		} else if ( user instanceof UserWrapper ){
			while(! (user instanceof DefaultUser) && user != null ) {
				user = ((UserWrapper) user).getUser();
			}
			if( user != null ) {
				this.user = (DefaultUser) user;
			}
		}
	}
}