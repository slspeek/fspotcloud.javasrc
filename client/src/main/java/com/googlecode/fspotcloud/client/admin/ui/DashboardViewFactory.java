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

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.googlecode.fspotcloud.client.admin.view.TagDetailsActivityMapper;
import com.googlecode.fspotcloud.client.admin.view.api.DashboardView;
import com.googlecode.fspotcloud.client.main.gin.Raw;
import com.googlecode.fspotcloud.client.main.view.MainWindowActivityMapper;
import com.googlecode.fspotcloud.client.place.MainPlaceHistoryMapper;

import javax.swing.text.ViewFactory;
import java.util.logging.Logger;


public class DashboardViewFactory implements Provider<DashboardView> {
    private final Logger log = Logger.getLogger(DashboardViewFactory.class.getName());
    private final DashboardView dashboardView;
    private final EventBus eventBus;

    @Inject
    public DashboardViewFactory(EventBus eventBus, @Raw DashboardView dashboardView
                                ) {
        this.eventBus = eventBus;
        this.dashboardView = dashboardView;
        //register(detailsMapper, dashboardView.getTagDetailsContainer());
        log.info("after register ");
    }

    public DashboardView get() {
        return dashboardView;
    }

    public void register(ActivityMapper mapper, HasOneWidget display) {
        ActivityManager manager = new ActivityManager(mapper, eventBus);
        manager.setDisplay(display);
        log.info("registered: " + mapper);
    }
}
