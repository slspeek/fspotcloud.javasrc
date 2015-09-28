package com.googlecode.fspotcloud.shared.main.test;

import com.googlecode.fspotcloud.shared.main.GetTagNodeAction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetTagNodeActionTest {

	public static final String ID = "ID";

	private final GetTagNodeAction action = new GetTagNodeAction(ID);

	@Test
	public void testGetTagId() throws Exception {
		assertEquals(ID, action.getTagId());
	}

	@Test
	public void testToString() throws Exception {
		assertNotNull(action.toString());
	}
}
