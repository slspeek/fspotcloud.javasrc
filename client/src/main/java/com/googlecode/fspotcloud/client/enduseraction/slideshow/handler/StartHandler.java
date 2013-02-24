package com.googlecode.fspotcloud.client.enduseraction.slideshow.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Slideshow;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class StartHandler implements IActionHandler {

    private final Slideshow slideshow;

    @Inject
    public StartHandler(Slideshow slideshow) {
        this.slideshow = slideshow;
    }

    @Override
    public void performAction(String actionId) {
        slideshow.start();
    }
}
