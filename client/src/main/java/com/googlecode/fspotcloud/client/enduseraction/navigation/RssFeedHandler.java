package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.LoadNewLocation;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class RssFeedHandler implements IActionHandler {

    private final Logger log = Logger.getLogger(RssFeedHandler.class.getName());
    @Inject
    private PlaceWhere placeWhere;
    @Inject
    private LoadNewLocation loader;

    @Override
    public void performAction(String actionId) {
        BasePlace basePlace = placeWhere.where();
        log.info("Rss: " + basePlace);
        String tagId = basePlace.getTagId();
        if (tagId != null) {
            loader.setLocation("/rss?tag=" + tagId);
        }
    }
}
