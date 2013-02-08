package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.TreeViewModel;

public class BasicCellTreeExt extends CellTree {

    public interface TreeResources extends CellTree.Resources {
        @Source("basiccelltree.css")
        public CellTree.Style cellTreeStyle();
    }


    private static final TreeResources resources = getResource();

    private static TreeResources getResource() {
        TreeResources treeResources = GWT.create(TreeResources.class);
        treeResources.cellTreeStyle().ensureInjected();
        return treeResources;
    }

    public <T> BasicCellTreeExt(TreeViewModel viewModel, T rootValue) {
        super(viewModel, rootValue, resources);
    }
}
