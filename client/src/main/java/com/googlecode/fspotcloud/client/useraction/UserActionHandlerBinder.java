package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.application.handler.HideControlsHandler;
import com.googlecode.fspotcloud.client.useraction.application.handler.TreeFocusHandler;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class UserActionHandlerBinder {


    @Inject
    UserActionHandlerBinder(ConfigBuilder configBuilder, HideControlsHandler hideControlsHandler, TreeFocusHandler treeFocusHandler
    )                                                  {
        configBuilder.bindHandler(ApplicationActions.HIDE_CONTROLS, hideControlsHandler);
        configBuilder.bindHandler(ApplicationActions.TREE_FOCUS, treeFocusHandler);
    }

}
