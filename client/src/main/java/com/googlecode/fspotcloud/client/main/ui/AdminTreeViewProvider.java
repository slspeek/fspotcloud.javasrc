package com.googlecode.fspotcloud.client.main.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;

public class AdminTreeViewProvider implements Provider<TreeView> {

    @Inject
    AdminCellTreeFactory adminCellTreeFactory;


    @Override
    public TreeView get() {
        return new TreeViewImpl(adminCellTreeFactory);
    }
}
