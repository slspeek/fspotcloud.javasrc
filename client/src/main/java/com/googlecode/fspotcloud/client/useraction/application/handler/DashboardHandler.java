package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class DashboardHandler implements IActionHandler {

    private final PlaceGoTo placeGoTo;

    @Inject
    public DashboardHandler(PlaceGoTo placeGoTo) {
        this.placeGoTo = placeGoTo;
    }


    @Override
    public void performAction(String actionId) {
        placeGoTo.goTo(TagPlace.DEFAULT);
    }
}
