package com.googlecode.fspotcloud.client.useraction.usergroup;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class UsergroupActions {
    public final ActionUIDef editUsergroup;
    public final ActionUIDef newUsergroup;
    public final ActionUIDef deleteUsergroup;
    public final ActionUIDef manageUsers;
    public final ActionUIDef usergroupTableFocus;

    @Inject
    public UsergroupActions() {
        editUsergroup = new ActionUIDef("edit-usergroup", "Edit", "Edit usergroup", null);
        newUsergroup = new ActionUIDef("new-usergroup", "New usergroup", "Create new usergroup", null);
        deleteUsergroup = new ActionUIDef("delete-usergroup", "Delete", "Delete usergroup", null);
        manageUsers = new ActionUIDef("manage-users", "Manage users", "Manage user in selected usergroup", null);
        usergroupTableFocus = new ActionUIDef("usergroup-table-focus", "Focus table", "Focus the usergroup table", null);
    }
}
