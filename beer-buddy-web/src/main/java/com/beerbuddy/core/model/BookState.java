package com.beerbuddy.core.model;

import java.util.Arrays;
import java.util.Optional;

public enum BookState {

	  AVAILABLE("Available")
	, CHECKEDOUT("Checked out")
	, NEW("New")
	, LOST("Lost")
	;
	
	private String display;
	
	private BookState(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public static BookState fromDisplay(final String display) {
		Optional<BookState> optional =  Arrays.asList(values()).stream()
			.filter(state -> state.getDisplay().equalsIgnoreCase(display))
			.findFirst();
		
		if( optional.isPresent() ) {
			return optional.get();
		}
		throw new IllegalArgumentException("Cannot find TagState from display: " + display);
	}
}
