package com.googlecode.fspotcloud.client.enduseraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.LoadNewLocation;

public class BuildServerHandler extends LoadNewLocationAction {

    @Inject
    private BuildServerHandler(LoadNewLocation loader) {
        super(loader, "http://188.200.115.158:9000/");
    }
}
