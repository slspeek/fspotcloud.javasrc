package com.googlecode.fspotcloud.client.useraction.usergroup;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.usergroup.handler.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

@GwtCompatible
public class UsergroupLateBinder {

    private final UsergroupActions actions;

    @Inject
    UsergroupLateBinder(ConfigBuilder configBuilder,
                        NewUsergroupHandler newUsergroupHandler,
                        DeleteUsergroupHandler deleteUsergroupHandler,
                        GoEditUsergroup goEditUsergroup,
                        GoManageUsers goManageUsers,
                        FocusUsergroupTableHandler focusUsergroupTableHandler,
                        SaveGroupHandler saveGroupHandler,
                        UsergroupActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.manageUsers, goManageUsers);
        configBuilder.bindHandler(actions.deleteUsergroup, deleteUsergroupHandler);
        configBuilder.bindHandler(actions.editUsergroup, goEditUsergroup);
        configBuilder.bindHandler(actions.newUsergroup, newUsergroupHandler);
        configBuilder.bindHandler(actions.usergroupTableFocus, focusUsergroupTableHandler);
        configBuilder.bindHandler(actions.saveUsergoup, saveGroupHandler);
    }

}
