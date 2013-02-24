package com.googlecode.fspotcloud.client.enduseraction.raster.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class RemoveRowHandler implements IActionHandler {

    private final Navigator navigator;

    @Inject
    public RemoveRowHandler(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void performAction(String actionId) {
        navigator.increaseRasterHeight(-1);
    }
}
