package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ManageUsersPlaceEqualsTest extends EqualsTest<ManageUsersPlace> {
	@Test
	public void testGetUserGroupId() throws Exception {
		ManageUsersPlace place = new ManageUsersPlace(1L);
		assertEquals(1, place.getUserGroupId());
	}

	@Override
	protected ManageUsersPlace getOne() {
		return new ManageUsersPlace(1L);
	}

	@Override
	protected ManageUsersPlace getDifferentOne() {
		return new ManageUsersPlace(2L);
	}
}
