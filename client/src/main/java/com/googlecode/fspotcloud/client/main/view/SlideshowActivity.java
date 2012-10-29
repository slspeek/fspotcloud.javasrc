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

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowView;
import com.googlecode.fspotcloud.client.place.SlideshowPlace;

import java.util.logging.Logger;


public class SlideshowActivity extends AbstractActivity
        implements SlideshowView.SlideshowPresenter {

    private final Logger log = Logger.getLogger(SlideshowActivity.class.getName());
    private final SlideshowView slideshowView;
    private final DoubleImagePresenterImpl doubleImagePresenter;

    private SlideshowPlace currentPlace;

    public SlideshowActivity(SlideshowView imageView,
                             DoubleImagePresenterImpl doubleImagePresenter) {
        this.slideshowView = imageView;
        this.doubleImagePresenter = doubleImagePresenter;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        slideshowView.setPresenter(this);
        panel.setWidget(slideshowView);
    }

    public void setCurrentPlace(SlideshowPlace currentPlace) {
        this.currentPlace = currentPlace;
        doubleImagePresenter.setCurrentPlace(currentPlace);
    }
}
