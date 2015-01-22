package com.beerbuddy.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="beer")
public class Beer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    protected String name;
    
    @Column(name="beer_store_id")
    protected int beerStoreId;

    @Column(name="image_url")
    protected String imageUrl;
    
    protected String category;
    protected String abv;
    protected String type;
    protected String brewer;
    protected String country;

    @Column(name="on_sale")
    protected boolean onSale;
    
    
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
	public long getBeerStoreId() {
		return beerStoreId;
	}
	public void setBeerStoreId(int beerStoreId) {
		this.beerStoreId = beerStoreId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAbv() {
		return abv;
	}
	public void setAbv(String abv) {
		this.abv = abv;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrewer() {
		return brewer;
	}
	public void setBrewer(String brewer) {
		this.brewer = brewer;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean isOnSale() {
		return onSale;
	}
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	
}