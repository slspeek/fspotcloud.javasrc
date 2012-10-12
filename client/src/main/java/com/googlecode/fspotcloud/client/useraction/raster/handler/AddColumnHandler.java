package com.googlecode.fspotcloud.client.useraction.raster.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class AddColumnHandler implements IActionHandler {

    private final Navigator navigator;

    @Inject
    public AddColumnHandler(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void performAction(String actionId) {
        navigator.increaseRasterWidth(1);
    }
}
