package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Assert;
import org.junit.Test;

public class TagApprovalPlaceEqualsTest extends EqualsTest<TagApprovalPlace> {
	@Override
	protected TagApprovalPlace getOne() {
		return new TagApprovalPlace("1");
	}

	@Override
	protected TagApprovalPlace getDifferentOne() {
		return new TagApprovalPlace("2");
	}

	@Test
	public void testGetTagId() throws Exception {
		TagApprovalPlace place = new TagApprovalPlace("1");
		Assert.assertEquals("1", place.getTagId());

	}
}
