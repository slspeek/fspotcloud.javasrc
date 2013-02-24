package com.googlecode.fspotcloud.client.enduseraction.raster.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class SetRasterHandler implements IActionHandler {

    private final Navigator navigator;
    private final int size;

    @Inject
    SetRasterHandler(Navigator navigator, @Assisted int size) {
        this.navigator = navigator;
        this.size = size;
    }

    @Override
    public void performAction(String actionId) {
        navigator.setRasterDimension(size, size);
    }
}
