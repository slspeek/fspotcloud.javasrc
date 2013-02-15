package com.googlecode.fspotcloud.client.useraction.dashboard;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class DashboardActions {
    public final ActionDef reloadTree;

    @Inject
    public DashboardActions() {

        reloadTree = new ActionDef("reload-admin-tree",
                "Reload tree",
                "Reload tree data from the server",
                null);
    }
}
