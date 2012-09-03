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

package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.TreeViewModel;
import com.googlecode.fspotcloud.client.main.view.CustomCellTree;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;

import java.util.logging.Logger;


public class TreeViewImpl extends ResizeComposite implements TreeView {
    private final Logger log = Logger.getLogger(TreeViewImpl.class.getName());
    private static TreeViewImplUiBinder uiBinder = GWT.create(TreeViewImplUiBinder.class);
    CellTree cellTree;
    @UiField
    ScrollPanel tagTreeViewPanel;
    @UiField
    Label userInfoLabel;

    public TreeViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        userInfoLabel.ensureDebugId("user-info-label");
        log.info("Created.");
    }

    @Override
    public void setTreeModel(TreeViewModel model) {
        cellTree = new CustomCellTree(model, null);
        tagTreeViewPanel.setWidget(cellTree);
    }

    @Override
    public TreeNode getRootNode() {
        if (cellTree != null) {
            return cellTree.getRootTreeNode();
        } else {
            log.warning("cellTree is null");

            return null;
        }
    }

    public void requestFocus() {
        if (cellTree != null) {
            cellTree.setFocus(true);
        }
    }

    @Override
    public void setUserInfo(String info) {
        userInfoLabel.setText(info);
    }

    interface TreeViewImplUiBinder extends UiBinder<Widget, TreeViewImpl> {
    }
}
