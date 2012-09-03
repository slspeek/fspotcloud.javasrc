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

import junit.framework.TestCase;


public class BasePlaceTest extends TestCase {
    public void testEquals() {
        BasePlace first = new BasePlace("1", "2", 1, 1);
        BasePlace second = new BasePlace("1", "2");
        assertEquals(first, second);
    }

    public void testNotEqualsByColumns() {
        BasePlace first = new BasePlace("1", "2", 1, 1);
        BasePlace second = new BasePlace("1", "2", 2, 1);
        assertNotSame("different ni of columns", first, second);
    }

    public void testNotEqualsByRows() {
        BasePlace first = new BasePlace("1", "2", 2, 1);
        BasePlace second = new BasePlace("1", "2", 1, 1);
        assertNotSame("different ni of columns", first, second);
    }
}
