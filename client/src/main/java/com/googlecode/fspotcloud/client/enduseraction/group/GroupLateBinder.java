package com.googlecode.fspotcloud.client.enduseraction.group;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUsersView;
import com.googlecode.fspotcloud.client.main.view.api.TagApprovalView;
import com.googlecode.fspotcloud.client.enduseraction.group.handler.*;
import com.googlecode.fspotcloud.keyboardaction.ConfigBuilder;

@GwtCompatible
public class GroupLateBinder {

    private final GroupActions actions;

    @Inject
    GroupLateBinder(ConfigBuilder configBuilder,
                    NewUsergroupHandler newUsergroupHandler,
                    DeleteUsergroupHandler deleteUsergroupHandler,
                    GoEditUsergroup goEditUsergroup,
                    GoManageUsers goManageUsers,
                    FocusUsergroupTableHandler focusUsergroupTableHandler,
                    SaveGroupHandler saveGroupHandler,
                    ManageUsersView.ManageUsersPresenter manageUsersPresenter,
                    TagApprovalView.TagApprovalPresenter tagApprovalPresenter,
                    GroupActions actions) {
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
        configBuilder.bindHandler(actions.grantGroup, tagApprovalPresenter);
        configBuilder.bindHandler(actions.revokeGroup, tagApprovalPresenter);
        configBuilder.bindHandler(actions.focusGrantedTable, tagApprovalPresenter);
        configBuilder.bindHandler(actions.focusRevokedTable, tagApprovalPresenter);
    }

}