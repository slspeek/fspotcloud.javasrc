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

package com.googlecode.fspotcloud.client.main.view.factory;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.event.SlideshowStatusEvent;
import com.googlecode.fspotcloud.client.main.view.SlideshowDelayPresenterImpl;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowDelayView;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowDelayView.SlideshowPresenter;
import com.googlecode.fspotcloud.client.place.api.Slideshow;


public class SlideshowDelayPresenterFactoryImpl implements Provider<SlideshowPresenter> {
    private final SlideshowDelayView delayView;
    private final Slideshow slideshow;
    private final EventBus eventBus;

    @Inject
    public SlideshowDelayPresenterFactoryImpl(SlideshowDelayView delayView,
                                              Slideshow slideshow, EventBus eventBus) {
        super();
        this.delayView = delayView;
        this.slideshow = slideshow;
        this.eventBus = eventBus;
    }

    @Override
    public SlideshowPresenter get() {
        SlideshowDelayPresenterImpl delayPresenter = new SlideshowDelayPresenterImpl(delayView);
        eventBus.addHandler(SlideshowStatusEvent.TYPE, delayPresenter);
        delayPresenter.redraw(slideshow.delay(), slideshow.isRunning());

        return delayPresenter;
    }
}
