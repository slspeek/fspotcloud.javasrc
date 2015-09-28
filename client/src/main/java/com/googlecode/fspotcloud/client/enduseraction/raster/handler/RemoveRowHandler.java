package com.googlecode.fspotcloud.client.enduseraction.raster.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.IRasterer;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class RemoveRowHandler implements IActionHandler {

	private final IRasterer rasterer;

	@Inject
	public RemoveRowHandler(IRasterer rasterer) {
		this.rasterer = rasterer;
	}

	@Override
	public void performAction(String actionId) {
		rasterer.increaseRasterHeight(-1);
	}
}
