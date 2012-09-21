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
import com.google.common.base.Objects;
import com.google.web.bindery.event.shared.Event;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
class ActionEnableEvent extends Event<IActionEnableHandler> {
    private final Logger log = Logger.getLogger(ActionEnableEvent.class.getName());
    public static final  Type<IActionEnableHandler> TYPE = new Type<IActionEnableHandler>();
    private final String actionId;
    private final boolean state;

    public ActionEnableEvent(String actionId, boolean state) {
        this.actionId = actionId;
        this.state = state;
    }

    public String getActionId() {
        return actionId;
    }

    @Override
    public Type<IActionEnableHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(IActionEnableHandler handlerI) {
        log.log(Level.OFF, "in dispatch for " + this);
        handlerI.onEvent(this);
    }

    public boolean equals(Object o) {
        if (o instanceof ActionEnableEvent) {
            ActionEnableEvent other = (ActionEnableEvent) o;
            return Objects.equal(other.actionId, actionId) && state == other.state;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(actionId, state);
    }

    public boolean getState() {
        return state;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("action", actionId).add("state", state).toString();
    }
}
