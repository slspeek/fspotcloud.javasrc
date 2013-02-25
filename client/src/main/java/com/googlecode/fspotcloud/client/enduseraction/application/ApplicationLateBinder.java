package com.googlecode.fspotcloud.client.enduseraction.application;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.application.handler.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;
import com.googlecode.fspotcloud.keyboardaction.HelpActionsFactory;

public class ApplicationLateBinder {

    private final ApplicationActions applicationActions;


    @Inject
    ApplicationLateBinder(ConfigBuilder configBuilder,
                          HideControlsHandler hideControlsHandler,
                          TreeFocusHandler treeFocusHandler,
                          DemoFactory demoFactory,
                          HelpActionsFactory helpActionsFactory,
                          ReloadTreeHandler reloadTreeHandler,
                          GoToLatestHandler goToLatestHandler,
                          ToggleAutoHideHandler toggleAutoHideHandler,
                          ApplicationActions actions) {
        this.applicationActions = actions;
        configBuilder.bindHandler(applicationActions.hide_controls, hideControlsHandler);
        configBuilder.bindHandler(applicationActions.tree_focus, treeFocusHandler);
        configBuilder.bindHandler(applicationActions.demo, demoFactory.getDemo());
        configBuilder.bindHandler(applicationActions.reloadTree, reloadTreeHandler);
        configBuilder.bindHandler(applicationActions.goToLatest, goToLatestHandler);
        configBuilder.bindHandler(applicationActions.show_shortcuts, helpActionsFactory.getShortcutsAction());
        configBuilder.bindHandler(applicationActions.toggleAutoHide, toggleAutoHideHandler);
    }
}
