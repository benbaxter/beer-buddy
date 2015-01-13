package com.beerbuddy.core.service.impl;


import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.beerbuddy.core.model.Beer;
import com.beerbuddy.core.repository.BeerRepository;
import com.beerbuddy.core.service.BeerStoreSyncService;
import com.google.common.collect.ImmutableMap;

public class DefaultBeerStoreSyncServiceTest {

	BeerStoreSyncService service;
	
	@Mock
	RestTemplate restTemplate;
	
	@Mock
	BeerRepository beerRepository;

	@Mock
	ResponseEntity<Object> responseEntity;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		//exmaple of not using annotations
		@SuppressWarnings({ "unchecked", "unused" })
		List<String> sampleMock = Mockito.mock(List.class);
		
		service = new DefaultBeerStoreSyncService(restTemplate, beerRepository);

		//Setup default behavior for restTemplate and response entity
		when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);
	}
	
	
	@Test
	public void sync() {

		List<Map<String, Object>> body = new ArrayList<>();
		body.add(ImmutableMap.<String, Object> builder()
				.put("name", "Beer One")
				.put("beer_id", 1)
				.put("image_url", "image")
				.put("category", "Craft")
				.put("abv", "5.5")
				.put("type", "Lager")
				.put("brewer", "Molson")
				.put("country", "Canada")
				.put("on_sale", false)
				.build());
		body.add(ImmutableMap.<String, Object> builder()
				.put("name", "Beer Two")
				.put("beer_id", 2)
				.put("image_url", "image")
				.put("category", "Craft")
				.put("abv", "5.5")
				.put("type", "Lager")
				.put("brewer", "Molson")
				.put("country", "Canada")
				.put("on_sale", false)
				.build());
		
		when(responseEntity.getBody()).thenReturn(body);
		
		boolean success = service.sync();
		
		assertThat(success, is(equalTo(true)));
		
		verify(beerRepository, times(2)).save(any(Beer.class));
		verify(beerRepository).save(argThat(new BeerNameMatcher(() -> "Beer One")));
		verify(beerRepository).save(argThat(new BeerNameMatcher(() -> "Beer Two")));
	}

	@Test
	public void sync_badResponseBackFromRestApi() {

		when(responseEntity.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);
		
		boolean success = service.sync();
		
		assertThat(success, is(equalTo(true)));
	}

	@FunctionalInterface
	public interface BeerNameMatcherFunction {
		public String getName();
	}
	public class BeerNameMatcher extends BaseMatcher<Beer> {

		BeerNameMatcherFunction nameFunction;
		
		public BeerNameMatcher(BeerNameMatcherFunction nameFunction) {
			this.nameFunction = nameFunction;
		}
		
		@Override
		public boolean matches(Object item) {
			if( ! (item instanceof Beer) ) {
				return false;
			}
			
			Beer beer = (Beer) item;
			return beer.getName().equals(nameFunction.getName());
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("Matches " + nameFunction.getName());
		}
	}
}
