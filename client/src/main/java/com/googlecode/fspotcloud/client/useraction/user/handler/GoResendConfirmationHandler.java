package com.googlecode.fspotcloud.client.useraction.user.handler;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.SendConfirmationPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.useraction.PlaceMoverBase;

public class GoResendConfirmationHandler extends PlaceMoverBase {

    @Inject
    public GoResendConfirmationHandler(PlaceGoTo placeGoTo) {
        super(placeGoTo);
    }

    @Override
    public Place getPlace() {
        return new SendConfirmationPlace();
    }
}
