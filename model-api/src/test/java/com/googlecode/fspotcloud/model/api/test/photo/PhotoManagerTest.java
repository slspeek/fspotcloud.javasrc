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

package com.googlecode.fspotcloud.model.api.test.photo;

import com.google.common.collect.ImmutableList;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.model.api.test.EmptyGuiceBerryEnv;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PhotoManagerTest {
    public static final String TEST_ID = "1";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    private PhotoDao photoManager;

    @After
    public void cleanUp() {
        photoManager.deleteBulk(100);
    }

    public Date getDate(int year, int month, int date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, date);

        return cal.getTime();
    }

    @Test
    public void simple() {
        Photo photo = photoManager.findOrNew(TEST_ID);

        if (photoManager.find(TEST_ID) != null) {
            fail();
        }

        photoManager.save(photo);

        //photoManager.delete(TEST_ID);
    }

    @Test
    public void simpleDescr() {
        Photo photo = photoManager.findOrNew(TEST_ID);
        photo.setDescription("Test desc");

        if (photoManager.find(TEST_ID) != null) {
            fail();
        }

        photoManager.save(photo);
        photo = null;
        photo = photoManager.findOrNew(TEST_ID);
        assertEquals("Test desc", photo.getDescription());

        //photoManager.delete(TEST_ID);
    }

    @Test
    public void testGetOrNew() {
        Photo photo = photoManager.findOrNew(TEST_ID);
        assertNull(photo.getFullsizeImageBlobKey());
        assertNull(photo.getImageBlobKey());
        assertNull(photo.getThumbBlobKey());
        assertNotNull(photo.getTagList());

        if (photoManager.find(TEST_ID) != null) {
            fail();
        }
    }

    @Test
    public void testCreateLoadModify() {
        Photo photo;
        Photo retrieved;
        photo = photoManager.findOrNew(TEST_ID);
        photo.setId(TEST_ID);
        photoManager.save(photo);
        retrieved = photoManager.findOrNew(TEST_ID);
        retrieved.setDescription("Nice");
        photoManager.save(retrieved);
        retrieved = photoManager.findOrNew(TEST_ID);
        assertEquals("Nice", retrieved.getDescription());

        //photoManager.delete(TEST_ID);
    }

    @Test
    public void testSave() {
        Photo photo = photoManager.findOrNew(TEST_ID);
        photo.setDescription("Nice");
        photoManager.save(photo);

        Photo retrieved = photoManager.findOrNew(TEST_ID);
        assertEquals("Nice", retrieved.getDescription());

        //photoManager.delete(TEST_ID);
    }

    @Test
    public void testDelete() {
        Photo photo = photoManager.findOrNew(TEST_ID);
        photo.setDescription("Nice");
        photoManager.save(photo);

        //photoManager.delete(TEST_ID);
    }

    @Test
    public void tagListPersists() {
        List abc = ImmutableList.of("a", "b", "c");
        Photo photo = photoManager.findOrNew(TEST_ID);
        photo.setTagList(abc);
        photoManager.save(photo);
        photo = null;

        photo = photoManager.find(TEST_ID);
        assertEquals(abc, photo.getTagList());
    }
}
