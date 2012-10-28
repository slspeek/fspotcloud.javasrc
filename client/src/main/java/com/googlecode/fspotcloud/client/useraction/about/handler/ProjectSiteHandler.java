package com.googlecode.fspotcloud.client.useraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.LoadNewLocation;

public class ProjectSiteHandler extends LoadNewLocationAction {

    @Inject
    private ProjectSiteHandler(LoadNewLocation loader) {
        super(loader, "http://code.google.com/p/fspotcloud");
    }
}
