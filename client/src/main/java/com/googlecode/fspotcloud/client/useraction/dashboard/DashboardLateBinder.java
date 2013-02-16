package com.googlecode.fspotcloud.client.useraction.dashboard;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.application.handler.DemoFactory;
import com.googlecode.fspotcloud.client.useraction.application.handler.HideControlsHandler;
import com.googlecode.fspotcloud.client.useraction.application.handler.TreeFocusHandler;
import com.googlecode.fspotcloud.client.useraction.dashboard.handler.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

public class DashboardLateBinder  {

    private final DashboardActions actions;


    @Inject
    DashboardLateBinder(ConfigBuilder configBuilder,
                        ReloadTree reloadTree,
                        ToPhotos toPhotos,
                        ManageUserGroupsHandler manageUserGroupsHandler,
                        RemoveAllCommandsHandler removeAllCommandsHandler,
                        SynchronizeHandler synchronizeHandler,
                        RemoveAllHandler removeAllHandler,
                        ManageAccessHandler manageAccessHandler,
                        ImportTagHandler importTagHandler,
                        DashboardActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.reloadTree, reloadTree);
        configBuilder.bindHandler(actions.manageUserGroups, manageUserGroupsHandler);
        configBuilder.bindHandler(actions.toPhotos, toPhotos);
        configBuilder.bindHandler(actions.deleteCommands, removeAllCommandsHandler);
        configBuilder.bindHandler(actions.synchronize, synchronizeHandler);
        configBuilder.bindHandler(actions.deleteAll, removeAllHandler);
        configBuilder.bindHandler(actions.manageAccess, manageAccessHandler);
        configBuilder.bindHandler(actions.importTag, importTagHandler);
    }
}
