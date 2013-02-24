package com.googlecode.fspotcloud.client.enduseraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class GoToLatestHandler implements IActionHandler {

    @Inject
    Navigator navigator;

    @Override
    public void performAction(String actionId) {
        navigator.goToLatestTag();
    }
}
