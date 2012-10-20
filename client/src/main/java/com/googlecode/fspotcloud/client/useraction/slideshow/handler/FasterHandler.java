package com.googlecode.fspotcloud.client.useraction.slideshow.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Slideshow;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class FasterHandler implements IActionHandler {

    private final Slideshow slideshow;

    @Inject
    public FasterHandler(Slideshow slideshow) {
        this.slideshow = slideshow;
    }

    @Override
    public void performAction(String actionId) {
        slideshow.faster();
    }
}
