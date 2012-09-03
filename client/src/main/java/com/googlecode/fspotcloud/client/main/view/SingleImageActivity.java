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
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterView;
import com.googlecode.fspotcloud.client.main.view.api.SingleImageView;

import java.util.logging.Logger;


public class SingleImageActivity extends AbstractActivity implements SingleImageView.SingleImagePresenter {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(SingleImageActivity.class.getName());
    private final SingleImageView singleImageView;
    private final ImageRasterView.ImageRasterPresenter imageRasterPresenter;
    private EventBus eventBus;

    public SingleImageActivity(SingleImageView imageView,
                               ImageRasterView.ImageRasterPresenter imageRasterPresenter) {
        this.singleImageView = imageView;
        this.imageRasterPresenter = imageRasterPresenter;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        singleImageView.setPresenter(this);
        this.eventBus = eventBus;
        panel.setWidget(singleImageView);

        Scheduler scheduler = Scheduler.get();
        scheduler.scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                imageRasterPresenter.init();
            }
        });
        singleImageView.hideControlsLater(3000);
    }
}
