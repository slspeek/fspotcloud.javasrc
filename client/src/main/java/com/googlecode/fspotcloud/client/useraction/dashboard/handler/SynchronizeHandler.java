package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.UserSynchronizesPeerAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;

public class SynchronizeHandler implements IActionHandler {
    private final DispatchAsync dispatcher;

    @Inject
    public SynchronizeHandler(DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void performAction(String actionId) {
        dispatcher.execute(new UserSynchronizesPeerAction(),
                new AsyncCallback<VoidResult>() {
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                    }
                });
    }
}
