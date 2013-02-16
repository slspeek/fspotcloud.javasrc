package com.googlecode.fspotcloud.client.useraction.dashboard;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class DashboardActions {
    public final ActionDef reloadTree;
    public final ActionDef toPhotos;
    public final ActionDef manageUserGroups;
    public final ActionDef synchronize;
    public final ActionDef deleteAll;
    public final ActionDef deleteCommands;
    public final ActionDef importTag;
    public final ActionDef manageAccess;

    @Inject
    public DashboardActions() {

        reloadTree = new ActionDef("reload-admin-tree",
                "Reload tree",
                "Reload tree data from the server",
                null);
        toPhotos = new ActionDef("to-photos", "To photos", "Go to the photos screen", null);
        manageUserGroups = new ActionDef("manage-usergroups", "Manage usergroups", "Manage usergroups", null);
        synchronize = new ActionDef("synchronize", "Synchronize", "Synchronize with the peer", null);
        deleteCommands = new ActionDef("clear-queue", "Clear queue", "Delete all pending commands", null);
        deleteAll = new ActionDef("remove-all", "Remove all", "Remove all imported data", null);
        importTag = new ActionDef("import-tag", "Import tag", "Import this tag", null);
        manageAccess = new ActionDef("manage-access", "Manage access", "Grant this label to usergroups", null);
    }
}
