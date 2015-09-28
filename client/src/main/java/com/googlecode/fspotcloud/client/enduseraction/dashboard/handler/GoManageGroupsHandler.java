package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.ManageGroupsPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class GoManageGroupsHandler implements IActionHandler {

	@Inject
	private IPlaceController IPlaceController;

	@Override
	public void performAction(String actionId) {
		IPlaceController.goTo(new ManageGroupsPlace());
	}
}
