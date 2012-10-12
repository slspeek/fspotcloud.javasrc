package com.googlecode.fspotcloud.client.useraction.raster.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class ResetRasterHandler implements IActionHandler {

    private final Navigator navigator;

    @Inject
    public ResetRasterHandler(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void performAction(String actionId) {
        navigator.resetRasterSize();
    }
}
