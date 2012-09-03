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

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;
import com.googlecode.fspotcloud.client.view.action.api.UserActionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public abstract class AbstractActionMap implements ActionMap {
    protected final UserActionFactory userActionFactory;
    SortedMap<ActionDef, UserAction> actionMap = new TreeMap<ActionDef, UserAction>();
    private final String description;

    public AbstractActionMap(UserActionFactory userActionFactory,
                             String description) {
        this.userActionFactory = userActionFactory;
        this.description = description;
    }

    @Override
    public List<UserAction> allActions() {
        return new ArrayList<UserAction>(actionMap.values());
    }

    @Override
    public UserAction get(ActionDef def) {
        return actionMap.get(def);
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void put(ActionDef actionDef, ImageResource icon,
                    Provider<UserEvent<? extends UserEventHandler>> eventProvider) {
        UserAction action = userActionFactory.get(actionDef.getId(),
                actionDef.getCaption(), actionDef.getDescription(),
                actionDef.getKey(), actionDef.getAlternateKey(), icon,
                eventProvider);
        actionMap.put(actionDef, action);
    }

    public abstract void buildMap();
}
