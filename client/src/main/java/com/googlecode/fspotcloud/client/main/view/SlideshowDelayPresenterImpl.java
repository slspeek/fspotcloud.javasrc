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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.event.SlideshowStatusEvent;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowDelayView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SlideshowDelayPresenterImpl
		implements
			SlideshowDelayView.SlideshowPresenter,
			SlideshowStatusEvent.Handler {
	private final Logger log = Logger
			.getLogger(SlideshowDelayPresenterImpl.class.getName());
	private final SlideshowDelayView slideshowDelayView;
	private final NumberFormat formatter = NumberFormat.getDecimalFormat();

	@Inject
	public SlideshowDelayPresenterImpl(SlideshowDelayView slideshowDelayView) {
		this.slideshowDelayView = slideshowDelayView;
		log.log(Level.FINEST, "Created");
	}

	public void redraw(float delay, boolean running) {
		slideshowDelayView.setLabelText(String.valueOf((int) delay)
				+ " seconds. ");

		if (running) {
			slideshowDelayView.addStyleRunning();

		} else {
			slideshowDelayView.removeStyleRunning();
		}
	}

	@Override
	public void onEvent(SlideshowStatusEvent e) {
		redraw(e.getDelay(), e.isRunning());
	}

	@Override
	public IsWidget getView() {
		return slideshowDelayView;
	}
}
