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

package com.googlecode.fspotcloud.client.main.shared;

import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowEvent;
import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowType;
import junit.framework.TestCase;


public class SlideshowEventTest extends TestCase {
    public void testSlideshowEvent() {
        SlideshowEvent event = new SlideshowEvent(SlideshowType.SLIDESHOW_START);
        assertNotNull(event);
    }

    public void testGetActionType() {
        SlideshowEvent event = new SlideshowEvent(SlideshowType.SLIDESHOW_START);
        assertEquals(SlideshowType.SLIDESHOW_START, event.getActionDef());
        event = new SlideshowEvent(SlideshowType.SLIDESHOW__END);
        assertEquals(SlideshowType.SLIDESHOW__END, event.getActionDef());
    }
}
