package com.googlecode.fspotcloud.client.useraction.dashboard;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.application.handler.DemoFactory;
import com.googlecode.fspotcloud.client.useraction.application.handler.HideControlsHandler;
import com.googlecode.fspotcloud.client.useraction.application.handler.TreeFocusHandler;
import com.googlecode.fspotcloud.client.useraction.dashboard.handler.ReloadTree;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

public class DashboardLateBinder  {

    private final DashboardActions actions;


    @Inject
    DashboardLateBinder(ConfigBuilder configBuilder,
                        ReloadTree reloadTree,
                        DashboardActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.reloadTree, reloadTree);
    }
}
