package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MailFullSizePlaceTest extends EqualsTest<MailFullsizePlace> {

	private static final String EXPECTED = "sls:foo";
	MailFullsizePlace place = new MailFullsizePlace("sls", "foo");
	@Test
	public void testGetSecret() throws Exception {
		assertEquals("foo", place.getPhotoId());

	}

	@Test
	public void testGetEmail() throws Exception {
		assertEquals("sls", place.getTagId());

	}

	@Override
	protected MailFullsizePlace getOne() {
		return new MailFullsizePlace("sls", "foo");
	}

	@Override
	protected MailFullsizePlace getDifferentOne() {
		return new MailFullsizePlace("sls", "foo bar");
	}

	@Test
	public void testTokenizerGetToken() throws Exception {
		MailFullsizePlace.Tokenizer tokenizer = new MailFullsizePlace.Tokenizer();
		String token = tokenizer.getToken(place);
		assertEquals(EXPECTED, token);
	}

	@Test
	public void testTokenizerGetPlace() throws Exception {
		MailFullsizePlace.Tokenizer tokenizer = new MailFullsizePlace.Tokenizer();
		assertEquals(place, tokenizer.getPlace(EXPECTED));
	}
}
