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

package com.googlecode.fspotcloud.client.useraction.navigation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Navigator.Direction;
import com.googlecode.fspotcloud.client.place.api.Navigator.Unit;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

import java.util.logging.Logger;


public class NavigationActionHandler implements IActionHandler {
    private final Logger log = Logger.getLogger(NavigationActionHandler.class.getName());
    private final Navigator navigator;

    @Inject
    public NavigationActionHandler(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void performAction(String actionId) {

        log.info("On Navigation Event: " + actionId);

        if (actionId.equals(NavigationActions.BACK)) {
            navigator.goAsync(Direction.BACKWARD, Unit.SINGLE);


        } else if (actionId.equals(NavigationActions.NEXT)) {
            navigator.goAsync(Direction.FORWARD, Unit.SINGLE);


        } else if (actionId.equals(NavigationActions.HOME)) {
            navigator.goAsync(Direction.BACKWARD, Unit.BORDER);


        } else if (actionId.equals(NavigationActions.END)) {
            navigator.goAsync(Direction.FORWARD, Unit.BORDER);


        } else if (actionId.equals(NavigationActions.PAGE_DOWN)) {
            navigator.goAsync(Direction.FORWARD, Unit.PAGE);


        } else if (actionId.equals(NavigationActions.PAGE_UP)) {
            navigator.goAsync(Direction.BACKWARD, Unit.PAGE);


        } else if (actionId.equals(NavigationActions.ROW_DOWN)) {
            navigator.goAsync(Direction.FORWARD, Unit.ROW);


        } else if (actionId.equals(NavigationActions.ROW_UP)) {
            navigator.goAsync(Direction.BACKWARD, Unit.ROW);


        } else {
        }
    }

}
