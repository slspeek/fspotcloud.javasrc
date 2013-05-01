package com.googlecode.fspotcloud.client.enduseraction.dashboard;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class DashboardActions {

    private final Resources RESOURCES;
    public final ActionUIDef adminHelp;
    public final ActionUIDef reloadTree;
    public final ActionUIDef focusTree;
    public final ActionUIDef toPhotos;
    public final ActionUIDef manageGroups;
    public final ActionUIDef synchronize;
    public final ActionUIDef deleteAll;
    public final ActionUIDef deleteCommands;
    public final ActionUIDef importTag;
    public final ActionUIDef manageAccess;


    @Inject
    public DashboardActions(Resources resources) {
        RESOURCES = resources;
        reloadTree = new ActionUIDef("reload-admin-tree",
                "Reload tree",
                "Reload tree data from the server", RESOURCES.reloadTreeIcon());
        toPhotos = new ActionUIDef("to-photos", "To photos", "Go to the photos screen");
        manageGroups = new ActionUIDef("manage-usergroups", "Manage groups", "Manage groups");
        synchronize = new ActionUIDef("synchronize", "Synchronize", "Synchronize with the peer");
        deleteCommands = new ActionUIDef("clear-queue", "Clear queue", "Delete all pending commands");
        deleteAll = new ActionUIDef("remove-all", "Remove all", "Remove all imported data");
        importTag = new ActionUIDef("import-tag", "Import tag", "Import this tag");
        manageAccess = new ActionUIDef("manage-access", "Manage access", "Grant or revoke access ");
        focusTree = new ActionUIDef("focus-admin-tree", "Focus tree", "Place keyboard focus on the category tree");
        adminHelp = new ActionUIDef("admin-help", "Help", "Dashboard help", RESOURCES.helpIcon());
    }
}
