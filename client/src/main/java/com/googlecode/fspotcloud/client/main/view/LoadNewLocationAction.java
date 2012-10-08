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

package com.googlecode.fspotcloud.client.main.view;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.view.api.LoadNewLocation;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;


public class LoadNewLocationAction implements Runnable, IActionHandler {
    private final LoadNewLocation loader;
    private final String newLocation;

    @Inject
    public LoadNewLocationAction(LoadNewLocation loader,
                                 @Assisted
                                 String newLocation) {
        super();
        this.loader = loader;
        this.newLocation = newLocation;
    }

    @Override
    public void run() {
        loader.setLocation(newLocation);
    }

    @Override
    public void performAction(String actionId) {
        run();
    }
}
