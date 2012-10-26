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
import com.googlecode.fspotcloud.client.main.ui.SlideshowViewImpl;
import com.googlecode.fspotcloud.client.main.view.SingleImagePresenterImpl;
import com.googlecode.fspotcloud.client.main.view.SlideshowActivity;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.SlideshowPlace;


public class SlideshowActivityFactoryImpl
        implements SlideshowActivityFactory {
    private final SingleImagePresenterImpl singleImagePresenter;
    private final SlideshowView slideshowView;
    private SlideshowActivity singleton;

    @Inject
    public SlideshowActivityFactoryImpl(
            SingleImagePresenterImpl singleImagePresenter, SlideshowView slideshowView) {
        super();
        this.singleImagePresenter = singleImagePresenter;
        this.slideshowView = slideshowView;
    }

    @Override
    public SlideshowView.SlideshowPresenter get(SlideshowPlace place) {
        if (singleton == null) {
            singleImagePresenter.init();
            singleton = new SlideshowActivity(slideshowView, singleImagePresenter);
        }
        singleton.setCurrentPlace(place);
        return singleton;
    }
}
