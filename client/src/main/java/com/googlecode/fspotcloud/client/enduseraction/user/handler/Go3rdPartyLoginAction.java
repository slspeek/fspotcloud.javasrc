package com.googlecode.fspotcloud.client.enduseraction.user.handler;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class Go3rdPartyLoginAction implements IActionHandler {

    private final IClientLoginManager clientLoginManager;
    private final PlaceWhere placeWhere;

    @Inject
    public Go3rdPartyLoginAction(IClientLoginManager clientLoginManager,
                                 PlaceWhere placeWhere
    ) {
        this.clientLoginManager = clientLoginManager;
        this.placeWhere = placeWhere;
    }


    @Override
    public void performAction(String actionId) {
        String url = null;
        Place place = placeWhere.getRawWhere();
        if (place instanceof LoginPlace) {
            url = ((LoginPlace) place).getNextUrl();
        } else {
            url = placeWhere.whereToken();
        }
        clientLoginManager.goTo3rdPartyLogin(url);
    }
}
