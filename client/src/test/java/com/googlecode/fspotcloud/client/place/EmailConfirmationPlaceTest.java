package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmailConfirmationPlaceTest
		extends
			EqualsTest<EmailConfirmationPlace> {

	private static final String EXPECTED = "sls:foo";
	EmailConfirmationPlace place = new EmailConfirmationPlace("sls", "foo");
	@Test
	public void testGetSecret() throws Exception {
		assertEquals("foo", place.getSecret());

	}

	@Test
	public void testGetEmail() throws Exception {
		assertEquals("sls", place.getEmail());

	}

	@Override
	protected EmailConfirmationPlace getOne() {
		return new EmailConfirmationPlace("sls", "foo");
	}

	@Override
	protected EmailConfirmationPlace getDifferentOne() {
		return new EmailConfirmationPlace("sls", "foo bar");
	}

	@Test
	public void testTokenizerGetToken() throws Exception {
		EmailConfirmationPlace.Tokenizer tokenizer = new EmailConfirmationPlace.Tokenizer();
		String token = tokenizer.getToken(place);
		assertEquals(EXPECTED, token);
	}

	@Test
	public void testTokenizerGetPlace() throws Exception {
		EmailConfirmationPlace.Tokenizer tokenizer = new EmailConfirmationPlace.Tokenizer();
		assertEquals(place, tokenizer.getPlace(EXPECTED));
	}
}
