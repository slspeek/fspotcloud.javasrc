package com.googlecode.fspotcloud.client.main.ui;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

@GwtCompatible
public class AdminTreeViewProvider implements Provider<TreeView> {

	@Inject
	private AdminCellTreeFactory adminCellTreeFactory;
	@Inject
	private IModeController modeController;
	@Inject
	private Resources resources;

	@Override
	public TreeView get() {
		return new TreeViewImpl(adminCellTreeFactory, modeController, resources);
	}
}
