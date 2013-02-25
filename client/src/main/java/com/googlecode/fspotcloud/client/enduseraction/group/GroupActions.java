package com.googlecode.fspotcloud.client.enduseraction.group;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

@GwtCompatible
public class GroupActions {
    public final ActionUIDef editUsergroup;
    public final ActionUIDef newUsergroup;
    public final ActionUIDef deleteUsergroup;
    public final ActionUIDef manageUsers;
    public final ActionUIDef focusGroupTable;
    public final ActionUIDef saveGroup;
    public final ActionUIDef addUser;
    public final ActionUIDef removeUser;
    public final ActionUIDef focusEmailField;
    public final ActionUIDef focusGrantedTable;
    public final ActionUIDef focusRevokedTable;
    public final ActionUIDef focusUserTable;
    public final ActionUIDef revokeGroup;
    public final ActionUIDef grantGroup;

    @Inject
    public GroupActions() {
        editUsergroup = new ActionUIDef("edit-usergroup", "Edit", "Edit usergroup");
        newUsergroup = new ActionUIDef("new-usergroup", "New usergroup", "Create new usergroup");
        deleteUsergroup = new ActionUIDef("delete-usergroup", "Delete", "Delete usergroup");
        manageUsers = new ActionUIDef("manage-users", "Manage users", "Manage user in selected usergroup");
        focusGroupTable = new ActionUIDef("usergroup-table-focus", "Focus table", "Focus the usergroup table");
        saveGroup = new ActionUIDef("save-usergroup", "Save", "Save this usergroup");
        addUser = new ActionUIDef("add-user", "Add user", "Add a user to the group");
        removeUser = new ActionUIDef("remove-user", "Remove user", "Remove selected user from group");
        focusEmailField = new ActionUIDef("focus-email", "Focus email field", "Focus the email field");
        focusUserTable = new ActionUIDef("focus-user-table", "Focus user table", "Focus user table");
        revokeGroup = new ActionUIDef("revoke-group", "Revoke group", "Revoke access from group");
        grantGroup = new ActionUIDef("grant-group", "Grant group", "Grant access to group");
        focusGrantedTable = new ActionUIDef("focus-granteds", "Focus granted groups", "Focus granted groups");
        focusRevokedTable = new ActionUIDef("focus-revokeds", "Focus revoked groups", "Focus revoked groups");
    }
}
