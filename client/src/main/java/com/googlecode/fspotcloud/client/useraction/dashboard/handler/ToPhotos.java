package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.HomePlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.TagNode;

public class ToPhotos implements IActionHandler {


    private final PlaceWhere placeWhere;
    private final DataManager dataManager;
    private final PlaceGoTo placeGoTo;

    @Inject
    public ToPhotos(PlaceWhere placeWhere,
                    DataManager dataManager,
                    PlaceGoTo placeGoTo) {
        this.placeWhere = placeWhere;
        this.dataManager = dataManager;
        this.placeGoTo = placeGoTo;
    }


    @Override
    public void performAction(String actionId) {
        final String tagId = placeWhere.getLastTagId();
        dataManager.getAdminTagNode(tagId, new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                placeGoTo.goTo(new HomePlace());
            }

            @Override
            public void onSuccess(TagNode result) {
                if (result != null && result.isImportIssued()) {
                    placeGoTo.goTo(new BasePlace(tagId, "", 5, 4));
                } else {
                    placeGoTo.goTo(new HomePlace());
                }
            }
        });
    }
}
