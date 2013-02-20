package com.googlecode.fspotcloud.client.useraction.group;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

@GwtCompatible
public class UsergroupActions {
    public final ActionUIDef editUsergroup;
    public final ActionUIDef newUsergroup;
    public final ActionUIDef deleteUsergroup;
    public final ActionUIDef manageUsers;
    public final ActionUIDef focusGroupTable;
    public final ActionUIDef saveGroup;
    public final ActionUIDef addUser;
    public final ActionUIDef removeUser;
    public final ActionUIDef focusEmailField;
    public final ActionUIDef focusUserTable;

    @Inject
    public UsergroupActions() {
        editUsergroup = new ActionUIDef("edit-usergroup", "Edit", "Edit usergroup", null);
        newUsergroup = new ActionUIDef("new-usergroup", "New usergroup", "Create new usergroup", null);
        deleteUsergroup = new ActionUIDef("delete-usergroup", "Delete", "Delete usergroup", null);
        manageUsers = new ActionUIDef("manage-users", "Manage users", "Manage user in selected usergroup", null);
        focusGroupTable = new ActionUIDef("usergroup-table-focus", "Focus table", "Focus the usergroup table", null);
        saveGroup = new ActionUIDef("save-usergroup", "Save", "Save this usergroup", null);
        addUser = new ActionUIDef("add-user", "Add user", "Add a user to the group", null);
        removeUser = new ActionUIDef("remove-user", "Remove user", "Remove selected user from group", null);
        focusEmailField = new ActionUIDef("focus-email", "Focus email field", "Focus the email field", null);
        focusUserTable = new ActionUIDef("focus-user-table", "Focus user table", "Focus user table", null);
    }
}
