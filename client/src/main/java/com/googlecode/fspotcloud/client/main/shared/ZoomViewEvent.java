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

package com.googlecode.fspotcloud.client.main.shared;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;


public class ZoomViewEvent extends GwtEvent<ZoomViewEvent.Handler> {
    public static final Type<ZoomViewEvent.Handler> TYPE = new Type<ZoomViewEvent.Handler>();
    private final String photoId;
    private final String tagId;

    public ZoomViewEvent(String tagId, String photoId) {
        this.tagId = tagId;
        this.photoId = photoId;
    }

    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(Handler handler) {
        handler.onEvent(this);
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getTagId() {
        return tagId;
    }

    public static interface Handler extends EventHandler {
        void onEvent(ZoomViewEvent e);
    }
}
