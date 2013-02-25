package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.TagApprovalPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Logger;

public class ManageAccessHandler implements IActionHandler {

    @Inject
    private IPlaceController IPlaceController;
    @Inject
    private CurrentTagNodeAsync nodeAsync;
    private Logger log = Logger.getLogger(ManageAccessHandler.class.getName());

    @Override
    public void performAction(String actionId) {
        nodeAsync.getNode(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(TagNode tagNode) {
                log.info("Go to manage access " + tagNode);
                IPlaceController.goTo(new TagApprovalPlace(tagNode.getId()));
            }
        });

    }
}
