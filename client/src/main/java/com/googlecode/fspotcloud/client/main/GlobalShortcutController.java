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

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.googlecode.fspotcloud.client.main.api.Initializable;
import com.googlecode.fspotcloud.client.view.action.api.IGlobalShortcutController;
import com.googlecode.fspotcloud.client.view.action.api.ShortcutHandler;

import java.util.Map;
import java.util.logging.Logger;


public class GlobalShortcutController implements Initializable,
        IGlobalShortcutController {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(GlobalShortcutController.class.getName());
    private final Map<Mode, ShortcutHandler> handler;
    private Mode mode;

    public GlobalShortcutController(Map<Mode, ShortcutHandler> handler) {
        this.handler = handler;
    }

    public void init() {
        Event.addNativePreviewHandler(new Preview());
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
        log.info("Set mode: " + mode);
    }

    class Preview implements NativePreviewHandler {
        public void onPreviewNativeEvent(NativePreviewEvent preview) {
            NativeEvent event = preview.getNativeEvent();
            int keycode = event.getKeyCode();

            if (!event.getType().equalsIgnoreCase("keydown") ||
                    event.getAltKey() ||
                    event.getCtrlKey() ||
                    event.getMetaKey() ||
                    event.getShiftKey()) {
                return;
            }

            // log.info("Event preview in keypress code: " + keycode);
            if (mode != null && handler.get(mode).handle(keycode)) {
                preview.consume();
            }
        }
    }
}
