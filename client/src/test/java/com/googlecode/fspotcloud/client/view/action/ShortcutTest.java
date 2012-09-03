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

import com.google.gwt.event.dom.client.KeyCodes;
import junit.framework.TestCase;


public class ShortcutTest extends TestCase {
    String id = "debug-id";
    KeyStroke key1 = new KeyStroke('h');
    KeyStroke key2 = new KeyStroke(KeyCodes.KEY_BACKSPACE);
    String caption = "Help";
    String description = "Press h to see help";
    UserActionImpl userActionImpl;

    public void testShortcut() {
        userActionImpl = new UserActionImpl(id, caption, description, key1,
                key2, null, null, null);
        assertNotNull(userActionImpl);
    }

    public void testGetDescription() {
        userActionImpl = new UserActionImpl(id, caption, description, key1,
                key2, null, null, null);
        assertEquals(description, userActionImpl.getDescription());
    }

    public void testGetKey() {
        userActionImpl = new UserActionImpl(id, caption, description, key1,
                key2, null, null, null);
        assertEquals(key1, userActionImpl.getKey());
    }

    public void testGetAlternateKey() {
        userActionImpl = new UserActionImpl(id, caption, description, key1,
                key2, null, null, null);
        assertEquals(key2, userActionImpl.getAlternateKey());
    }

    public void testGetIcon() {
        userActionImpl = new UserActionImpl(id, caption, description, key1,
                key2, null, null, null);
        assertNull(userActionImpl.getIcon());
    }

    public void testGetCaption() {
        userActionImpl = new UserActionImpl(id, caption, description, key1,
                key2, null, null, null);
        assertEquals(caption, userActionImpl.getCaption());
    }

    public void testGetId() {
        userActionImpl = new UserActionImpl(id, caption, description, key1,
                key2, null, null, null);
        assertEquals(id, userActionImpl.getId());
    }
}
