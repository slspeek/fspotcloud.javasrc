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

package com.googlecode.fspotcloud.client.main.event.slideshow;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.event.AbstractActionMap;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;
import com.googlecode.fspotcloud.client.view.action.api.UserActionFactory;


public class SlideshowMapBuilder extends AbstractActionMap {
    private Resources resources;

    @Inject
    public SlideshowMapBuilder(UserActionFactory userActionFactory,
                               Resources resources) {
        super(userActionFactory, "Slideshow");
        this.resources = resources;
    }

    public void buildMap() {
        put(SlideshowType.SLIDESHOW_START, resources.playIcon());
        put(SlideshowType.SLIDESHOW_PAUSE, resources.pauseIcon());
        put(SlideshowType.SLIDESHOW__END, resources.stopIcon());
        put(SlideshowType.SLIDESHOW_SLOWER, resources.slowerIcon());
        put(SlideshowType.SLIDESHOW_FASTER, resources.fasterIcon());
    }

    private void put(ActionDef actionDef, ImageResource icon) {
        put(actionDef, icon, new SlideshowEventProvider(actionDef));
    }
}
