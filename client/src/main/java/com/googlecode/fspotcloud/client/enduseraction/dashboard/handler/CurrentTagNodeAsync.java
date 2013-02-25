package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Logger;

public class CurrentTagNodeAsync {

    @Inject
    protected IPlaceController placeController;
    @Inject
    protected DataManager dataManager;
    private Logger log = Logger.getLogger(CurrentTagNodeAsync.class.getName());

    public void getNode(AsyncCallback<TagNode> node) {
        TagPlace place = (TagPlace) placeController.getRawWhere();
        String tagId = place.getTagId();
        log.info("Current node async: " + tagId);
        dataManager.getAdminTagNode(tagId, node);
    }


}
