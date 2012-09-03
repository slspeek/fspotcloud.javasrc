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

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.api.Initializable;
import com.googlecode.fspotcloud.client.main.event.UserEvent;
import com.googlecode.fspotcloud.client.main.event.navigation.NavigationEvent;
import com.googlecode.fspotcloud.client.main.event.navigation.NavigationType;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Navigator.Direction;
import com.googlecode.fspotcloud.client.place.api.Navigator.Unit;

import java.util.logging.Logger;


public class NavigationEventHandler implements NavigationEvent.Handler,
        Initializable {
    private final Logger log = Logger.getLogger(NavigationEventHandler.class.getName());
    private final Navigator navigator;
    private final EventBus eventBus;

    @Inject
    public NavigationEventHandler(Navigator navigator, EventBus eventBus) {
        this.navigator = navigator;
        this.eventBus = eventBus;
    }

    @Override
    public void onEvent(UserEvent e) {
        log.info("On Naviagtion Event: " + e.getActionDef());

        switch ((NavigationType) e.getActionDef()) {
            case BACK:
                navigator.goAsync(Direction.BACKWARD, Unit.SINGLE);

                break;

            case NEXT:
                navigator.goAsync(Direction.FORWARD, Unit.SINGLE);

                break;

            case HOME:
                navigator.goAsync(Direction.BACKWARD, Unit.BORDER);

                break;

            case END:
                navigator.goAsync(Direction.FORWARD, Unit.BORDER);

                break;

            case PAGE_DOWN:
                navigator.goAsync(Direction.FORWARD, Unit.PAGE);

                break;

            case PAGE_UP:
                navigator.goAsync(Direction.BACKWARD, Unit.PAGE);

                break;

            case ROW_DOWN:
                navigator.goAsync(Direction.FORWARD, Unit.ROW);

                break;

            case ROW_UP:
                navigator.goAsync(Direction.BACKWARD, Unit.ROW);

                break;

            default:
                break;
        }
    }

    public void init() {
        eventBus.addHandler(NavigationEvent.TYPE, this);
    }
}
