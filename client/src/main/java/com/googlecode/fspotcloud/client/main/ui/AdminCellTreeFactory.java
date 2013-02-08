package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.TreeViewModel;

public class AdminCellTreeFactory implements CellTreeFactory {
    @Override
    public CellTree get(TreeViewModel model) {
        return new AdminCellTreeExt(model, null);
    }
}
