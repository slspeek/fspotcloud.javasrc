package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.application.handler.HideControlsAction;
import com.googlecode.fspotcloud.client.useraction.application.handler.TreeFocusAction;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class UserActionHandlerBinder {


    @Inject
    UserActionHandlerBinder(ConfigBuilder configBuilder, HideControlsAction hideControlsAction, TreeFocusAction treeFocusAction
    )                                                  {
        configBuilder.bindHandler(ApplicationActions.HIDE_CONTROLS, hideControlsAction);
        configBuilder.bindHandler(ApplicationActions.TREE_FOCUS, treeFocusAction);
    }

}
