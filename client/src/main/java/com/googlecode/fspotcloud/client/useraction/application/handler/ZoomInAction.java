package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class ZoomInAction implements IActionHandler

{
    private final Logger log = Logger.getLogger(ZoomInAction.class.getName());
    private final Navigator navigator;

    @Inject
    public ZoomInAction(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void performAction(String actionId) {
        navigator.zoom(Navigator.Zoom.IN);
    }
}

