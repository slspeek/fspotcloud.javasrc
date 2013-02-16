package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.UserDeletesAllCommandsAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveAllCommandsHandler implements IActionHandler {

    private final DispatchAsync dispatcher;
    private Logger log = Logger.getLogger(RemoveAllCommandsHandler.class.getName());

    @Inject
    public RemoveAllCommandsHandler(DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void performAction(String actionId) {
        dispatcher.execute(new UserDeletesAllCommandsAction(),
                new AsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "Action Exception ", caught);
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                    }
                });
    }
}
