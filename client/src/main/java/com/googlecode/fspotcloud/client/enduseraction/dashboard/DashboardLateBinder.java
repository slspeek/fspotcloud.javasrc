package com.googlecode.fspotcloud.client.enduseraction.dashboard;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.handler.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

public class DashboardLateBinder {

    private final DashboardActions actions;


    @Inject
    DashboardLateBinder(ConfigBuilder configBuilder,
                        ReloadTree reloadTree,
                        ToPhotos toPhotos,
                        GoManageGroupsHandler goManageGroupsHandler,
                        RemoveAllCommandsHandler removeAllCommandsHandler,
                        SynchronizeHandler synchronizeHandler,
                        RemoveAllHandler removeAllHandler,
                        ManageAccessHandler manageAccessHandler,
                        ImportTagHandler importTagHandler,
                        FocusTreeHandler focusTreeHandler,
                        DashboardActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.reloadTree, reloadTree);
        configBuilder.bindHandler(actions.manageGroups, goManageGroupsHandler);
        configBuilder.bindHandler(actions.toPhotos, toPhotos);
        configBuilder.bindHandler(actions.deleteCommands, removeAllCommandsHandler);
        configBuilder.bindHandler(actions.synchronize, synchronizeHandler);
        configBuilder.bindHandler(actions.deleteAll, removeAllHandler);
        configBuilder.bindHandler(actions.manageAccess, manageAccessHandler);
        configBuilder.bindHandler(actions.importTag, importTagHandler);
        configBuilder.bindHandler(actions.focusTree, focusTreeHandler);

    }

}