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

import com.google.common.base.Objects;
import com.google.web.bindery.event.shared.Event;

import java.util.logging.Logger;


public class KeyboardActionEvent extends Event<IKeyboardActionHandler> {
    private final Logger log = Logger.getLogger(KeyboardActionEvent.class.getName());
    public final Type<IKeyboardActionHandler> TYPE = new Type<IKeyboardActionHandler>();
    private String actionId;

    public KeyboardActionEvent(String actionId) {
        this.actionId = actionId;
    }

    @Override
    public Type<IKeyboardActionHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(IKeyboardActionHandler handlerI) {
        log.info("in dispatch");
        handlerI.onEvent(this);
    }

    public boolean equals(Object o) {
        if (o instanceof KeyboardActionEvent) {
            KeyboardActionEvent other = (KeyboardActionEvent) o;
            return Objects.equal(other.actionId, actionId);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(actionId);
    }
}
