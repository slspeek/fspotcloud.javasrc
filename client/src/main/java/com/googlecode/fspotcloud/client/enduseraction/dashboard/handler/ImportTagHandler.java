package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.Dashboard;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.client.main.view.api.TagDetailsView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.UserImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.UserUnImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.TagNode;
import net.customware.gwt.dispatch.client.DispatchAsync;

public class ImportTagHandler implements IActionHandler {

    @Inject
    DispatchAsync dispatch;
    @Inject
    CurrentTagNodeAsync nodeAsync;
    @Inject
    TagDetailsView.TagDetailsPresenter tagDetailsView;
    @Inject
    @Dashboard
    StatusView statusView;


    @Override
    public void performAction(String actionId) {
        nodeAsync.getNode(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(final TagNode tagNode) {
                if (tagNode.isImportIssued()) {
                    statusView.setStatusText("Requesting the server to remove category: " + tagNode.getTagName());
                    dispatch.execute(new UserUnImportsTagAction(tagNode.getId()),
                            new AsyncCallback<VoidResult>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    statusView.setStatusText("The server could not remove category: " + tagNode.getTagName() + " due to an error.");
                                }

                                @Override
                                public void onSuccess(VoidResult result) {
                                    tagDetailsView.populateView();
                                    statusView.setStatusText("The server will remove category: " + tagNode.getTagName());
                                }
                            });
                } else {
                    statusView.setStatusText("Requesting the server to import category: " + tagNode.getTagName());
                    dispatch.execute(new UserImportsTagAction(tagNode.getId()),
                            new AsyncCallback<VoidResult>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                    statusView.setStatusText("The server could not import category: " + tagNode.getTagName() + " due to an error.");
                                }

                                @Override
                                public void onSuccess(VoidResult result) {
                                    statusView.setStatusText("The server has scheduled an import request for category: " + tagNode.getTagName());
                                    tagDetailsView.populateView();
                                }
                            });
                }
            }
        });

    }
}
