package com.beerbuddy.web.controller.ui.model;


public class BeerDTO {

    private long id;

    protected String name;
    
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
}
