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

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;

import java.util.logging.Logger;


public class UserEvent<H extends UserEventHandler> extends GwtEvent<H> {
    private final Logger log = Logger.getLogger(UserEvent.class.getName());
    public final Type<H> TYPE = new Type<H>();
    private ActionDef actionDef;

    @Inject
    public UserEvent(@Assisted
                     ActionDef actionDef) {
        this.actionDef = actionDef;
    }

    public ActionDef getActionDef() {
        return actionDef;
    }

    @Override
    public Type<H> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(H handler) {
        log.info("in dispatch");
        handler.onEvent(this);
    }

    public boolean equals(Object o) {
        if (o instanceof UserEvent) {
            UserEvent other = (UserEvent) o;

            if (getActionDef() == other.getActionDef()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        return getActionDef().toString().hashCode();
    }
}
