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

package com.googlecode.fspotcloud.shared.dashboard;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetMetaDataResultTest {
    GetMetaDataResult result;

    @Before
    public void setUp() throws Exception {
        result = new GetMetaDataResult();
    }

    @Test
    public void testPhotoCount() {
        final int count = 100000;
        result.setPhotoCount(count);
        assertEquals(count, result.getPhotoCount());
    }

    @Test
    public void testPhotosLastCounted() {
        final Date lastCounted = new Date(10000);
        result.setPhotosLastCounted(lastCounted);
        assertEquals(lastCounted, result.getPhotosLastCounted());
    }

    @Test
    public void testGetCreated() {
        assertNotNull(result.getCreated());
    }

    @Test
    public void testPeerPhotoCount() {
        final int peerCount = 100000;
        result.setPeerPhotoCount(peerCount);
        assertEquals(peerCount, result.getPeerPhotoCount());
    }

    @Test
    public void testTagCount() {
        final int tagCount = 1000;
        result.setTagCount(tagCount);
        assertEquals(tagCount, result.getTagCount());
    }

    @Test
    public void testPeerLastSeen() {
        final Date peerLastSeen = new Date(0);
        result.setPeerLastSeen(peerLastSeen);
        assertEquals(peerLastSeen, result.getPeerLastSeen());
    }

    @Test
    public void testGetInstanceName() {
        final String INSTANCE = "FOO";
        result.setInstanceName(INSTANCE);
        assertEquals(INSTANCE, result.getInstanceName());
    }

    @Test
    public void testGetPendingCommandCount() {
        final int PENDING_COUNT = 999;
        result.setPendingCommandCount(PENDING_COUNT);
        assertEquals(PENDING_COUNT, result.getPendingCommandCount());
    }
}
