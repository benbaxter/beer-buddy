package com.beerbuddy.web.controller.ui.model;


public class BeerDTO {

    private long id;

    protected String name;
    protected String author;
    protected String state;    
    protected String deweyDecimal;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDeweyDecimal() {
		return deweyDecimal;
	}
	public void setDeweyDecimal(String deweyDecimal) {
		this.deweyDecimal = deweyDecimal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
