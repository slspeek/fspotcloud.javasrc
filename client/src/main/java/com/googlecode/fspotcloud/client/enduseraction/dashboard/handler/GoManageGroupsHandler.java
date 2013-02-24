package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.ManageUserGroupsPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class GoManageGroupsHandler implements IActionHandler {

    @Inject
    private PlaceGoTo placeGoTo;

    @Override
    public void performAction(String actionId) {
        placeGoTo.goTo(new ManageUserGroupsPlace());
    }
}
