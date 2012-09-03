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

package com.googlecode.fspotcloud.client.admin;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.ui.AdminResources;
import com.googlecode.fspotcloud.client.admin.view.AdminActivityMapper;
import com.googlecode.fspotcloud.client.admin.view.DashboardPresenterImpl;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.client.main.ui.HasOneWidgetAdapter;
import com.googlecode.fspotcloud.client.place.AdminPlaceHistoryMapper;
import com.googlecode.fspotcloud.client.place.TagPlace;

import java.util.logging.Logger;


public class MVPSetup {
    private final Logger log = Logger.getLogger(MVPSetup.class.getName());
    private final DockLayoutPanel appWidget = new DockLayoutPanel(Style.Unit.PX);
    private final Place defaultPlace = new TagPlace("1");
    private final EventBus eventBus;
    private final PlaceController placeController;
    private final DashboardPresenterImpl dashboardImpl;
    private final ClientLoginManager clientLoginManager;
    private final AdminActivityMapper mapper;

    @Inject
    public MVPSetup(EventBus eventBus, PlaceController placeController,
                    DashboardPresenterImpl dashboardImpl,
                    ClientLoginManager clientLoginManager, AdminActivityMapper mapper,
                    AdminResources adminResources) {
        super();
        this.eventBus = eventBus;
        this.placeController = placeController;
        this.dashboardImpl = dashboardImpl;
        this.clientLoginManager = clientLoginManager;
        this.mapper = mapper;
        adminResources.style().ensureInjected();
    }

    public void setup() {
        log.info("Admin setup");

        ActivityManager activityManager = new ActivityManager(mapper, eventBus);
        activityManager.setDisplay(new HasOneWidgetAdapter(appWidget));

        AdminPlaceHistoryMapper historyMapper = GWT.create(AdminPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        log.info("Just before handleCurrentHistory()");
        historyHandler.handleCurrentHistory();

        dashboardImpl.init();

        //Widget w = dashboardImpl.getView().asWidget();
        RootLayoutPanel.get().add(appWidget);
        log.info("Setup finished");

        //        clientLoginManager.getUserInfoAsync(new GetUserInfo("Dashboard.html"), new AsyncCallback<UserInfo>() {
        //
        //            @Override
        //            public void onFailure(Throwable caught) {
        //            }
        //
        //            @Override
        //            public void onSuccess(UserInfo result) {
        //                if (!result.isAdmin()) {
        //                    Window.Location.replace(result.getLoginUrl());
        //                }
        //            }
        //        });
    }
}
