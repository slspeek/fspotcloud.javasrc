package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUserGroupsView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class FocusUsergroupTableHandler implements IActionHandler {


    @Inject
    private ManageUserGroupsView view;

    @Override
    public void performAction(String actionId) {
           view.focusTable();

    }
}
