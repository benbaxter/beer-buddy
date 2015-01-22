package com.beerbuddy.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="beer_sync")
public class BeerSync {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date timestamp;
    
    @Enumerated(EnumType.STRING)
    protected SyncStatus status;

    public BeerSync() {
    	timestamp = new Date();
	}
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public SyncStatus getStatus() {
		return status;
	}
	public void setStatus(SyncStatus status) {
		this.status = status;
	}
}