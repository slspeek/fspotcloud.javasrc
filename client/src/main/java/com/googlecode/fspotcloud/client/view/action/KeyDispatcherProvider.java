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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.event.ActionFamily;
import com.googlecode.fspotcloud.client.main.event.ActionMap;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import java.util.logging.Logger;


public class KeyDispatcherProvider implements Provider<KeyDispatcher> {
    private final Logger log = Logger.getLogger(KeyDispatcherProvider.class.getName());
    private final KeyDispatcher keyDispatcher = new KeyDispatcher();
    private final ActionFamily actions;

    @Inject
    public KeyDispatcherProvider(ActionFamily actions) {
        this.actions = actions;
        log.info("created");
        registerShortcuts();
    }

    private void registerShortcuts() {
        for (ActionMap actionMap : actions.allMaps()) {
            for (UserAction action : actionMap.allActions()) {
                if (action != null) {
                    keyDispatcher.register(action);
                } else {
                    log.warning("action was null");
                }
            }
        }
    }

    @Override
    public KeyDispatcher get() {
        return keyDispatcher;
    }
}
