package com.googlecode.fspotcloud.client.enduseraction.user.handler;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.PlaceMoverBase;
import com.googlecode.fspotcloud.client.place.SignUpPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;

public class GoSignUpHandler extends PlaceMoverBase {

    @Inject
    public GoSignUpHandler(IPlaceController IPlaceController) {
        super(IPlaceController);
    }

    @Override
    public Place getPlace() {
        return new SignUpPlace();
    }
}
