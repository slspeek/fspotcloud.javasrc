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
import com.googlecode.fspotcloud.server.model.api.TagDao;
import org.testng.Assert;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class TagAssert {
    @Inject
    TagDao tagDao;

    public void assertTagLoaded(String id) {
        Assert.assertNotNull(tagDao.find(id));
    }

    public void assertTagsLoaded(String... allIds) {
        for (String id : allIds) {
            assertTagLoaded(id);
        }
    }

    public void assertTagsRemoved(String... allIds) {
        for (String id : allIds) {
            verifyTagRemoved(id);
        }
    }

    public void verifyTagRemoved(String id) {
        Assert.assertNull(tagDao.find(id));
    }
}
