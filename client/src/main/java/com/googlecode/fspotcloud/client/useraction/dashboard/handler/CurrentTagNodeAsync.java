package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Logger;

public class CurrentTagNodeAsync {

    @Inject
    protected PlaceWhere placeWhere;
    @Inject
    protected DataManager dataManager;
    private Logger log = Logger.getLogger(CurrentTagNodeAsync.class.getName());

    public void getNode(AsyncCallback<TagNode> node) {
        TagPlace place = (TagPlace) placeWhere.getRawWhere();
        String tagId = place.getTagId();
        log.info("Current node async: " + tagId);
        dataManager.getAdminTagNode(tagId, node);
    }


}
