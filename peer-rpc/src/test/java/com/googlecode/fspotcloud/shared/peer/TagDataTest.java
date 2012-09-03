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

package com.googlecode.fspotcloud.shared.peer;

import org.junit.Test;

import static com.googlecode.fspotcloud.test.Serialization.testSerialization;
import static org.junit.Assert.assertEquals;

public class TagDataTest {
    private static final int COUNT = 10;
    private static final String PARENT = "2";
    private static final String NAME = "Tag";
    private static final String TAG_ID = "1";
    TagData tag = new TagData(TAG_ID, NAME, PARENT, COUNT);

    @Test
    public void testSerialize() throws Exception {
        testSerialization(tag);
    }

    @Test
    public void testId() {
        assertEquals(TAG_ID, tag.getTagId());
    }

    @Test
    public void testParent() {
        assertEquals(PARENT, tag.getParentId());
    }

    @Test
    public void testName() {
        assertEquals(NAME, tag.getName());
    }

    @Test
    public void testCount() {
        assertEquals(COUNT, tag.getCount());
    }
}
