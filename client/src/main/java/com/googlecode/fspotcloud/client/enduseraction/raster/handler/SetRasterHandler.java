package com.googlecode.fspotcloud.client.enduseraction.raster.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.place.api.IRasterer;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class SetRasterHandler implements IActionHandler {

	private final IRasterer rasterer;
	private final int size;

	@Inject
	SetRasterHandler(IRasterer rasterer, @Assisted int size) {
		this.rasterer = rasterer;
		this.size = size;
	}

	@Override
	public void performAction(String actionId) {
		rasterer.setRasterDimension(size, size);
	}
}
