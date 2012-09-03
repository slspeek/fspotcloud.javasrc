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
import com.googlecode.fspotcloud.client.main.ui.SingleImageViewImpl;
import com.googlecode.fspotcloud.client.main.view.SingleImageActivity;
import com.googlecode.fspotcloud.client.main.view.SlideshowControlsPresenter;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterPresenterFactory;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterView;
import com.googlecode.fspotcloud.client.main.view.api.SingleImageView.SingleImagePresenter;
import com.googlecode.fspotcloud.client.main.view.api.SingleViewActivityFactory;
import com.googlecode.fspotcloud.client.place.BasePlace;


public class SingleImageViewActivityFactoryImpl
        implements SingleViewActivityFactory {
    private final ImageRasterPresenterFactory imageRasterPresenterFactory;
    private final SingleImageViewImpl singleImageView;
    @SuppressWarnings("unused")
    private final SlideshowControlsPresenter controlsPresenter;

    @Inject
    public SingleImageViewActivityFactoryImpl(
            ImageRasterPresenterFactory imageRasterPresenterFactory,
            SingleImageViewImpl singleImageView,
            SlideshowControlsPresenter controlsPresenter) {
        super();
        this.controlsPresenter = controlsPresenter;
        this.imageRasterPresenterFactory = imageRasterPresenterFactory;
        this.singleImageView = singleImageView;
    }

    @Override
    public SingleImagePresenter get(BasePlace place) {
        ImageRasterView.ImageRasterPresenter raster = imageRasterPresenterFactory.get(place,
                singleImageView.getImageRasterView());

        return new SingleImageActivity(singleImageView, raster);
    }
}
