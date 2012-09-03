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
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.googlecode.fspotcloud.client.main.event.raster.RasterEvent;
import com.googlecode.fspotcloud.client.main.event.raster.RasterType;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;


public class RasterEventHandlerTest extends TestCase {
    Mockery context;
    Navigator navigator;
    RasterEventHandler handler;
    EventBus eventBus;
    GwtEvent event;

    @Override
    protected void setUp() throws Exception {
        eventBus = new SimpleEventBus();
        context = new Mockery();
        navigator = context.mock(Navigator.class);
        handler = new RasterEventHandler(navigator, eventBus, null, null);
        handler.init();
        super.setUp();
    }

    public void testToggleTabularview() {
        event = new RasterEvent(RasterType.TOGGLE_TABULAR_VIEW);
        context.checking(new Expectations() {

            {
                oneOf(navigator).toggleRasterView();
            }
        });
        eventBus.fireEvent(event);
        context.assertIsSatisfied();
    }

    public void testDescreaseRasterHeight() {
        event = new RasterEvent(RasterType.REMOVE_ROW);
        context.checking(new Expectations() {

            {
                oneOf(navigator).increaseRasterHeight(with(-1));
            }
        });
        eventBus.fireEvent(event);
        context.assertIsSatisfied();
    }

    public void testIncreaseRasterHeight() {
        event = new RasterEvent(RasterType.ADD_ROW);
        context.checking(new Expectations() {

            {
                oneOf(navigator).increaseRasterHeight(with(1));
            }
        });
        eventBus.fireEvent(event);
        context.assertIsSatisfied();
    }

    public void testDescreaseRasterWidth() {
        event = new RasterEvent(RasterType.REMOVE_COLUMN);
        context.checking(new Expectations() {

            {
                oneOf(navigator).increaseRasterWidth(with(-1));
            }
        });
        eventBus.fireEvent(event);
        context.assertIsSatisfied();
    }

    public void testIncreaseRasterWidth() {
        event = new RasterEvent(RasterType.ADD_COLUMN);
        context.checking(new Expectations() {

            {
                oneOf(navigator).increaseRasterWidth(with(1));
            }
        });
        eventBus.fireEvent(event);
        context.assertIsSatisfied();
    }

    public void testSetRasterXxY(final int x, final int y,
                                 final RasterType eventType) {
        event = new RasterEvent(eventType);
        context.checking(new Expectations() {

            {
                oneOf(navigator).setRasterDimension(with(x), with(x));
            }
        });
        eventBus.fireEvent(event);
        context.assertIsSatisfied();
    }

    public void testSetRaster2x2() {
        testSetRasterXxY(2, 2, RasterType.SET_RASTER_2x2);
    }

    public void testSetRaster3x3() {
        testSetRasterXxY(3, 3, RasterType.SET_RASTER_3x3);
    }

    public void testSetRaster4x4() {
        testSetRasterXxY(4, 4, RasterType.SET_RASTER_4x4);
    }

    public void testSetRaster5x5() {
        testSetRasterXxY(5, 5, RasterType.SET_RASTER_5x5);
    }

    public void testResetRaster() {
        event = new RasterEvent(RasterType.SET_DEFAULT_RASTER);
        context.checking(new Expectations() {

            {
                oneOf(navigator).resetRasterSize();
            }
        });
        eventBus.fireEvent(event);
        context.assertIsSatisfied();
    }
}
