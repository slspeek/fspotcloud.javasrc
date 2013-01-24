package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.application.handler.DemoFactory;
import com.googlecode.fspotcloud.client.useraction.application.handler.HideControlsHandler;
import com.googlecode.fspotcloud.client.useraction.application.handler.TreeFocusHandler;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class UserActionHandlerBinder {

    private final ApplicationActions applicationActions;

    @Inject
    UserActionHandlerBinder(ConfigBuilder configBuilder,
                            HideControlsHandler hideControlsHandler,
                            TreeFocusHandler treeFocusHandler,
                            DemoFactory demoFactory,
                            ApplicationActions applicationActions) {
        this.applicationActions = applicationActions;
        configBuilder.bindHandler(applicationActions.hide_controls, hideControlsHandler);
        configBuilder.bindHandler(applicationActions.tree_focus, treeFocusHandler);
        configBuilder.bindHandler(applicationActions.demo, demoFactory.getDemo());
    }

}
