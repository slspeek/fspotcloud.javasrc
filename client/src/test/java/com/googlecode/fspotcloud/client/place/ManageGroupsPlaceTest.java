package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ManageGroupsPlaceTest extends EqualsTest<ManageGroupsPlace> {

	@Override
	protected ManageGroupsPlace getOne() {
		return new ManageGroupsPlace();
	}

	@Override
	protected ManageGroupsPlace getDifferentOne() {
		return null; //To change body of implemented methods use File | Settings | File Templates.
	}

	@Test
	public void testTokenizerGetToken() throws Exception {
		ManageGroupsPlace.Tokenizer tokenizer = new ManageGroupsPlace.Tokenizer();
		String token = tokenizer.getToken(new ManageGroupsPlace());
		assertEquals("", token);
	}

	@Test
	public void testTokenizerGetPlace() throws Exception {
		ManageGroupsPlace.Tokenizer tokenizer = new ManageGroupsPlace.Tokenizer();
		assertEquals(new ManageGroupsPlace(), tokenizer.getPlace(""));
	}
}
