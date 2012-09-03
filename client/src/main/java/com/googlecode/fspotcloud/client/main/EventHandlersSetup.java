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

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.api.Initializable;
import com.googlecode.fspotcloud.client.view.action.*;


public class EventHandlersSetup {
    private final Initializable slideshow;
    private final Initializable navigation;
    private final Initializable application;
    private final Initializable raster;
    private final Initializable about;
    private final Initializable zoomViewEventHandler;

    @Inject
    public EventHandlersSetup(SlideshowEventHandler slideshow,
                              NavigationEventHandler navigation, ApplicationEventHandler application,
                              AboutEventHandler about, RasterEventHandler raster,
                              ZoomViewEventHandler zoomViewEventHandler) {
        super();
        this.about = about;
        this.slideshow = slideshow;
        this.navigation = navigation;
        this.application = application;
        this.raster = raster;
        this.zoomViewEventHandler = zoomViewEventHandler;
    }

    public void setUp() {
        about.init();
        slideshow.init();
        navigation.init();
        application.init();
        raster.init();
        zoomViewEventHandler.init();
    }
}
