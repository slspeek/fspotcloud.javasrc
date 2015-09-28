/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.client.enduseraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.gin.BasicTreeView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;

public class ReloadTreeHandler implements IActionHandler {
	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(ReloadTreeHandler.class
			.getName());
	private final TreeView.TreePresenter treePresenter;
	private final DataManager dataManager;

	@Inject
	public ReloadTreeHandler(
			@BasicTreeView TreeView.TreePresenter treePresenter,
			DataManager dataManager) {
		this.treePresenter = treePresenter;
		this.dataManager = dataManager;
	}

	@Override
	public void performAction(String actionId) {
		dataManager.reset();
		treePresenter.reloadTree();
	}
}
