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
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.ui.DashboardViewFactory;
import com.googlecode.fspotcloud.client.admin.view.api.DashboardView;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsActivityFactory;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsView;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.gin.Finished;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView.TreePresenter;
import com.googlecode.fspotcloud.client.place.TagPlace;


public class DashboardPresenterImpl extends AbstractActivity
        implements com.googlecode.fspotcloud.client.admin.view.api.DashboardView.DashboardPresenter {
    private final DashboardView dashboardView;
    private final TreeView.TreePresenter treePresenter;
    private final GlobalActionsPresenter globalActionsPresenter;
    private final TagDetailsActivityFactory tagDetailsActivityFactory;


    @Inject
    public DashboardPresenterImpl(DashboardView dashboardView,
                                  @AdminTreeView TreePresenter treePresenter,
                                  GlobalActionsPresenter globalActionsPresenter,
                                  TagDetailsActivityFactory tagDetailsActivityFactory) {
        super();
        this.dashboardView = dashboardView;
        this.treePresenter = treePresenter;
        this.globalActionsPresenter = globalActionsPresenter;
        this.tagDetailsActivityFactory = tagDetailsActivityFactory;
        init();
    }

    @Override
    public void init() {
        treePresenter.init();
        globalActionsPresenter.init();
    }

    @Override
    public DashboardView.DashboardPresenter withPlace(TagPlace place) {
        TagDetailsView tagDetailsView = dashboardView.getTagDetailsView();
        TagDetailsView.TagDetailsPresenter activity = tagDetailsActivityFactory.get(place);
        //tagDetailsView.setPresenter(activity);
        activity.init();
        return this;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(dashboardView);
    }
}
