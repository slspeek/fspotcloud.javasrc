package com.googlecode.fspotcloud.client.enduseraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class DashboardHandler implements IActionHandler {

    private final IPlaceController placeController;

    @Inject
    public DashboardHandler(IPlaceController placeController) {
        this.placeController = placeController;
    }

    @Override
    public void performAction(String actionId) {
        BasePlace lastBasePlace = placeController.where();
        TagPlace destTagPlace;
        if (lastBasePlace != null) {
            destTagPlace = new TagPlace(lastBasePlace.getTagId());
        } else {
            destTagPlace = TagPlace.DEFAULT;
        }
        placeController.goTo(destTagPlace);
    }
}
