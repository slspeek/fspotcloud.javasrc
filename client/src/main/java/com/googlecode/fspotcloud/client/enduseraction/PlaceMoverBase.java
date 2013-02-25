package com.googlecode.fspotcloud.client.enduseraction;

import com.google.gwt.place.shared.Place;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public abstract class PlaceMoverBase implements IActionHandler {

    private final IPlaceController IPlaceController;

    protected PlaceMoverBase(IPlaceController IPlaceController) {
        this.IPlaceController = IPlaceController;
    }

    public abstract Place getPlace();

    @Override
    public void performAction(String actionId) {
        final Place place = getPlace();
        if (place != null) {
            IPlaceController.goTo(place);
        }
    }
}
