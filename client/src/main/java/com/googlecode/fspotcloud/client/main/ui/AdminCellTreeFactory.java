package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.TreeViewModel;
import com.google.inject.Inject;

public class AdminCellTreeFactory implements CellTreeFactory {

    private final AdminTreeResources resources;

    @Inject
    public AdminCellTreeFactory(AdminTreeResources resources) {
        this.resources = resources;
    }

    @Override
    public CellTree get(TreeViewModel model) {
        return new CellTree(model, null, resources);
    }
}
