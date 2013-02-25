package com.googlecode.fspotcloud.client.enduseraction.raster.handler;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.MailFullsizePlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MailFullSizeHandler implements IActionHandler {
    private final Logger log = Logger.getLogger(MailFullSizeHandler.class.getName());
    private final IPlaceController placeController;
    private final IClientLoginManager IClientLoginManager;

    @Inject
    public MailFullSizeHandler(
            IPlaceController placeController,
            IClientLoginManager IClientLoginManager) {
        this.placeController = placeController;
        this.IClientLoginManager = IClientLoginManager;
    }

    @Override
    public void performAction(String actionId) {
        IClientLoginManager.getUserInfoAsync(new AsyncCallback<UserInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.INFO, "client login manager return an error", caught);
            }

            @Override
            public void onSuccess(UserInfo result) {
                Place place = placeController.where();
                if (place instanceof BasePlace) {
                    BasePlace basePlace = (BasePlace) place;
                    final String tagId = basePlace.getTagId();
                    final String photoId = basePlace.getPhotoId();
                    if (result.isLoggedIn()) {
                        final MailFullsizePlace mailFullsizePlace = new MailFullsizePlace(tagId, photoId);
                        placeController.goTo(mailFullsizePlace);
                    } else {
                        LoginPlace loginPlace = new LoginPlace("#MailFullsizePlace:" + tagId + ":" + photoId);
                        placeController.goTo(loginPlace);
                    }
                }
            }
        });

    }
}
