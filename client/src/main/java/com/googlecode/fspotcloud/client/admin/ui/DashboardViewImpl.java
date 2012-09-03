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

package com.googlecode.fspotcloud.client.admin.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.view.api.DashboardView;
import com.googlecode.fspotcloud.client.admin.view.api.GlobalActionsView;
import com.googlecode.fspotcloud.client.main.ui.TreeViewImpl;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;


public class DashboardViewImpl extends Composite implements DashboardView {
    private static DashboardViewImplUiBinder uiBinder = GWT.create(DashboardViewImplUiBinder.class);
    @UiField
    SimplePanel tagDetailsViewContainer;
    GlobalActionsView globalActionsView;
    TreeView treeView;
    @UiField
    PushButton toPhotos;
    @UiField
    PushButton manageGroups;

    @Inject
    public DashboardViewImpl(TreeView treeView,
                             GlobalActionsView globalActionsView) {
        this.treeView = treeView;
        this.globalActionsView = globalActionsView;
        initWidget(uiBinder.createAndBindUi(this));
        toPhotos.ensureDebugId("to-photos-button");
        manageGroups.ensureDebugId("manage-groups-button");
    }

    @UiFactory
    public GlobalActionsViewImpl getGlobalActionsView() {
        return (GlobalActionsViewImpl) globalActionsView;
    }

    @Override
    public HasOneWidget getTagDetailsContainer() {
        return tagDetailsViewContainer;
    }

    @UiFactory
    public TreeViewImpl getTreeView() {
        return (TreeViewImpl) treeView;
    }

    @UiHandler("toPhotos")
    public void toPhotosClicked(ClickEvent e) {
        Window.Location.replace("FSpotCloud.html");
    }

    @UiHandler("manageGroups")
    public void manageGroupsClicked(ClickEvent e) {
        Window.Location.replace("FSpotCloud.html#MyUserGroupsPlace:");
    }

    interface DashboardViewImplUiBinder extends UiBinder<Widget, DashboardViewImpl> {
    }
}
