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
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.gin.BasicTreeView;
import com.googlecode.fspotcloud.client.main.view.api.TreeSelectionHandlerInterface;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import javax.inject.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TreePresenterImpl extends TreePresenterBase {
    private final Logger log = Logger.getLogger(TreePresenterImpl.class.getName());
    private final TreeSelectionHandlerInterface treeSelectionHandler;
    private final IClientLoginManager IClientLoginManager;
    private BasePlace place;

    @Inject
    public TreePresenterImpl(@BasicTreeView TreeView treeView,
                             DataManager dataManager,
                             SingleSelectionModel<TagNode> singleSelectionModel,
                             TreeSelectionHandlerInterface treeSelectionHandler,
                             IClientLoginManager IClientLoginManager) {
        super(treeView, dataManager, singleSelectionModel);
        this.treeSelectionHandler = treeSelectionHandler;
        this.IClientLoginManager = IClientLoginManager;
    }

    public void init() {
        super.init();
        treeSelectionHandler.setSelectionModel(selectionModel);
        loadUserInfo();
    }

    private void loadUserInfo() {
        IClientLoginManager.getUserInfoAsync(
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

    protected void setModel(TagNode root) {
        TagTreeModel treeModel = new TagTreeModel(root, selectionModel,
                new Provider<Cell<TagNode>>() {
                    @Override
                    public Cell<TagNode> get() {
                        return new TagCell();
                    }
                });
        treeView.setTreeModel(treeModel);
        updatePlace();
    }

    protected void requestTagTreeData() {

        dataManager.getTagTree(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                log.warning("Loading of the tree data failed: " + caught);
            }

            @Override
            public void onSuccess(TagNode result) {
                setModel(result);
            }
        });
    }

    public void setPlace(BasePlace place) {
        this.place = place;
        updatePlace();
    }

}
