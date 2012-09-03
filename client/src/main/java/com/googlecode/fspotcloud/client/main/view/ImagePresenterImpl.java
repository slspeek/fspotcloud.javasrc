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

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.shared.ZoomViewEvent;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;

import java.util.logging.Logger;


public class ImagePresenterImpl implements ImageView.ImagePresenter {
    private final Logger log = Logger.getLogger(ImagePresenterImpl.class.getName());
    private final ImageView imageView;
    private final String tagId;
    private final String photoId;
    private final boolean thumb;
    private final EventBus eventBus;
    private final PhotoInfo info;
    @SuppressWarnings("unused")
    private final Resources resources;

    @Inject
    public ImagePresenterImpl(@Assisted
                              String tagId, @Assisted
    ImageView imageView, @Assisted
    boolean thumb, @Assisted
    PhotoInfo info, EventBus eventBus, Resources resources) {
        this.tagId = tagId;
        this.resources = resources;
        photoId = info.getId();
        this.imageView = imageView;
        this.thumb = thumb;
        this.eventBus = eventBus;
        this.info = info;
    }

    public void init() {
        imageView.setPresenter(this);
        setImage();
        imageView.hideLabelLater(4000);
    }

    public void setImage() {
        if (photoId != null) {
            String date;

            if (thumb) {
                date = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM)
                        .format(info.getDate());
            } else {
                date = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL)
                        .format(info.getDate()) + " " +
                        DateTimeFormat.getFormat(PredefinedFormat.TIME_MEDIUM)
                                .format(info.getDate()) + "(v" +
                        info.getVersion() + ")";
            }

            imageView.setDescription(date);

            String versionSuffix = "";

            if (info.getVersion() != 1) {
                versionSuffix = "&version=" + info.getVersion();
            }

            String url = "image?id=" + photoId + versionSuffix;
            url += (thumb ? "&thumb=true" : "");
            imageView.setImageUrl(url);
            imageView.adjustSize();
        } else {
            log.warning("No photoId defined for tagId:  " + tagId);
        }
    }

    @Override
    public void imageClicked() {
        log.info("about to fire zoom event");
        eventBus.fireEvent(new ZoomViewEvent(tagId, photoId));
    }

    @Override
    public void setSelected(boolean selected) {
        imageView.setSelected(selected);
    }
}
