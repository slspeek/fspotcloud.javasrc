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

package com.googlecode.fspotcloud.client.main.event;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.fspotcloud.client.place.api.IRasterer;

@GwtCompatible
public class ZoomViewEventHandlerImpl implements ZoomViewEvent.Handler {

    private final EventBus eventBus;
    private final IRasterer rasterer;

    @Inject
    public ZoomViewEventHandlerImpl(EventBus eventBus,
                                    IRasterer rasterer
                                    ) {
        super();
        this.eventBus = eventBus;
        this.rasterer = rasterer;
    }

    public void init() {
        eventBus.addHandler(ZoomViewEvent.TYPE, this);
    }

    @Override
    public void onEvent(ZoomViewEvent e) {
        rasterer.toggleZoomView(e.getTagId(), e.getPhotoId());

    }
}
