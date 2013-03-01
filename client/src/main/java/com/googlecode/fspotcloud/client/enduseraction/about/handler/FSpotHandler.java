package com.googlecode.fspotcloud.client.enduseraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;

public class FSpotHandler extends LoadNewLocationAction {

    @Inject
    private FSpotHandler(OpenNewTab loader) {
        super(loader, "http://f-spot.org/");
    }
}
