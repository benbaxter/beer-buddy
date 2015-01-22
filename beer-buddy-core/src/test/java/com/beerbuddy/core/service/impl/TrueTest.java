package com.beerbuddy.core.service.impl;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

public class TrueTest {

	
	@Test
	public void trueIsTrue() {
		Assert.assertTrue(true);
		Assert.assertTrue("true is equal to true...", true);
		
		Assert.assertThat(true, Is.is(IsEqual.equalTo(true)));
		Assert.assertThat("True is true", true, Is.is(IsEqual.equalTo(true)));
		
		assertThat("True is true", true, is(equalTo(true)));
	}
	
}
