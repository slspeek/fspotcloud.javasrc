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

import com.google.common.annotations.GwtCompatible;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class ActionManager implements IKeyboardActionHandler {
    private final Logger log = Logger.getLogger(ActionManager.class.getName());
    private final ActionHandlerRegistry actionHandlerRegistry;

    ActionManager(ActionHandlerRegistry actionHandlerRegistry
    ) {
        this.actionHandlerRegistry = actionHandlerRegistry;
    }


    @Override
    public void onEvent(KeyboardActionEvent event) {
        log.log(Level.FINEST, "onEvent for: " + event.getActionId());
        log.log(Level.INFO, "onEvent for: " + event.getActionId());
        String actionId = event.getActionId();
        IActionHandler handler = actionHandlerRegistry.getAction(actionId);
        handler.performAction(actionId);
    }
}
