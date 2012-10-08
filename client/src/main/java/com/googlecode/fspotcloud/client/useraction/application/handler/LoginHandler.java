package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class LoginHandler implements IActionHandler

{
    private final Logger log = Logger.getLogger(LoginHandler.class.getName());
    private final PlaceGoTo placeGoTo;

    @Inject
    public LoginHandler(PlaceGoTo placeGoTo) {
        this.placeGoTo = placeGoTo;
    }

    @Override
    public void performAction(String actionId) {
        placeGoTo.goTo(new LoginPlace());
    }
}
