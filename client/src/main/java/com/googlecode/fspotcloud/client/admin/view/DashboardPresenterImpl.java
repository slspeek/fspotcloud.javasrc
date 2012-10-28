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

package com.googlecode.fspotcloud.client.admin.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.ui.DashboardViewFactory;
import com.googlecode.fspotcloud.client.admin.view.api.DashboardView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView.TreePresenter;


public class DashboardPresenterImpl extends AbstractActivity implements com.googlecode.fspotcloud.client.admin.view.api.DashboardView.DashboardPresenter {
    private final DashboardViewFactory dashboardViewFactory;
    private final TreeView.TreePresenter treePresenter;
    private final GlobalActionsPresenter globalActionsPresenter;

    @Inject
    public DashboardPresenterImpl(DashboardViewFactory dashboardViewFactory,
                                  TreePresenter treePresenter,
                                  GlobalActionsPresenter globalActionsPresenter) {
        super();
        this.dashboardViewFactory = dashboardViewFactory;
        this.treePresenter = treePresenter;
        this.globalActionsPresenter = globalActionsPresenter;
    }

    public DashboardView getView() {
        return dashboardViewFactory.get();
    }

    @Override
    public void init() {
        treePresenter.init();
        globalActionsPresenter.init();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(getView());
    }
}
