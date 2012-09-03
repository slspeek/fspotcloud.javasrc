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
import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowEvent;
import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowType;
import com.googlecode.fspotcloud.client.place.api.Slideshow;


public class SlideshowEventHandler implements SlideshowEvent.Handler,
        Initializable {
    private final Slideshow slideshow;
    private final EventBus eventBus;

    @Inject
    public SlideshowEventHandler(Slideshow slideshow, EventBus eventBus) {
        this.slideshow = slideshow;
        this.eventBus = eventBus;
    }

    @Override
    public void onEvent(UserEvent<?> e) {
        switch ((SlideshowType) e.getActionDef()) {
            case SLIDESHOW_START:
                slideshow.start();

                break;

            case SLIDESHOW__END:
                slideshow.stop();

                break;

            case SLIDESHOW_PAUSE:
                slideshow.pause();

                break;

            case SLIDESHOW_FASTER:
                slideshow.faster();

                break;

            case SLIDESHOW_SLOWER:
                slideshow.slower();

                break;

            default:
                break;
        }
    }

    public void init() {
        eventBus.addHandler(SlideshowEvent.TYPE, this);
    }
}
