package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.MyUserGroupsPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class ManageUserGroupsHandler implements IActionHandler {

    @Inject
    private PlaceGoTo placeGoTo;

    @Override
    public void performAction(String actionId) {
        placeGoTo.goTo(new MyUserGroupsPlace());
    }
}
