package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;

public class SendConfirmationPlaceEqualsTest extends EqualsTest {
	@Override
	protected Object getOne() {
		return new SendConfirmationPlace();
	}

	@Override
	protected Object getDifferentOne() {
		return null;
	}
}
