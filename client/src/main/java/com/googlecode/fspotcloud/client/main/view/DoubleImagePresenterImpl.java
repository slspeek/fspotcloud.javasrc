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

package com.googlecode.fspotcloud.client.main.view;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.DoubleImageView;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.place.SlideshowPlace;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Slideshow;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class DoubleImagePresenterImpl implements DoubleImageView.ImagePresenter, AsyncCallback<List<PhotoInfo>> {
    private final Logger log = Logger.getLogger(DoubleImagePresenterImpl.class.getName());
    private final DoubleImageView imageView;
    private final Navigator navigator;
    private final Slideshow slideshow;
    private final IScheduler scheduler;
    private PhotoInfo info;
    private SlideshowPlace currentPlace;
    private SlideshowPlace previousPlace;

    @Inject
    public DoubleImagePresenterImpl(DoubleImageView imageView,
                                    Navigator navigator,
                                    Slideshow slideshow,
                                    IScheduler scheduler) {
        this.imageView = imageView;
        this.navigator = navigator;
        this.slideshow = slideshow;
        this.scheduler = scheduler;
    }

    public void init() {
        imageView.setPresenter(this);
    }

    public void setImage() {
        imageView.removeAnimationStyles();
        String url = getUrl(currentPlace);
        imageView.setImageUrl(url);
        if (previousPlace != null) {
            url = getUrl(previousPlace);
            imageView.setPreviousImageUrl(url);
        }
        imageView.adjustSize();
        imageView.addAnimationStyles();
    }

    private String getUrl(SlideshowPlace place) {
        if (place != null) {
            return "image?id=" + place.getPhotoId();
        } else {
            return "";
        }
    }

    private void setInformation() {
        String date = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL)
                .format(info.getDate()) + " " +
                DateTimeFormat.getFormat(PredefinedFormat.TIME_MEDIUM)
                        .format(info.getDate()) + "(v" +
                info.getVersion() + ")";
        imageView.setDescription(date);
    }

    @Override
    public void imageClicked() {
        slideshow.togglePause();
    }

    public void setCurrentPlace(SlideshowPlace currentPlace) {
        this.previousPlace = this.currentPlace;
        this.currentPlace = currentPlace;
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                setImage();
            }
        });
        navigator.getPageAsync(currentPlace.getTagId(), currentPlace.getPhotoId(), 1, this);
    }

    @Override
    public void onFailure(Throwable caught) {
        log.log(Level.WARNING, "Error during information for photo lookup", caught);
    }

    @Override
    public void onSuccess(List<PhotoInfo> result) {
        info = result.get(0);
        setInformation();
    }
}
