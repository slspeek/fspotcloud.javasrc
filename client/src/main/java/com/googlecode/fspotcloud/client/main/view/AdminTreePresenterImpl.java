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

package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.main.TagNode;

import javax.inject.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminTreePresenterImpl implements TreeView.TreePresenter,
        Handler {

    private final Logger log = Logger.getLogger(AdminTreePresenterImpl.class.getName());
    private final TreeView treeView;
    private final DataManager dataManager;
    private final SingleSelectionModel<TagNode> selectionModel;
    private final PlaceGoTo placeGoTo;
    private final Resources resources;

    private TagPlace place;
    private Runnable openSelectedNode;

    @Inject
    public AdminTreePresenterImpl(@AdminTreeView TreeView treeView,
                                  DataManager dataManager,
                                  SingleSelectionModel<TagNode> selectionModel,
                                  PlaceGoTo placeGoTo,
                                  Resources resources) {
        super();
        this.treeView = treeView;
        this.dataManager = dataManager;
        this.selectionModel = selectionModel;
        this.placeGoTo = placeGoTo;
        this.resources = resources;
    }

    public void init() {
        log.info("init");
        reloadTree();
        selectionModel.addSelectionChangeHandler(this);
    }

    private void setModel(TagNode root) {
        TagTreeModel treeModel = new TagTreeModel(root, selectionModel,
                new Provider<Cell<TagNode>>() {
                    @Override
                    public Cell<TagNode> get() {
                        return new AdminTagCell();
                    }
                });
        treeView.setTreeModel(treeModel);
        if (openSelectedNode != null) {
            openSelectedNode.run();
            openSelectedNode = null;
        }
    }

    private void requestTagTreeData() {
        dataManager.getAdminTagTree(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.WARNING, "getAdminTagTree", caught);
            }

            @Override
            public void onSuccess(TagNode result) {
                setModel(result);
            }
        });
    }

    @Override
    public void setPlace(BasePlace place) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reloadTree() {
        requestTagTreeData();
    }

    private void openSelectedTreeNode(TreeNode node) {
        try {
            for (int i = 0; i < node.getChildCount(); i++) {
                TreeNode child = node.setChildOpen(i, true, false);

                if (child != null) {
                    openSelectedTreeNode(child);
                }
            }
        } catch (Exception e) {
            log.log(Level.INFO, "openTreeNode", e);
        }
    }

    public void setPlace(TagPlace place) {
        this.place = place;
        updatePlace();
    }

    private void updatePlace() {
        if (place != null) {
            TagNode node = new TagNode();
            String tagId = place.getTagId();
            node.setId(tagId);
            selectionModel.setSelected(node, true);

            TreeNode root = treeView.getRootNode();

            if (root != null) {
                openSelectedTreeNode(root);
            } else {
                openSelectedNode = new Runnable() {
                    @Override
                    public void run() {
                        updatePlace();
                    }
                };
            }
        } else {
            log.warning("place is null");
        }
    }
    @Override
    public void onSelectionChange(SelectionChangeEvent event) {
        log.info("Selection event from tree" + selectionModel);

        TagNode node = selectionModel.getSelectedObject();

        if (node != null) {
            String tagId = node.getId();
            log.info("About to go");
            placeGoTo.goTo(new TagPlace(tagId));
        }
    }
}
