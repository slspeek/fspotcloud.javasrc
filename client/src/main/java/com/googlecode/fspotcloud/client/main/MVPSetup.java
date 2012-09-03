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

package com.googlecode.fspotcloud.client.main;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.ui.AdminResources;
import com.googlecode.fspotcloud.client.main.api.Initializable;
import com.googlecode.fspotcloud.client.main.ui.HasOneWidgetAdapter;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.ui.UserPagesResources;
import com.googlecode.fspotcloud.client.main.view.MainWindowActivityMapper;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.MainPlaceHistoryMapper;
import com.googlecode.fspotcloud.client.view.action.api.IGlobalShortcutController;

import java.util.logging.Logger;


public class MVPSetup {
    private final Logger log = Logger.getLogger(MVPSetup.class.getName());
    private final Place defaultPlace = new BasePlace("latest", "latest", 1, 1);
    private final DockLayoutPanel appWidget = new DockLayoutPanel(Unit.PX);
    private final EventBus eventBus;
    private final MainWindowActivityMapper activityMapper;
    private final PlaceController placeController;
    private final Initializable keyboardHandler;
    private final EventHandlersSetup eventSetup;
    private final ClientLoginManager clientLoginManager;

    @Inject
    public MVPSetup(MainWindowActivityMapper activityMapper, EventBus eventBus,
                    PlaceController placeController,
                    IGlobalShortcutController keyboardHandler,
                    EventHandlersSetup eventSetup, Resources resources,
                    AdminResources adminResources, ClientLoginManager clientLoginManager,
                    UserPagesResources userPagesResources) {
        this.activityMapper = activityMapper;
        this.eventBus = eventBus;
        this.placeController = placeController;
        this.keyboardHandler = keyboardHandler;
        this.eventSetup = eventSetup;
        this.clientLoginManager = clientLoginManager;
        resources.style().ensureInjected();
        adminResources.style().ensureInjected();
        userPagesResources.style().ensureInjected();
    }

    public void setup() {
        keyboardHandler.init();
        log.info("Starting MVP setup");
        eventSetup.setUp();

        ActivityManager activityManager = new ActivityManager(activityMapper,
                eventBus);
        activityManager.setDisplay(new HasOneWidgetAdapter(appWidget));

        MainPlaceHistoryMapper historyMapper = GWT.create(MainPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        log.info("Just before handleCurrentHistory()");
        RootLayoutPanel.get().add(appWidget);
        historyHandler.handleCurrentHistory();
        log.info("Setup finished");
        clientLoginManager.getAsync();
    }
}
