package com.googlecode.fspotcloud.client.useraction.group;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUsersView;
import com.googlecode.fspotcloud.client.useraction.group.handler.*;
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
                        ManageUsersView.ManageUsersPresenter manageUsersPresenter,
                        UsergroupActions actions) {
        this.actions = actions;
        configBuilder.bindHandler(actions.manageUsers, goManageUsers);
        configBuilder.bindHandler(actions.deleteUsergroup, deleteUsergroupHandler);
        configBuilder.bindHandler(actions.editUsergroup, goEditUsergroup);
        configBuilder.bindHandler(actions.newUsergroup, newUsergroupHandler);
        configBuilder.bindHandler(actions.focusGroupTable, focusUsergroupTableHandler);
        configBuilder.bindHandler(actions.saveGroup, saveGroupHandler);
        configBuilder.bindHandler(actions.removeUser, manageUsersPresenter);
        configBuilder.bindHandler(actions.addUser, manageUsersPresenter);
        configBuilder.bindHandler(actions.focusEmailField, manageUsersPresenter);
        configBuilder.bindHandler(actions.focusUserTable, manageUsersPresenter);
    }

}
