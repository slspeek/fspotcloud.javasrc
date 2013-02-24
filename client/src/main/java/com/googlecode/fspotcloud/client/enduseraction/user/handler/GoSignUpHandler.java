package com.googlecode.fspotcloud.client.enduseraction.user.handler;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.SignUpPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.enduseraction.PlaceMoverBase;

public class GoSignUpHandler extends PlaceMoverBase {

    @Inject
    public GoSignUpHandler(PlaceGoTo placeGoTo) {
        super(placeGoTo);
    }

    @Override
    public Place getPlace() {
        return new SignUpPlace();
    }
}
