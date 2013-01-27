package com.googlecode.fspotcloud.client.useraction.raster.handler;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.MailFullsizePlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MailFullSizeHandler implements IActionHandler {
    private final Logger log = Logger.getLogger(MailFullSizeHandler.class.getName());
    private final PlaceWhere placeWhere;
    private final PlaceGoTo placeGoTo;
    private final ClientLoginManager clientLoginManager;

    @Inject
    public MailFullSizeHandler(PlaceWhere placeWhere,
                               PlaceGoTo placeGoTo,
                               ClientLoginManager clientLoginManager) {
        this.placeWhere = placeWhere;
        this.placeGoTo = placeGoTo;
        this.clientLoginManager = clientLoginManager;
    }

    @Override
    public void performAction(String actionId) {
        clientLoginManager.getUserInfoAsync(new AsyncCallback<UserInfo>() {
            @Override
            public void onFailure(Throwable caught) {
               log.log(Level.INFO, "client login manager return an error", caught);
            }

            @Override
            public void onSuccess(UserInfo result) {
                Place place = placeWhere.where();
                if (place instanceof BasePlace) {
                    BasePlace basePlace = (BasePlace) place;
                    final String tagId = basePlace.getTagId();
                    final String photoId = basePlace.getPhotoId();
                    if (result.isLoggedIn()) {
                        final MailFullsizePlace mailFullsizePlace = new MailFullsizePlace(tagId, photoId);
                        placeGoTo.goTo(mailFullsizePlace);
                    } else {
                        LoginPlace loginPlace = new LoginPlace("#MailFullsizePlace:" + tagId + ":" + photoId);
                        placeGoTo.goTo(loginPlace);
                    }
                }
            }
        });

    }
}
