package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

public class PlaceControllerImpl implements IPlaceController {

    @Inject
    private PlaceController placeController;

    @Override
    public void goTo(Place newPlace) {
        placeController.goTo(newPlace);
    }

    @Override
    public Place getWhere() {
        return placeController.getWhere();
    }
}
