package com.googlecode.fspotcloud.client.useraction.group.handler;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.ManageGroups;
import com.googlecode.fspotcloud.client.main.view.api.ManageGroupsView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

@GwtCompatible
public class FocusUsergroupTableHandler implements IActionHandler {


    @Inject
    private ManageGroupsView view;
    @Inject
    @ManageGroups
    StatusView statusView;

    @Override
    public void performAction(String actionId) {
        view.focusTable();
        statusView.setStatusText("Focused the table");
    }
}
