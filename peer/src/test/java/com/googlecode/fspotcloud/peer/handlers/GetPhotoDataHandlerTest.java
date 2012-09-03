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

package com.googlecode.fspotcloud.peer.handlers;

import com.google.common.collect.ImmutableList;
import com.googlecode.fspotcloud.peer.db.Data;
import com.googlecode.fspotcloud.shared.peer.GetPhotoDataAction;
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.fspotcloud.shared.peer.PhotoDataResult;
import junit.framework.TestCase;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.net.URL;


public class GetPhotoDataHandlerTest extends TestCase {
    private Data data;
    private GetPhotoDataHandler handler;
    private GetPhotoDataAction action;

    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("photo.dir.original", "//home/steven/Photos");
        System.setProperty("photo.dir.override",
                "" + System.getProperty("user.dir") + "/src/test/resources/Photos");

        URL testDatabase = ClassLoader.getSystemResource("photos.db");
        String path = testDatabase.getPath();
        data = new Data("jdbc:sqlite:" + path);
        handler = new GetPhotoDataHandler(data);
        action = new GetPhotoDataAction(new ImageSpecs(1, 1, 1, 1),
                ImmutableList.of("3"));
    }

    public void testExecute() throws DispatchException {
        PhotoDataResult result = handler.execute(action, null);
        PhotoData photoData = result.getPhotoDataList().get(0);
        assertEquals("3", photoData.getPhotoId());
    }
}
