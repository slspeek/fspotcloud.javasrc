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

import com.google.gwt.i18n.client.NumberFormat;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.shared.SlideshowStatusEvent;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowView;

import java.util.logging.Logger;


public class SlideshowPresenterImpl implements SlideshowView.SlideshowPresenter,
        SlideshowStatusEvent.Handler {
    private final Logger log = Logger.getLogger(SlideshowPresenterImpl.class.getName());
    private final SlideshowView slideshowView;
    private final NumberFormat formatter = NumberFormat.getDecimalFormat();

    @Inject
    public SlideshowPresenterImpl(SlideshowView slideshowView) {
        this.slideshowView = slideshowView;
        log.info("Created");
    }

    public void redraw(float delay, boolean running) {
        slideshowView.setLabelText(formatter.format(delay) + " seconds. ");

        if (running) {
            slideshowView.asWidget().addStyleDependentName("running");
        } else {
            slideshowView.asWidget().removeStyleDependentName("running");
        }
    }

    @Override
    public void onEvent(SlideshowStatusEvent e) {
        redraw(e.getDelay(), e.isRunning());
    }
}
