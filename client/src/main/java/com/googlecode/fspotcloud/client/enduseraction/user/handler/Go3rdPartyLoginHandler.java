package com.googlecode.fspotcloud.client.enduseraction.user.handler;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class Go3rdPartyLoginHandler implements IActionHandler {

	private final IClientLoginManager clientLoginManager;
	private final IPlaceController placeController;

	@Inject
	public Go3rdPartyLoginHandler(IClientLoginManager clientLoginManager,
			IPlaceController placeController) {
		this.clientLoginManager = clientLoginManager;
		this.placeController = placeController;
	}

	@Override
	public void performAction(String actionId) {
		String url = null;
		Place place = placeController.getRawWhere();
		if (place instanceof LoginPlace) {
			url = ((LoginPlace) place).getNextUrl();
		} else {
			url = placeController.whereToken();
		}
		clientLoginManager.goTo3rdPartyLogin(url);
	}
}
