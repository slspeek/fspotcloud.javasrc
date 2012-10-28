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

/**
 *
 */
package com.googlecode.fspotcloud.peer.db;

import com.google.common.collect.ImmutableList;
import com.googlecode.fspotcloud.shared.peer.TagData;
import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;


public class DataTest extends TestCase {
    private final Logger log = Logger.getLogger(DataTest.class.getName());
    private Data data;

    protected void setUp() throws Exception {
        super.setUp();

        URL testDatabase = ClassLoader.getSystemResource("photos.db");
        System.clearProperty("photo.dir.original");
        System.clearProperty("photo.dir.override");

        String path = testDatabase.getPath();
        data = new Data("jdbc:sqlite:" + path);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        data = null;
    }

    public void testGetPhotoCount() throws SQLException {
        int count = data.getCount("photos");
        assertEquals(28, count);
    }

    public void testGetTagCount() throws SQLException {
        int count = data.getCount("tags");
        assertEquals(5, count);
    }

    public final void testGetTagList2() throws SQLException {
        List<TagData> result = data.getTagData(ImmutableList.of("1"));
        assertEquals(1, result.size());
    }

    public void testGetPhotoKeysForTag() throws Exception {
        List<String> result = data.getPhotoKeysInTag("5");
        assertEquals("3", result.get(0));
    }

    public final void testGetImageURL()
            throws MalformedURLException, SQLException {
        String url = data.getImageURL("20");
        assertEquals("file:///home/steven/Photos/2010/06/22/img_0859-1.jpg",
                String.valueOf(url));
    }

    public void testIsPhotoInTag() throws Exception {
        boolean yep = data.isPhotoInTag("5", "3");
        assertTrue(yep);

        boolean nope = data.isPhotoInTag("5", "4");
        assertFalse(nope);
    }

    public void testPhotoVersion() throws Exception {
        assertEquals(1, data.getPhotoDefaultVersion("3"));
    }
}
