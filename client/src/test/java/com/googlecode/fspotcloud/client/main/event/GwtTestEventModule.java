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

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.fspotcloud.client.main.gin.EventGinjector;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;


public class GwtTestEventModule extends GWTTestCase {
    public void testOne() {
        EventGinjector injector = GWT.create(EventGinjector.class);
        ActionFamily family = injector.getAllActions();
        assertNotNull(family);

        assertEquals(8, family.get("Navigation").allActions().size());

        for (UserAction action : family.get("Slideshow").allActions()) {
            assertNotNull(action.getIcon());
        }
    }

    @Override
    public String getModuleName() {
        return "com.googlecode.fspotcloud.FSpotCloud";
    }
}
