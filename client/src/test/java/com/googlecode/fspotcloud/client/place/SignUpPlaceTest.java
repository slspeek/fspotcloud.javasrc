package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SignUpPlaceTest extends EqualsTest<SignUpPlace> {

	@Override
	protected SignUpPlace getOne() {
		return new SignUpPlace();
	}

	@Override
	protected SignUpPlace getDifferentOne() {
		return null; //To change body of implemented methods use File | Settings | File Templates.
	}

	@Test
	public void testTokenizerGetToken() throws Exception {
		SignUpPlace.Tokenizer tokenizer = new SignUpPlace.Tokenizer();
		String token = tokenizer.getToken(new SignUpPlace());
		assertEquals("", token);
	}

	@Test
	public void testTokenizerGetPlace() throws Exception {
		SignUpPlace.Tokenizer tokenizer = new SignUpPlace.Tokenizer();
		assertEquals(new SignUpPlace(), tokenizer.getPlace(""));
	}
}
