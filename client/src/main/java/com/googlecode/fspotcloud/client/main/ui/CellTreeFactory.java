package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.TreeViewModel;

public interface CellTreeFactory {
	CellTree get(TreeViewModel model);
}
