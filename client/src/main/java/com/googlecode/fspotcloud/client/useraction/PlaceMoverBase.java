package com.googlecode.fspotcloud.client.useraction;

import com.google.gwt.place.shared.Place;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public abstract class PlaceMoverBase implements IActionHandler {

    private final PlaceGoTo placeGoTo;

    protected PlaceMoverBase(PlaceGoTo placeGoTo) {
        this.placeGoTo = placeGoTo;
    }

    public abstract Place getPlace();

    @Override
    public void performAction(String actionId) {
        placeGoTo.goTo(getPlace());
    }
}
