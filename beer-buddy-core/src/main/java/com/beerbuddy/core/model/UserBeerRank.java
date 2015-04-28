package com.beerbuddy.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user_beer_rank")
public class UserBeerRank {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_profile_id")
    protected UserProfile user;

    @ManyToOne
    @JoinColumn(name="beer_id")
    protected Beer beer;
    
    protected int rank;
    
    public long getId() {
		return id;
	}
    public void setId(long id) {
		this.id = id;
	}
    public UserProfile getUser() {
		return user;
	}
    public void setUser(UserProfile user) {
		this.user = user;
	}
    public int getRank() {
    	return rank;
    }
    public void setRank(int rank) {
		this.rank = rank;
	}
    public Beer getBeer() {
		return beer;
	}
    public void setBeer(Beer beer) {
		this.beer = beer;
	}
     
}