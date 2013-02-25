package com.googlecode.fspotcloud.client.enduseraction.user.handler;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.SendResetPasswordPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.enduseraction.PlaceMoverBase;

public class GoResetPasswordHandler extends PlaceMoverBase {

    @Inject
    public GoResetPasswordHandler(IPlaceController IPlaceController) {
        super(IPlaceController);
    }

    @Override
    public Place getPlace() {
        return new SendResetPasswordPlace();
    }
}
