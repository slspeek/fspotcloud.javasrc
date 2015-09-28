package com.googlecode.fspotcloud.client.main.gin;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButton;

public class BigToPhotosButtonProvider implements Provider<ActionButton> {
	@Inject
	BigButtonFactory buttonFactory;
	@Inject
	DashboardActions dashboardActions;

	@Override
	public ActionButton get() {
		return buttonFactory.getButton(dashboardActions.toPhotos);
	}
}
