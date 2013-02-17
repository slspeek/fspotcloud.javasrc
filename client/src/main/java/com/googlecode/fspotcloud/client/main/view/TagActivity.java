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
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterView;
import com.googlecode.fspotcloud.client.main.view.api.TagView;

import java.util.logging.Logger;


public class TagActivity extends AbstractActivity implements TagView.TagPresenter {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(TagActivity.class.getName());
    private final TagView tagView;
    private final ImageRasterView.ImageRasterPresenter imageRasterPresenter;
    private final IScheduler scheduler;

    public TagActivity(TagView tagView,
                       ImageRasterView.ImageRasterPresenter imageRasterPresenter,
                       IScheduler scheduler) {
        this.tagView = tagView;
        this.imageRasterPresenter = imageRasterPresenter;
        this.scheduler = scheduler;
    }

    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
        containerWidget.setWidget(tagView);


        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                imageRasterPresenter.init();
            }
        });
        tagView.hideLabelLater(10000);
    }
}
