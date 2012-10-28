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

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.googlecode.fspotcloud.test.Serialization.testSerialization;
import static org.junit.Assert.assertEquals;

public class PhotoDataTest {
    private static final int VERSION = 17;
    private static final byte[] IMAGE_DATA = new byte[]{0, 1};
    private static final byte[] THUMB_DATA = new byte[]{0};
    private static final String TAG = "TAG";
    private static final Date LONG_TIME_AGO = new Date(10);
    private static final String DESCR = "Story";
    private static final String PHOTO_ID = "1";
    PhotoData data;

    @Before
    public void setUp() throws Exception {
        List<String> tags = newArrayList(TAG);
        data = new PhotoData(PHOTO_ID, DESCR, LONG_TIME_AGO, IMAGE_DATA,
                THUMB_DATA, tags, VERSION);
    }

    @Test
    public void print() {
        System.out.println(data);
    }

    @Test
    public void testSerialize() throws Exception {
        testSerialization(data);
    }

    @Test
    public void testGetPhotoId() {
        assertEquals(PHOTO_ID, data.getPhotoId());
    }

    @Test
    public void testGetDesscription() {
        assertEquals(DESCR, data.getDescription());
    }

    @Test
    public void testGetDate() {
        assertEquals(LONG_TIME_AGO, data.getDate());
    }

    @Test
    public void testGetTagList() {
        assertEquals(TAG, data.getTagList().get(0));
    }

    @Test
    public void testGetThumbData() {
        assertEquals(THUMB_DATA, data.getThumbData());
    }

    @Test
    public void testGetImageData() {
        assertEquals(IMAGE_DATA, data.getImageData());
    }

    @Test
    public void testGetVersion() {
        assertEquals(VERSION, data.getVersion());
    }
}
