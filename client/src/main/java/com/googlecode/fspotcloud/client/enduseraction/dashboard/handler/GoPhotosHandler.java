package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.HomePlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GoPhotosHandler implements IActionHandler {


    private final Logger log = Logger.getLogger(GoPhotosHandler.class.getName());
    private final DataManager dataManager;
    private final IPlaceController placeController;
    private final IClientLoginManager clientLoginManager;

    @Inject
    public GoPhotosHandler(
                           DataManager dataManager,
                           IPlaceController placeController,
                           IClientLoginManager clientLoginManager) {
        this.dataManager = dataManager;
        this.placeController = placeController;
        this.clientLoginManager = clientLoginManager;
    }


    @Override
    public void performAction(String actionId) {
        clientLoginManager.getUserInfoAsync(new AsyncCallback<UserInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                placeController.goTo(new HomePlace());
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onSuccess(UserInfo result) {
                if (result.isAdmin()) {
                    final String tagId = placeController.getLastTagId();
                    dataManager.getAdminTagNode(tagId, new AsyncCallback<TagNode>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            log.log(Level.WARNING, "get Admin tag node failed:", caught);
                            placeController.goTo(new HomePlace());
                        }

                        @Override
                        public void onSuccess(TagNode result) {
                            if (result != null && result.isImportIssued()) {
                                placeController.goTo(new BasePlace(tagId, "", 5, 4, true));
                            } else {
                                placeController.goTo(new HomePlace());
                            }
                        }
                    });
                } else {
                    BasePlace basePlace = placeController.where();
                    if (basePlace != null) {
                        placeController.goTo(basePlace);

                    } else {

                        placeController.goTo(new HomePlace());
                    }
                }
            }
        });

    }
}
