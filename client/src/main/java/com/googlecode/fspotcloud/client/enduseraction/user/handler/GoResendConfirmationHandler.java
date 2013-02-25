package com.googlecode.fspotcloud.client.enduseraction.user.handler;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.SendConfirmationPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.enduseraction.PlaceMoverBase;

public class GoResendConfirmationHandler extends PlaceMoverBase {

    @Inject
    public GoResendConfirmationHandler(IPlaceController IPlaceController) {
        super(IPlaceController);
    }

    @Override
    public Place getPlace() {
        return new SendConfirmationPlace();
    }
}
