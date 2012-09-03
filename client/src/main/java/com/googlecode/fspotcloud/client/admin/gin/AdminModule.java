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

package com.googlecode.fspotcloud.client.admin.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.admin.ui.DashboardViewImpl;
import com.googlecode.fspotcloud.client.admin.ui.GlobalActionsViewImpl;
import com.googlecode.fspotcloud.client.admin.ui.TagApprovalViewImpl;
import com.googlecode.fspotcloud.client.admin.ui.TagDetailsViewImpl;
import com.googlecode.fspotcloud.client.admin.view.AdminTreePresenterImpl;
import com.googlecode.fspotcloud.client.admin.view.DashboardPresenterImpl;
import com.googlecode.fspotcloud.client.admin.view.TagApprovalPresenterImpl;
import com.googlecode.fspotcloud.client.admin.view.TagDetailsActivityMapper;
import com.googlecode.fspotcloud.client.admin.view.api.*;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.data.DataManagerImpl;
import com.googlecode.fspotcloud.client.main.gin.PlaceControllerProvider;
import com.googlecode.fspotcloud.client.main.ui.TimerImpl;
import com.googlecode.fspotcloud.client.main.ui.TreeViewImpl;
import com.googlecode.fspotcloud.client.main.view.TagCell;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.PlaceGoToImpl;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;


public class AdminModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(TagDetailsActivityMapper.class).in(Singleton.class);
        bind(TagDetailsActivityFactory.class)
                .to(TagDetailsActivityFactoryImpl.class);
        bind(TagDetailsView.class).to(TagDetailsViewImpl.class)
                .in(Singleton.class);
        bind(DashboardView.class).to(DashboardViewImpl.class).in(Singleton.class);
        bind(DashboardView.DashboardPresenter.class)
                .to(DashboardPresenterImpl.class);
        bind(GlobalActionsView.class).to(GlobalActionsViewImpl.class)
                .in(Singleton.class);
        bind(DataManager.class).to(DataManagerImpl.class).in(Singleton.class);
        bind(TagCell.class);
        bind(PlaceGoTo.class).to(PlaceGoToImpl.class);
        bind(PlaceController.class).toProvider(PlaceControllerProvider.class);
        bind(PlaceControllerProvider.class).in(Singleton.class);
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(TreeView.TreePresenter.class).to(AdminTreePresenterImpl.class)
                .in(Singleton.class);
        bind(TreeView.class).to(TreeViewImpl.class).in(Singleton.class);
        bind(TimerInterface.class).to(TimerImpl.class);

        bind(TagApprovalView.class).to(TagApprovalViewImpl.class);
        bind(TagApprovalView.TagApprovalPresenter.class)
                .to(TagApprovalPresenterImpl.class);
    }
}
