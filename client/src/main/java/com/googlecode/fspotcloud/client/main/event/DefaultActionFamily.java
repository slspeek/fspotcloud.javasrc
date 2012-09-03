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

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.fspotcloud.client.view.action.api.ActionGroup;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class DefaultActionFamily extends AbstractActionFamily {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(DefaultActionFamily.class.getName());
    List<ActionGroup> allGroups;
    List<UserAction> allActions = new ArrayList<UserAction>();

    @Inject
    public DefaultActionFamily(
            @Named("application")
            AbstractActionMap application,
            @Named("navigation")
            AbstractActionMap navigation,
            @Named("slideshow")
            AbstractActionMap slideshow,
            @Named("raster")
            AbstractActionMap raster,
            @Named("about")
            AbstractActionMap about) {
        super("All action groups");
        addMap(application);
        addMap(about);
        addMap(raster);
        addMap(navigation);
        addMap(slideshow);
        initAllActions();
    }

    private void initAllActions() {
        for (ActionMap group : allMaps()) {
            allActions.addAll(group.allActions());
        }
    }
}
