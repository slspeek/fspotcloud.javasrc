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

package com.googlecode.fspotcloud.client.view.action;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.event.UserEvent;
import com.googlecode.fspotcloud.client.main.event.UserEventHandler;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import java.util.logging.Logger;


public class UserActionImpl implements UserAction {
    private final Logger log = Logger.getLogger(UserActionImpl.class.getName());
    private final String description;
    private final KeyStroke key;
    private final KeyStroke alternateKey;
    private final ImageResource imageResource;
    private final String caption;
    private final String id;
    private final Provider<? extends UserEvent<? extends UserEventHandler>> eventProvider;
    private final EventBus eventBus;

    @Inject
    public UserActionImpl(@Assisted("id")
                          String id, @Assisted("caption")
    String caption, @Assisted("description")
    String description, @Assisted("key")
    KeyStroke key, @Assisted("altKey")
    KeyStroke alternateKey, @Assisted
    ImageResource imageResource,
                          @Assisted
                          Provider<? extends UserEvent<? extends UserEventHandler>> eventProvider,
                          EventBus eventBus) {
        super();
        this.id = id;
        this.caption = caption;
        this.description = description;
        this.key = key;
        this.alternateKey = alternateKey;
        this.imageResource = imageResource;
        this.eventProvider = eventProvider;
        this.eventBus = eventBus;
    }

    public Provider<? extends UserEvent<? extends UserEventHandler>> getEventProvider() {
        return eventProvider;
    }

    public String getDescription() {
        return description;
    }

    public KeyStroke getKey() {
        return key;
    }

    public KeyStroke getAlternateKey() {
        return alternateKey;
    }

    public ImageResource getIcon() {
        return imageResource;
    }

    @Override
    public void run() {
        GwtEvent event = eventProvider.get();
        eventBus.fireEvent(event);
    }

    @Override
    public String getCaption() {
        return caption;
    }

    public String getId() {
        return id;
    }
}
