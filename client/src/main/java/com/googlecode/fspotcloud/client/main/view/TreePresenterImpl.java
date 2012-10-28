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
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.client.main.view.api.TreeSelectionHandlerInterface;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import javax.inject.Provider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TreePresenterImpl implements TreeView.TreePresenter {
    private final Logger log = Logger.getLogger(TreePresenterImpl.class.getName());
    private final TreeView treeView;
    private final DataManager dataManager;
    private final SingleSelectionModel<TagNode> selectionModel;
    private final TreeSelectionHandlerInterface treeSelectionHandler;
    private final ClientLoginManager clientLoginManager;
    private BasePlace place;

    @Inject
    public TreePresenterImpl(TreeView treeView, DataManager dataManager,
                             SingleSelectionModel<TagNode> singleSelectionModel,
                             TreeSelectionHandlerInterface treeSelectionHandler,
                             ClientLoginManager clientLoginManager) {
        this.treeView = treeView;
        this.dataManager = dataManager;
        this.selectionModel = singleSelectionModel;
        this.treeSelectionHandler = treeSelectionHandler;
        this.clientLoginManager = clientLoginManager;
    }

    public void init() {
        treeSelectionHandler.setSelectionModel(selectionModel);
        reloadTree();
        loadUserInfo();
    }

    private void loadUserInfo() {
        clientLoginManager.getUserInfoAsync(new GetUserInfo(""),
                new AsyncCallback<UserInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(UserInfo result) {
                        String userName = result.getEmail();
                        String info;

                        if (userName != null) {
                            info = "Logged in as: " + userName;
                        } else {
                            info = "Not logged in";
                        }

                        treeView.setUserInfo(info);
                    }
                });
    }

    private void setModel(List<TagNode> roots) {
        TagTreeModel treeModel = new TagTreeModel(roots, selectionModel,
                new Provider<Cell<TagNode>>() {
                    @Override
                    public Cell<TagNode> get() {
                        return new TagCell();
                    }
                });
        treeView.setTreeModel(treeModel);
        updatePlace();
    }

    private void requestTagTreeData() {
        dataManager.getTagTree(new AsyncCallback<List<TagNode>>() {
            @Override
            public void onFailure(Throwable caught) {
                log.warning("Loading of the tree data failed: " + caught);
            }

            @Override
            public void onSuccess(List<TagNode> result) {
                setModel(result);
            }
        });
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
            log.log(Level.SEVERE, "openTreeNode", e);
        }
    }

    public void setPlace(BasePlace place) {
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
                log.warning("Root node is null");
            }
        } else {
            log.warning("place is null");
        }
    }
}
