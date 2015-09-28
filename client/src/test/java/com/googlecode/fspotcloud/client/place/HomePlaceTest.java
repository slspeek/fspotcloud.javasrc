package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;

public class HomePlaceTest extends EqualsTest<HomePlace> {
	@Override
	protected HomePlace getOne() {
		return new HomePlace();
	}

	@Override
	protected HomePlace getDifferentOne() {
		return null; //To change body of implemented methods use File | Settings | File Templates.
	}
}
