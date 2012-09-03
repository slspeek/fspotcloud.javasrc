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

package com.googlecode.fspotcloud.server.admin.integration;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.inject.Inject;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import org.testng.Assert;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class PhotoAssert {
    @Inject
    PhotoDao photoDao;

    public boolean isEmpty() {
        return photoDao.isEmpty();
    }

    public void verifyPhotoRemoved(String id) {
        Assert.assertNull(photoDao.find(id));
    }

    public void assertPhotoLoaded(String id) {
        Assert.assertNotNull(photoDao.find(id));
    }

    public void assertPhotosRemoved(String... allIds) {
        for (String id : allIds) {
            verifyPhotoRemoved(id);
        }
    }

    public void assertPhotosLoaded(String... allIds) {
        for (String id : allIds) {
            assertPhotoLoaded(id);
        }
    }
}
