package com.googlecode.fspotcloud.client.main.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

public class BasicTreeViewProvider implements Provider<TreeView> {

    @Inject
    private BasicCellTreeFactory basicCellTreeFactory;
    @Inject
    private IModeController modeController;


    @Override
    public TreeView get() {
        return new TreeViewImpl(basicCellTreeFactory, modeController);
    }
}
