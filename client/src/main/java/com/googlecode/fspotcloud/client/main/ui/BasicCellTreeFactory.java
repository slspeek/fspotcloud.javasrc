package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.TreeViewModel;
import com.google.inject.Inject;

public class BasicCellTreeFactory implements CellTreeFactory {

	private final BasicTreeResources resources;

	@Inject
	public BasicCellTreeFactory(BasicTreeResources resources) {
		this.resources = resources;
	}

	@Override
	public CellTree get(TreeViewModel model) {
		return new CellTree(model, null, resources);
	}
}
