package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class ReloadTree implements IActionHandler {

    @Inject
    @AdminTreeView
    TreeView.TreePresenter treePresenter;

    @Override
    public void performAction(String actionId) {
        treePresenter.reloadTree();
    }
}
