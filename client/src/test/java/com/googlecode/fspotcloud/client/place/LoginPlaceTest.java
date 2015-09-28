package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginPlaceTest extends EqualsTest<LoginPlace> {

	private static final String EXPECTED = "sls";
	LoginPlace place = new LoginPlace("sls");
	@Test
	public void testGetEmail() throws Exception {
		assertEquals("sls", place.getNextUrl());

	}

	@Test
	public void testDefaultCons() {
		assertEquals("", new LoginPlace().getNextUrl());
	}

	@Override
	protected LoginPlace getOne() {
		return new LoginPlace("sls");
	}

	@Override
	protected LoginPlace getDifferentOne() {
		return new LoginPlace("rms");
	}

	@Test
	public void testTokenizerGetToken() throws Exception {
		LoginPlace.Tokenizer tokenizer = new LoginPlace.Tokenizer();
		String token = tokenizer.getToken(place);
		assertEquals(EXPECTED, token);
	}

	@Test
	public void testTokenizerGetPlace() throws Exception {
		LoginPlace.Tokenizer tokenizer = new LoginPlace.Tokenizer();
		assertEquals(place, tokenizer.getPlace(EXPECTED));
	}
}
