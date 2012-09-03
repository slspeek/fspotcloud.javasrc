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

package com.googlecode.fspotcloud.client.view.action;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.api.Initializable;
import com.googlecode.fspotcloud.client.main.event.UserEvent;
import com.googlecode.fspotcloud.client.main.event.UserEventHandler;
import com.googlecode.fspotcloud.client.main.event.about.AboutEvent;
import com.googlecode.fspotcloud.client.main.event.about.AboutType;
import com.googlecode.fspotcloud.client.view.action.api.LoadNewLocationActionFactory;

import java.util.logging.Logger;


public class AboutEventHandler implements AboutEvent.Handler, Initializable {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(AboutEventHandler.class.getName());
    private final LoadNewLocationActionFactory locationFactory;
    private Runnable projectHostingAction;
    private Runnable fspotAction;
    private Runnable mavenAction;
    private Runnable protonAction;
    private Runnable licenseAction;
    private Runnable stevenAction;
    private final EventBus eventBus;

    @Inject
    public AboutEventHandler(LoadNewLocationActionFactory locationFactory,
                             EventBus eventBus) {
        super();
        this.locationFactory = locationFactory;
        this.eventBus = eventBus;
    }

    @Override
    public void onEvent(UserEvent<? extends UserEventHandler> e) {
        log.info("On application event of type " + e.getActionDef());

        switch ((AboutType) e.getActionDef()) {
            case PROJECT_HOSTING:
                projectHostingAction.run();

                break;

            case F_SPOT:
                fspotAction.run();

                break;

            case MAVEN:
                mavenAction.run();

                break;

            case PROTON:
                protonAction.run();

                break;

            case LICENSE:
                licenseAction.run();

                break;

            case STEVEN:
                stevenAction.run();

                break;

            default:
                break;
        }
    }

    public void init() {
        initLocationActions();
        eventBus.addHandler(AboutEvent.TYPE, this);
    }

    private void initLocationActions() {
        projectHostingAction = locationFactory.get(
                "http://code.google.com/p/fspotcloud");
        fspotAction = locationFactory.get("http://f-spot.org/");
        mavenAction = locationFactory.get(
                "http://slspeek.github.com/FSpotCloudSite/");
        licenseAction = locationFactory.get(
                "http://slspeek.github.com/FSpotCloudSite/license.html");
        protonAction = locationFactory.get(
                "http://www.protonradio.com/player/live/player.php");
        stevenAction = locationFactory.get("http://profiles.google.com/slspeek");
    }
}
