package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.TagDetailsView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.UserImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.UserUnImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.TagNode;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;

public class ImportTagHandler implements IActionHandler {

    @Inject
    DispatchAsync dispatch;
    @Inject
    CurrentTagNodeAsync nodeAsync;
    @Inject
    TagDetailsView.TagDetailsPresenter tagDetailsView;

    @Override
    public void performAction(String actionId) {
        nodeAsync.getNode(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(final TagNode tagNode) {
                if (tagNode.isImportIssued()) {
                    dispatch.execute(new UserUnImportsTagAction(tagNode.getId()),
                            new AsyncCallback<VoidResult>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                }

                                @Override
                                public void onSuccess(VoidResult result) {
                                    tagDetailsView.populateView();
                                }
                            });
                } else {
                    dispatch.execute(new UserImportsTagAction(tagNode.getId()),
                            new AsyncCallback<VoidResult>() {
                                @Override
                                public void onFailure(Throwable caught) {
                                }

                                @Override
                                public void onSuccess(VoidResult result) {
                                    tagDetailsView.populateView();
                                }
                            });
                }
            }
        });

    }
}
