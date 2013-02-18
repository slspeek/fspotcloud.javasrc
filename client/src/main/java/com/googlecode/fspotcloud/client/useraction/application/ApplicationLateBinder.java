package com.googlecode.fspotcloud.client.useraction.application;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.application.handler.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class ApplicationLateBinder {

    private final ApplicationActions applicationActions;


    @Inject
    ApplicationLateBinder(ConfigBuilder configBuilder,
                          HideControlsHandler hideControlsHandler,
                          TreeFocusHandler treeFocusHandler,
                          DemoFactory demoFactory,
                          ReloadTreeHandler reloadTreeHandler,
                          GoToLatestHandler goToLatestHandler,
                          ApplicationActions actions) {
        this.applicationActions = actions;
        configBuilder.bindHandler(applicationActions.hide_controls, hideControlsHandler);
        configBuilder.bindHandler(applicationActions.tree_focus, treeFocusHandler);
        configBuilder.bindHandler(applicationActions.demo, demoFactory.getDemo());
        configBuilder.bindHandler(applicationActions.reloadTree, reloadTreeHandler);
        configBuilder.bindHandler(applicationActions.goToLatest, goToLatestHandler);
    }
}
