package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.UserSynchronizesPeerAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

public class SynchronizeHandler implements IActionHandler {
    private final DispatchAsync dispatcher;
    private final StatusView statusView;


    @Inject
    public SynchronizeHandler(DispatchAsync dispatcher,
                              StatusView statusView) {
        this.dispatcher = dispatcher;
        this.statusView = statusView;
    }

    @Override
    public void performAction(String actionId) {
        statusView.setStatusText("Calling to server with a request to synchronize with the peer");
        dispatcher.execute(new UserSynchronizesPeerAction(),
                new AsyncCallback<VoidResult>() {
                    public void onFailure(Throwable caught) {
                        statusView.setStatusText("The synchronize request could not be full-filled");
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        statusView.setStatusText("The synchronize request was successfully scheduled for the peer");
                    }
                });
    }
}
