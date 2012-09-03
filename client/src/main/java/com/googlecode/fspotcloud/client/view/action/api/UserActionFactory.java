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

package com.googlecode.fspotcloud.client.view.action.api;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.event.UserEvent;
import com.googlecode.fspotcloud.client.main.event.UserEventHandler;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;


public interface UserActionFactory {
    UserAction get(@Assisted("id")
                   String id, @Assisted("caption")
    String caption, @Assisted("description")
    String description, @Assisted("key")
    KeyStroke key, @Assisted("altKey")
    KeyStroke alternateKey, @Assisted
    ImageResource imageResource,
                   @Assisted
                   Provider<? extends UserEvent<? extends UserEventHandler>> eventProvider);
}
