package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class ZoomOutAction implements IActionHandler

{
    private final Logger log = Logger.getLogger(ZoomOutAction.class.getName());
    private final Navigator navigator;

    @Inject
    public ZoomOutAction(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void performAction(String actionId) {
        navigator.zoom(Navigator.Zoom.OUT);
    }
}

