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

package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;

import java.util.logging.Logger;


class ActionManager implements IModeController {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(ActionManager.class.getName());
    private final ActionImplementationRegister actionImplementationRegister;
    private final KeyboardPreferences keyboardPreferences;

    private String mode;

    public ActionManager(ActionImplementationRegister actionImplementationRegister, KeyboardPreferences keyboardPreferences) {
        this.actionImplementationRegister = actionImplementationRegister;
        this.keyboardPreferences = keyboardPreferences;
    }

    public void init() {
        Event.addNativePreviewHandler(new Preview());
    }

    @Override
    public void setMode(String mode) {
        this.mode = mode;
        log.info("Set mode: " + mode);
    }

    @Override
    public String getMode() {
        return mode;
    }


    class Preview implements NativePreviewHandler {
        public void onPreviewNativeEvent(NativePreviewEvent preview) {
            NativeEvent event = preview.getNativeEvent();
            int keycode = event.getKeyCode();

            if (!event.getType().equalsIgnoreCase("keydown") ||
                    event.getAltKey() ||
                    event.getCtrlKey() ||
                    event.getMetaKey()
                    ) {
                return;
            }

            log.info("Event preview in keypress code: " + keycode);
            String actionId = keyboardPreferences.get(mode, new KeyStroke(event.getShiftKey(), keycode));
            if (mode != null && actionId != null) {
                IActionHandler handler = actionImplementationRegister.getAction(actionId);
                handler.performAction(actionId);
                preview.consume();
            }
        }
    }
}
