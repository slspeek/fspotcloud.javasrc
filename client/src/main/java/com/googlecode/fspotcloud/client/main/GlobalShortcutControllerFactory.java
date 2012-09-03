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

package com.googlecode.fspotcloud.client.main;

import com.googlecode.fspotcloud.client.view.action.api.IGlobalShortcutController.Mode;
import com.googlecode.fspotcloud.client.main.event.ActionFamily;
import com.googlecode.fspotcloud.client.main.event.ActionMap;
import com.googlecode.fspotcloud.client.view.action.KeyDispatcher;
import com.googlecode.fspotcloud.client.view.action.api.ShortcutHandler;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;


public class GlobalShortcutControllerFactory implements Provider<GlobalShortcutController> {
    final ActionFamily family;
    final Map<Mode, ShortcutHandler> map;
    private final KeyDispatcher slideshowDispatcher = new KeyDispatcher();
    private final KeyDispatcher tagViewDispatcher = new KeyDispatcher();
    private final KeyDispatcher aboutDispatcher = new KeyDispatcher();
    private final KeyDispatcher treeFocusDispatcher = new KeyDispatcher();
    private final KeyDispatcher loginDispatcher = new KeyDispatcher();
    private final GlobalShortcutController controller;

    @Inject
    public GlobalShortcutControllerFactory(ActionFamily family) {
        super();
        this.family = family;
        map = new HashMap<Mode, ShortcutHandler>();
        map.put(Mode.SLIDESHOW, slideshowDispatcher);
        map.put(Mode.ABOUT, aboutDispatcher);
        map.put(Mode.TAG_VIEW, tagViewDispatcher);
        map.put(Mode.TREE_VIEW, treeFocusDispatcher);
        map.put(Mode.LOGIN, loginDispatcher);

        putAll("Slideshow", slideshowDispatcher);
        putAll("Application", slideshowDispatcher);
        putAll("About", slideshowDispatcher);

        putAll("About", aboutDispatcher);
        putAll("Application", aboutDispatcher);

        putAll("Application", treeFocusDispatcher);
        putAll("Raster", treeFocusDispatcher);
        putAll("Slideshow", treeFocusDispatcher);

        putAll("Navigation", tagViewDispatcher);
        putAll("Raster", tagViewDispatcher);
        putAll("About", tagViewDispatcher);
        putAll("Application", tagViewDispatcher);
        putAll("Slideshow", tagViewDispatcher);
        controller = new GlobalShortcutController(map);
    }

    public GlobalShortcutController get() {
        return controller;
    }

    private void putAll(String name, KeyDispatcher dispatcher) {
        ActionMap actions = family.get(name);

        for (UserAction action : actions.allActions()) {
            dispatcher.register(action);
        }
    }
}
