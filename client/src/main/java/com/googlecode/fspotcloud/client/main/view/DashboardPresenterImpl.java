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

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.HomePlace;
import com.googlecode.fspotcloud.client.place.MyUserGroupsPlace;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.logging.Logger;


public class DashboardPresenterImpl extends AbstractActivity
        implements DashboardView.DashboardPresenter {
    private final Logger log = Logger.getLogger(DashboardPresenterImpl.class.getName());
    private final DashboardView dashboardView;
    private final TreeView.TreePresenter treePresenter;
    private final GlobalActionsView.GlobalActionsPresenter globalActionsPresenter;
    private final TagDetailsActivityFactory tagDetailsActivityFactory;
    private TagDetailsView.TagDetailsPresenter activity;
    private final IClientLoginManager clientLoginManager;
    private final DataManager dataManager;
    private final PlaceGoTo placeGoTo;
    private final PlaceWhere placeWhere;


    @Inject
    public DashboardPresenterImpl(DashboardView dashboardView,
                                  @AdminTreeView TreeView.TreePresenter treePresenter,
                                  GlobalActionsView.GlobalActionsPresenter globalActionsPresenter,
                                  TagDetailsActivityFactory tagDetailsActivityFactory,
                                  IClientLoginManager IClientLoginManager,
                                  DataManager dataManager,
                                  PlaceGoTo placeGoTo,
                                  PlaceWhere placeWhere) {
        this.dashboardView = dashboardView;
        this.treePresenter = treePresenter;
        this.globalActionsPresenter = globalActionsPresenter;
        this.tagDetailsActivityFactory = tagDetailsActivityFactory;
        this.clientLoginManager = IClientLoginManager;
        this.dataManager = dataManager;
        this.placeGoTo = placeGoTo;
        this.placeWhere = placeWhere;
    }

    @Override
    public void init() {
        clientLoginManager.getUserInfoAsync(new AsyncCallback<UserInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                placeGoTo.goTo(new HomePlace());
            }

            @Override
            public void onSuccess(UserInfo result) {
                if (result.isAdmin()) {
                    treePresenter.init();
                    globalActionsPresenter.init();
                } else if (result.isLoggedIn()) {
                    placeGoTo.goTo(new HomePlace());
                } else {
                    clientLoginManager.redirectToLogin();
                }
            }
        });
    }

    @Override
    public DashboardView.DashboardPresenter withPlace(TagPlace place) {
        ((AdminTreePresenterImpl) treePresenter).setPlace(place);

        activity = tagDetailsActivityFactory.get(place);
        activity.init();
        return this;
    }

    @Override
    public void onToPhotos() {
        final String tagId = placeWhere.getLastTagId();
        dataManager.getAdminTagNode(tagId, new AsyncCallback<TagNode>() {
            @Override
            public void onFailure(Throwable caught) {
                placeGoTo.goTo(new HomePlace());
            }

            @Override
            public void onSuccess(TagNode result) {
                if (result != null && result.isImportIssued()) {
                    placeGoTo.goTo(new BasePlace(tagId, "", 5, 4));
                } else {
                    placeGoTo.goTo(new HomePlace());
                }
            }
        });
    }

    @Override
    public void onManageGroups() {
        placeGoTo.goTo(new MyUserGroupsPlace());
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        dashboardView.setPresenter(this);
        panel.setWidget(dashboardView);
        init();
    }

    @Override
    public void onStop() {
        globalActionsPresenter.stop();
        super.onStop();
    }
}
