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

import com.googlecode.fspotcloud.peer.db.Data;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.peer.GetTagUpdateInstructionsAction;
import com.googlecode.fspotcloud.shared.peer.PhotoRemovedFromTag;
import com.googlecode.fspotcloud.shared.peer.PhotoUpdate;
import com.googlecode.fspotcloud.shared.peer.TagUpdateInstructionsResult;
import junit.framework.TestCase;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;


public class GetTagUpdateInstructionsHandlerTest extends TestCase {
    /**
     * Tag "5" Glass contains only image "3"
     */
    private Data data;
    GetTagUpdateInstructionsHandler handler;
    GetTagUpdateInstructionsAction action;

    protected void setUp() throws Exception {
        super.setUp();

        URL testDatabase = ClassLoader.getSystemResource("photos.db");
        String path = testDatabase.getPath();
        data = new Data("jdbc:sqlite:" + path);
        handler = new GetTagUpdateInstructionsHandler(data);
        action = new GetTagUpdateInstructionsAction("5",
                new TreeSet<PhotoInfo>());
    }

    public void testExecute() throws DispatchException {
        TagUpdateInstructionsResult result = handler.execute(action, null);
        List<PhotoUpdate> updates = result.getToBoUpdated();
        assertEquals(1, updates.size());
        assertEquals("3", result.getToBoUpdated().get(0).getPhotoId());
    }

    public void testExecuteAllReadyImported() throws DispatchException {
        PhotoInfo info = new PhotoInfo("3", "", new Date(0));
        TreeSet<PhotoInfo> set = new TreeSet<PhotoInfo>();
        set.add(info);
        action = new GetTagUpdateInstructionsAction("5", set);

        TagUpdateInstructionsResult result = handler.execute(action, null);
        List<PhotoUpdate> updates = result.getToBoUpdated();
        assertEquals(0, updates.size());
    }

    public void testExecuteFurniture() throws DispatchException {
        PhotoInfo info = new PhotoInfo("3", "", new Date(0));
        TreeSet<PhotoInfo> set = new TreeSet<PhotoInfo>();
        set.add(info);
        action = new GetTagUpdateInstructionsAction("1", set);

        TagUpdateInstructionsResult result = handler.execute(action, null);
        List<PhotoUpdate> updates = result.getToBoUpdated();
        assertEquals(9, updates.size());
    }

    public void testExecuteRemoved() throws DispatchException {
        PhotoInfo info = new PhotoInfo("7", "", new Date(0));
        TreeSet<PhotoInfo> set = new TreeSet<PhotoInfo>();
        set.add(info);
        action = new GetTagUpdateInstructionsAction("5", set);

        TagUpdateInstructionsResult result = handler.execute(action, null);

        List<PhotoRemovedFromTag> toBoRemovedFromTag = result.getToBoRemovedFromTag();
        assertEquals(1, toBoRemovedFromTag.size());
    }
}
