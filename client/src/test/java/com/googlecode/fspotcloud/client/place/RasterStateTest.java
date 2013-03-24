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

package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(JukitoRunner.class)
public class RasterStateTest {

    @Inject
    private RasterState state;
    final int WIDTH = 3;
    final int HEIGHT = 2;

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
            bind(Integer.class).annotatedWith(RasterWidth.class).toInstance(5);
            bind(Integer.class).annotatedWith(RasterHeight.class).toInstance(4);
        }
    }

    @Test
    public void testSetWidth() {
        state.setColumnCount(WIDTH);
        assertEquals(WIDTH, state.getColumnCount());
    }

    @Test
    public void testSetHeight() {
        state.setRowCount(HEIGHT);
        assertEquals(HEIGHT, state.getRowCount());
    }

    @Test
    public void testResetHeightOne() {
        testSetHeight();
        testSetWidth();
        state.setRowCount(1);
        assertEquals(1, state.getRowCount());
    }

    @Test
    public void testResetWidthOne() {
        testSetHeight();
        testSetWidth();
        state.setColumnCount(1);
        assertEquals(1, state.getColumnCount());
    }

    @Test
    public void testSetWidthOne() {
        state.setColumnCount(1);
        assertEquals(1, state.getColumnCount());
    }

    @Test
    public void testTryOneByOne() {
        state.setColumnCount(1);
        assertEquals(1, state.getColumnCount());
        state.setRowCount(1);
        assertEquals(state.rasterHeight, state.getRowCount());
    }

    @Test
    public void testAutoHide() throws Exception {
        state.setAutoHide(true);
        assertTrue(state.isAutoHide());
        state.setAutoHide(false);
        assertFalse(state.isAutoHide());

    }
}
