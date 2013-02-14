package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class DashboardHandler implements IActionHandler {

    private final PlaceWhere placeWhere;
    private final PlaceGoTo placeGoTo;

    @Inject
    public DashboardHandler(PlaceWhere placeWhere, PlaceGoTo placeGoTo) {
        this.placeWhere = placeWhere;
        this.placeGoTo = placeGoTo;
    }


    @Override
    public void performAction(String actionId) {
        BasePlace lastBasePlace = placeWhere.where();
        TagPlace destTagPlace;
        if (lastBasePlace != null) {
            destTagPlace = new TagPlace(lastBasePlace.getTagId());
        } else {
            destTagPlace = TagPlace.DEFAULT;
        }
        placeGoTo.goTo(destTagPlace);
    }
}
