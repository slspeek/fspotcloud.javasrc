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

import com.google.code.ginmvp.client.GinMvpDisplay;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.event.ZoomViewEventHandlerImpl;
import com.googlecode.fspotcloud.client.main.ui.StylesSetup;
import com.googlecode.fspotcloud.client.useraction.UserActionHandlerBinder;

import java.util.logging.Logger;


public class MVPSetup {
    private final Logger log = Logger.getLogger(MVPSetup.class.getName());
    private final GinMvpDisplay appWidget;
    private final StylesSetup stylesSetup;
    private final PlaceHistoryHandler placeHistoryHandler;

    @Inject
    public MVPSetup(ZoomViewEventHandlerImpl zoomViewEventHandler,
                    StylesSetup stylesSetup,
                    GinMvpDisplay appWidget,
                    PlaceHistoryHandler placeHistoryHandler,
                    UserActionHandlerBinder userActionHandlerBinder) {
        this.appWidget = appWidget;
        this.stylesSetup = stylesSetup;
        this.placeHistoryHandler = placeHistoryHandler;
        zoomViewEventHandler.init();
    }

    public void setup() {
        log.info("Starting MVP setup");
        stylesSetup.injectStyles();
        RootLayoutPanel.get().add(appWidget);
        placeHistoryHandler.handleCurrentHistory();
        log.info("MVP setup finished");
    }
}
