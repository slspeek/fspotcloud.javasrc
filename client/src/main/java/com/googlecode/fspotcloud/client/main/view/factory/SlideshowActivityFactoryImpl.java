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

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.DoubleImagePresenterImpl;
import com.googlecode.fspotcloud.client.main.view.SlideshowActivity;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.place.SlideshowPlace;


public class SlideshowActivityFactoryImpl
        implements SlideshowActivityFactory {
    private final DoubleImagePresenterImpl doubleImagePresenter;
    private final SlideshowView slideshowView;
    private SlideshowActivity singleton;

    @Inject
    public SlideshowActivityFactoryImpl(
            DoubleImagePresenterImpl doubleImagePresenter, SlideshowView slideshowView) {
        super();
        this.doubleImagePresenter = doubleImagePresenter;
        this.slideshowView = slideshowView;
    }

    @Override
    public SlideshowView.SlideshowPresenter get(SlideshowPlace place) {
        if (singleton == null) {
            doubleImagePresenter.init();
            singleton = new SlideshowActivity(slideshowView, doubleImagePresenter);
        }
        singleton.setCurrentPlace(place);
        return singleton;
    }
}
