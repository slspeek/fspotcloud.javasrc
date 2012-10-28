package com.googlecode.fspotcloud.client.useraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.LoadNewLocation;

public class FSpotHandler extends LoadNewLocationAction {

    @Inject
    private FSpotHandler(LoadNewLocation loader) {
        super(loader, "http://f-spot.org/");
    }
}
