package com.googlecode.fspotcloud.client.enduseraction.raster.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.PlaceManager;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class ToggleTabularViewHandler implements IActionHandler {

    private final PlaceManager placeManager;

    @Inject
    public ToggleTabularViewHandler(PlaceManager placeManager) {
        this.placeManager = placeManager;
    }


    @Override
    public void performAction(String actionId) {
        placeManager.toggleRasterView();
    }
}
