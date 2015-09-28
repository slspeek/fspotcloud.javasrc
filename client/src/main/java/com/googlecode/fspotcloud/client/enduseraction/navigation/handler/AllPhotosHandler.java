package com.googlecode.fspotcloud.client.enduseraction.navigation.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.PlaceBuilder;
import com.googlecode.fspotcloud.client.place.RasterState;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class AllPhotosHandler implements IActionHandler {

	private final RasterState rasterState;
	private final IPlaceController placeController;

	@Inject
	public AllPhotosHandler(RasterState rasterState,
			IPlaceController placeController) {
		this.rasterState = rasterState;
		this.placeController = placeController;
	}

	@Override
	public void performAction(String actionId) {
		BasePlace basePlace = placeController.where();
		BasePlace destPlace;
		if (basePlace != null) {
			PlaceBuilder converter = new PlaceBuilder(basePlace);
			converter.setTagId("all");
			destPlace = converter.place();
		} else {
			destPlace = new BasePlace("all", null,
					rasterState.getColumnCount(), rasterState.getRowCount(),
					rasterState.isAutoHide());
		}
		placeController.goTo(destPlace);
	}
}
