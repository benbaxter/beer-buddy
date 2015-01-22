package com.beerbuddy.core.function;

import static com.beerbuddy.core.utils.MapUtils.getBoolean;
import static com.beerbuddy.core.utils.MapUtils.getString;
import static com.beerbuddy.core.utils.MapUtils.getInteger;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beerbuddy.core.model.Beer;

public interface BeerStoreMapperFunction extends Function<Map<String, Object>, Beer> {

	public final static Logger log = LoggerFactory.getLogger(BeerStoreMapperFunction.class);
	
	/**
	 * converts json response to a {@link Beer} 
	 * 
	 * Example json:
	 * 	  {
	 * 		"name" : "Rickards Cardigan",
		  	"beer_id" : 120,
		  	"image_url" : "http://www.thebeerstore.ca/sites/default/files/styles/brand_hero/public/brand/hero/Rickards%20Cardigan%20Hero.jpg?itok=IRGzYwK0",
		  	"category" : "Craft",
		  	"abv" : "5.5",
		  	"type" : "Lager",
		  	"brewer" : "Molson",
		  	"country" : "Canada",
		  	"on_sale" : false
		  }
	 */
	@Override
	public default Beer apply(Map<String, Object> json) {
		Beer beer = new Beer();
		beer.setName(getString(json, "name"));
		beer.setBeerStoreId(getInteger(json, "beer_id"));
		beer.setAbv(getString(json, "abv"));
		beer.setImageUrl(getString(json, "image_url"));
		beer.setCategory(getString(json, "category"));
		beer.setType(getString(json, "type"));
		beer.setBrewer(getString(json, "brewer"));
		beer.setCountry(getString(json, "country"));
		beer.setOnSale(getBoolean(json, "on_sale"));
		return beer;
	}
		
}