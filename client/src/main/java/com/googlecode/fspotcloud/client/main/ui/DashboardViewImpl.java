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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.view.api.DashboardView;
import com.googlecode.fspotcloud.client.main.view.api.GlobalActionsView;
import com.googlecode.fspotcloud.client.main.view.api.TagDetailsView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;

import java.util.logging.Logger;


public class DashboardViewImpl extends Composite implements DashboardView {
    private static final DashboardViewImplUiBinder uiBinder = GWT.create(DashboardViewImplUiBinder.class);
    private static final Logger log = Logger.getLogger(DashboardViewImpl.class.getName());

    private final TagDetailsViewImpl tagDetailsView;
    private final GlobalActionsView globalActionsView;
    private final TreeView treeView;
    private DashboardPresenter presenter;
    @UiField
    PushButtonExt toPhotos;
    @UiField
    PushButtonExt manageGroups;

    public static int counter;

    @Inject
    public DashboardViewImpl(@AdminTreeView TreeView treeView,
                             GlobalActionsView globalActionsView,
                             TagDetailsView tagDetailsView
    ) {
        counter++;
        this.treeView = treeView;
        this.globalActionsView = globalActionsView;
        this.tagDetailsView = (TagDetailsViewImpl) tagDetailsView;
        initWidget(uiBinder.createAndBindUi(this));
        toPhotos.ensureDebugId("to-photos-button");
        manageGroups.ensureDebugId("manage-groups-button");
        log.info("Dashboard created: " + counter);

    }

    @UiFactory
    public GlobalActionsViewImpl getGlobalActionsView() {
        return (GlobalActionsViewImpl) globalActionsView;
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

    @UiHandler("toPhotos")
    public void toPhotosClicked(ClickEvent e) {
        presenter.onToPhotos();
    }

    @UiHandler("manageGroups")
    public void manageGroupsClicked(ClickEvent e) {
        presenter.onManageGroups();
    }

    interface DashboardViewImplUiBinder extends UiBinder<Widget, DashboardViewImpl> {
    }
}
