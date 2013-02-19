package com.googlecode.fspotcloud.client.useraction.dashboard;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class DashboardActions {
    public final ActionUIDef reloadTree;
    public final ActionUIDef focusTree;
    public final ActionUIDef toPhotos;
    public final ActionUIDef manageUserGroups;
    public final ActionUIDef synchronize;
    public final ActionUIDef deleteAll;
    public final ActionUIDef deleteCommands;
    public final ActionUIDef importTag;
    public final ActionUIDef manageAccess;


    @Inject
    public DashboardActions() {
        reloadTree = new ActionUIDef("reload-admin-tree",
                "Reload tree",
                "Reload tree data from the server",
                null);
        toPhotos = new ActionUIDef("to-photos", "To photos", "Go to the photos screen", null);
        manageUserGroups = new ActionUIDef("manage-usergroups", "Manage usergroups", "Manage usergroups", null);
        synchronize = new ActionUIDef("synchronize", "Synchronize", "Synchronize with the peer", null);
        deleteCommands = new ActionUIDef("clear-queue", "Clear queue", "Delete all pending commands", null);
        deleteAll = new ActionUIDef("remove-all", "Remove all", "Remove all imported data", null);
        importTag = new ActionUIDef("import-tag", "Import tag", "Import this tag", null);
        manageAccess = new ActionUIDef("manage-access", "Manage access", "Grant this label to usergroups", null);
        focusTree = new ActionUIDef("focus-admin-tree", "Focus tree", "Place keyboard focus on the category tree", null);
    }
}
