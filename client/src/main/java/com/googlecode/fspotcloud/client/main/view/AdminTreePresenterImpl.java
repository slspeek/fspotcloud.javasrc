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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.gin.Dashboard;
import com.googlecode.fspotcloud.client.main.view.api.ITreeSelectionHandler;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminTreePresenterImpl extends TreePresenterBase implements TreeView.TreePresenter {

    private final Logger log = Logger.getLogger(AdminTreePresenterImpl.class.getName());
    private final IPlaceController IPlaceController;
    private final StatusView statusView;

    @Inject
    public AdminTreePresenterImpl(@AdminTreeView TreeView treeView,
                                  DataManager dataManager,
                                  SingleSelectionModelExt selectionModel,
                                  IPlaceController IPlaceController,
                                  @AdminTreeView ITreeSelectionHandler treeSelectionHandler,
                                  @Dashboard StatusView statusView) {
        super(treeView, dataManager, selectionModel, treeSelectionHandler);
        this.IPlaceController = IPlaceController;
        this.statusView = statusView;
    }


    public void onSelectionChange2(SelectionChangeEvent event) {
        log.log(Level.FINE, "Selection event from admin tree" + event);

        TagNode node = selectionModel.getSelectedObject();

        if (node != null) {
            String tagId = node.getId();
            log.log(Level.FINEST, "About to go");
            IPlaceController.goTo(new TagPlace(tagId));
        }
    }

    public void setPlace(TagPlace place) {
        log.log(Level.FINE, "Set place with place " + place);
        this.place = place;
        updatePlace();
    }

    protected void requestTagTreeData() {
        statusView.setStatusText("Requesting new category tree from the server");
        dataManager.getAdminTagTree(new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.WARNING, "getAdminTagTree", caught);
                statusView.setStatusText("The tree could not be retrieved due to a server error");
            }

            @Override
            public void onSuccess(TagNode result) {
                statusView.setStatusText("Received new category tree from the server");
                setModel(result);
                statusView.setStatusText("Reloaded tree UI");
            }
        });
    }

    @Override
    public Cell<TagNode> get() {
        return new AdminTagCell();
    }

    @Override
    public void setPlace(BasePlace place) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
