package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.LoadNewLocation;

public class DashboardAction extends LoadNewLocationAction {
    @Inject
    public DashboardAction(LoadNewLocation loader) {
        super(loader, "Dashboard.html");
    }
}
