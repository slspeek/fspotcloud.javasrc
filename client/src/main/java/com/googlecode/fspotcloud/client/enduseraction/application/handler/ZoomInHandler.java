package com.googlecode.fspotcloud.client.enduseraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.IRasterer;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class ZoomInHandler implements IActionHandler

{
    private final Logger log = Logger.getLogger(ZoomInHandler.class.getName());

    @Inject
    private IRasterer placeManager;


    @Override
    public void performAction(String actionId) {
        placeManager.zoom(Navigator.Zoom.IN);
    }
}

