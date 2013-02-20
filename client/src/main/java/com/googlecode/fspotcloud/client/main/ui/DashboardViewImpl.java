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
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.gin.Dashboard;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.useraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.ButtonFactory;

import java.util.logging.Logger;


public class DashboardViewImpl extends Composite implements DashboardView {
    private static final DashboardViewImplUiBinder uiBinder = GWT.create(DashboardViewImplUiBinder.class);
    private static final Logger log = Logger.getLogger(DashboardViewImpl.class.getName());

    private final TagDetailsViewImpl tagDetailsView;
    private final PeerActionsView peerActionsView;
    private final TreeView treeView;
    private DashboardPresenter presenter;
    @UiField(provided = true)
    ActionButton toPhotos;
    @UiField(provided = true)
    ActionButton manageGroups;
    @UiField(provided = true)
    ActionButton reloadTree;
    @UiField(provided = true)
    StatusViewImpl statusView;

    public static int counter;

    @Inject
    public DashboardViewImpl(@AdminTreeView TreeView treeView,
                             PeerActionsView peerActionsView,
                             TagDetailsView tagDetailsView,
                             @Dashboard StatusView statusView,
                             ButtonFactory buttonFactory,
                             AdminActionButtonResources resources,
                             DashboardActions actions
    ) {
        counter++;
        this.statusView = (StatusViewImpl) statusView;
        buttonFactory.setButtonResources(resources);
        toPhotos = buttonFactory.getButton(actions.toPhotos);
        manageGroups = buttonFactory.getButton(actions.manageGroups);
        reloadTree = buttonFactory.getButton(actions.reloadTree);
        this.treeView = treeView;
        this.peerActionsView = peerActionsView;
        this.tagDetailsView = (TagDetailsViewImpl) tagDetailsView;
        initWidget(uiBinder.createAndBindUi(this));
        toPhotos.ensureDebugId("to-photos-button");
        manageGroups.ensureDebugId("manage-groups-button");
        log.info("Dashboard created: " + counter);
    }

    @UiFactory
    public PeerActionsViewImpl getPeerActionsView() {
        return (PeerActionsViewImpl) peerActionsView;
    }

    @UiFactory
    @Override
    public TagDetailsViewImpl getTagDetailsView() {
        log.info("Dashboard getTagDetail..: " + counter);
        return tagDetailsView;
    }

    @Override
    public void setPresenter(DashboardPresenter presenter) {
        this.presenter = presenter;
    }

    @UiFactory
    public TreeViewImpl getTreeView() {
        return (TreeViewImpl) treeView;
    }

    interface DashboardViewImplUiBinder extends UiBinder<Widget, DashboardViewImpl> {
    }
}
