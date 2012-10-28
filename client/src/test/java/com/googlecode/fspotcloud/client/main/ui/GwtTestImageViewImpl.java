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

package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.junit.client.GWTTestCase;

import java.util.logging.Logger;


public class GwtTestImageViewImpl extends GWTTestCase {
    private final Logger log = Logger.getLogger(GwtTestImageViewImpl.class.getName());
    ImageViewImpl imageView;

    public void testConstructor() {
    }

    public void testSetUrl() {
        testConstructor();

        //imageView.setImageUrl("foo");
        //assertEquals("gwt-debug-image-view-0x0", imageView.getElement().getId());
        //assertEquals("foo", imageView.getElement().getAttribute("src"));
    }

    @Override
    public String getModuleName() {
        return "com.googlecode.fspotcloud.FSpotCloud";
    }
}
