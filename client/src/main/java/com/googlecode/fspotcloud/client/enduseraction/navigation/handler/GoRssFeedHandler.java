package com.googlecode.fspotcloud.client.enduseraction.navigation.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GoRssFeedHandler implements IActionHandler {

	private final Logger log = Logger.getLogger(GoRssFeedHandler.class
			.getName());
	@Inject
	private IPlaceController placeController;
	@Inject
	private OpenNewTab loader;

	@Override
	public void performAction(String actionId) {
		BasePlace basePlace = placeController.where();
		log.log(Level.FINE, "Rss for place: " + basePlace);
		String tagId = basePlace.getTagId();
		loader.setLocation("/rss?tag=" + tagId);
	}
}
