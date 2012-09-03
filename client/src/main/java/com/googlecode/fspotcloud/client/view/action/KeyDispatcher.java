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

import com.googlecode.fspotcloud.client.view.action.api.ShortcutHandler;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class KeyDispatcher implements ShortcutHandler {
    private final Logger log = Logger.getLogger(KeyDispatcher.class.getName());
    Map<Integer, Runnable> registeredActions = new HashMap<Integer, Runnable>();

    public void register(UserAction shortcut) {
        int[] keys;

        if (shortcut.getAlternateKey() != null) {
            keys = new int[2];
            keys[1] = shortcut.getAlternateKey().getKeyCode();
        } else {
            keys = new int[1];
        }

        keys[0] = shortcut.getKey().getKeyCode();

        for (int key : keys) {
            registeredActions.put(key, shortcut);
        }
    }

    @Override
    public boolean handle(int keycode) {
        log.info("Checking code: " + keycode);

        Runnable action = registeredActions.get(keycode);

        if (action != null) {
            log.info("Taking action on code: " + keycode);
            action.run();

            return true;
        }

        return false;
    }
}
