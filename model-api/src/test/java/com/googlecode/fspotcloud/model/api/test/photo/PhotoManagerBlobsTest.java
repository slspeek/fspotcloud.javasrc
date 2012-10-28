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

import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.model.api.test.EmptyGuiceBerryEnv;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.BlobService;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PhotoManagerBlobsTest {
    public static final String TEST_ID = "1";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    private PhotoDao photoManager;
    @Inject
    private BlobService blobService;
    private final Random random = new Random();

    @After
    public void cleanUp() {
        photoManager.deleteBulk(100);
    }

    private String saveBlob(int size) {
        byte[] data = new byte[size];
        random.nextBytes(data);

        return blobService.save("application", data).getKeyString();
    }

    @Test
    public void testDeletionOfImageBlob() {
        String blobKey = saveBlob(100000);
        Photo photo = photoManager.findOrNew(TEST_ID);
        photo.setId(TEST_ID);
        photo.setImageBlobKey(blobKey);
        photoManager.save(photo);
        photo = null;
        assertNotNull(blobService.fetchData(new BlobKey(blobKey)));
        photoManager.deleteByKey(TEST_ID);
        assertNull(blobService.fetchData(new BlobKey(blobKey)));
    }

    @Test
    public void testDeletionOfThumbBlob() {
        String blobKey = saveBlob(100000);
        Photo photo = photoManager.findOrNew(TEST_ID);
        photo.setId(TEST_ID);
        photo.setThumbBlobKey(blobKey);
        photoManager.save(photo);
        photo = null;
        assertNotNull(blobService.fetchData(new BlobKey(blobKey)));
        photoManager.deleteByKey(TEST_ID);
        assertNull(blobService.fetchData(new BlobKey(blobKey)));
    }

    @Test
    public void testDeletionOfFullsizeBlob() {
        String blobKey = saveBlob(100000);
        Photo photo = photoManager.findOrNew(TEST_ID);
        photo.setId(TEST_ID);
        photo.setFullsizeImageBlobKey(blobKey);
        photoManager.save(photo);
        photo = null;
        assertNotNull(blobService.fetchData(new BlobKey(blobKey)));
        photoManager.deleteByKey(TEST_ID);
        assertNull(blobService.fetchData(new BlobKey(blobKey)));
    }
}
