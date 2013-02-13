package com.googlecode.fspotcloud.client.main.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;

public class BasicTreeViewProvider implements Provider<TreeView> {

    @Inject
    BasicCellTreeFactory basicCellTreeFactory;


    @Override
    public TreeView get() {
        return new TreeViewImpl(basicCellTreeFactory);
    }
}
