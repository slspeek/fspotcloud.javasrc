package com.googlecode.fspotcloud.client.enduseraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.PlaceCalculator;
import com.googlecode.fspotcloud.client.place.PlaceConverter;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class ToggleAutoHideHandler implements IActionHandler {
    @Inject
    private IPlaceController placeController;

    @Override
    public void performAction(String actionId) {
        BasePlace basePlace = placeController.where();
        PlaceConverter converter = new PlaceConverter(basePlace);
        converter.setAutoHide(!basePlace.isAutoHide());
        placeController.goTo(converter.getNewPlace());
    }
}
