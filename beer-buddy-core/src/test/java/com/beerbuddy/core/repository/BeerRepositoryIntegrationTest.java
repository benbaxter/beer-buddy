package com.beerbuddy.core.repository;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beerbuddy.core.model.Beer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestRepositoryConfig.class })
public class BeerRepositoryIntegrationTest {

	@Autowired
	BeerRepository repository;

	@Autowired
	private DataSource dataSource;

	private IDatabaseTester databaseTester;

	@Before
	public void setup() throws Exception {
		databaseTester = new DataSourceDatabaseTester(dataSource);

		IDataSet dataSet = null;

		dataSet = new FlatXmlDataFileLoader().load("/beer-repo-dataset.xml");

		databaseTester.setDataSet(dataSet);

		databaseTester.onSetup();
	}

	@After
	public void tearDown() throws Exception {
		databaseTester.onTearDown();
	}

	@Test
	public void findType() {
		List<String> types = repository.findTypes();
		// System.out.println(types.toString()); //without distinct, retrieved:
		// [Malt, Malt, Malt, Malt, Malt, Ale]
		// The XML document for the test beers had one ale and 5 malts. I
		// modified the XML document to contain 3 extra types
		assertThat(types.size(), is(equalTo(2)));
	}

	@Test
	public void findByType() {
		String type = "Malt";
		Pageable pageRequest = new PageRequest(0, 1);

		Page<Beer> page = repository.findByType(type, pageRequest);

		assertThat(page.getNumber(), is(equalTo(0)));
		assertThat(page.getNumberOfElements(), is(equalTo(1)));
		assertThat(page.getSize(), is(equalTo(1)));

		List<Beer> content = page.getContent();
		assertThat(content, is(not(nullValue())));
		assertThat(content.size(), is(equalTo(1)));
		Beer beer = content.get(0);
		assertThat(beer, is(not(nullValue())));
		assertThat(beer.getType(), is(equalTo(type)));

		// test all beers returned are of the correct type...
		pageRequest = new PageRequest(0, 100);

		page = repository.findByType(type, pageRequest);

		assertThat(page.getNumber(), is(equalTo(0)));
		assertThat(page.getNumberOfElements(), is(equalTo(5)));
		assertThat(page.getSize(), is(equalTo(100)));

		content = page.getContent();
		assertThat(content, is(not(nullValue())));
		assertThat(content.size(), is(equalTo(5)));
		page.forEach(b -> {
			assertThat(b, is(not(nullValue())));
			assertThat(b.getType(), is(equalTo(type)));
		});
	}

	@Test
	public void findByNameContaining() {
		String name = "Six";
		Pageable pageRequest = new PageRequest(0, 100);
		Page<Beer> page = repository.findByNameContaining(name, pageRequest);
		assertThat(page.getNumberOfElements(), is(equalTo(1)));
		List<Beer> content = page.getContent();
		assertThat(content, is(not(nullValue())));
		assertThat(content.size(), is(equalTo(1)));
		Beer beer = content.get(0);
		assertThat(beer, is(not(nullValue())));
		assertThat(beer.getName(), is(equalTo("My Sixth Beer")));

	}
	
	@Test
	public void findByNameAndFilterAle(){
		String name = "Beer";
		String type = "Ale";
		Pageable pageRequest = new PageRequest(0,100);
		Page<Beer> page = repository.findByNameContainingAndType(name, type, pageRequest);
		assertThat(page.getNumberOfElements(), is(equalTo(1)));
		List<Beer> content = page.getContent();
		assertThat(content, is(not(nullValue())));
		assertThat(content.size(), is(equalTo(1)));
		Beer beer = content.get(0);
		assertThat(beer, is(not(nullValue())));
		assertThat(beer.getName(), is(equalTo("My First Beer")));
	}
	
	@Test
	public void findByNameAndFilterMalt(){
		String name = "Beer";
		String type = "Malt";
		Pageable pageRequest = new PageRequest(0,100);
		Page<Beer> page = repository.findByNameContainingAndType(name, type, pageRequest);
		assertThat(page.getNumberOfElements(), is(equalTo(5)));
		List<Beer> content = page.getContent();
		assertThat(content, is(not(nullValue())));
		assertThat(content.size(), is(equalTo(5)));
		Beer beer = content.get(0);
		assertThat(beer, is(not(nullValue())));
		assertThat(beer.getName(), is(equalTo("My Sixth Beer")));
	}
	
	@Test
	public void findByType_badTestThatNeedsToBeFixed() {
		String type = "Ale";
		Pageable pageRequest = new PageRequest(0, 1);

		Page<Beer> page = repository.findByType(type, pageRequest);

		assertThat(page.getNumber(), is(equalTo(0)));
		assertThat(page.getNumberOfElements(), is(equalTo(1)));
		assertThat(page.getSize(), is(equalTo(1)));

		List<Beer> content = page.getContent();
		assertThat(content, is(not(nullValue())));
		assertThat(content.size(), is(equalTo(1)));
		Beer beer = content.get(0);
		assertThat(beer, is(not(nullValue())));
		assertThat(beer.getType(), is(equalTo(type)));

		// test all beers returned are of the correct type...
		pageRequest = new PageRequest(0, 100);

		page = repository.findByType(type, pageRequest);

		content = page.getContent();

		assertThat(page.getNumber(), is(equalTo(0)));
		assertThat(page.getNumberOfElements(), is(equalTo(1)));
		assertThat(page.getSize(), is(equalTo(100)));
		content = page.getContent();
		assertThat(content, is(not(nullValue())));
		assertThat(content.size(), is(equalTo(1)));
		page.forEach(b -> {
			assertThat(b, is(not(nullValue())));
			assertThat(b.getType(), is(equalTo(type)));
		});
	}

}
