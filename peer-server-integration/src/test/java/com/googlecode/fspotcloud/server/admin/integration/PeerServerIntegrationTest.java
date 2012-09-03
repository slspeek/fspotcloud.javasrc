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

import com.google.common.testing.TearDown;
import com.google.guiceberry.testng.TestNgGuiceBerry;
import com.googlecode.fspotcloud.shared.dashboard.UserDeletesAllAction;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import com.googlecode.fspotcloud.shared.peer.GetPeerMetaDataAction;
import com.googlecode.fspotcloud.shared.peer.PeerMetaDataResult;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class PeerServerIntegrationTest extends PeerServerEnvironment {
    private TearDown toTearDown;

    @BeforeMethod
    public void setUp(Method m) throws SQLException {
        // Make this the call to TestNgGuiceBerry.setUp as early as possible
        toTearDown = TestNgGuiceBerry.setUp(this, m,
                NoAuthPlaceHolderIntegrationModule.class);
        setUpPeer();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        tagDao.deleteBulk(1000);
        photoDao.deleteBulk(1000);
        peers.deleteBulk(1000);
        // Make this the call to TestNgGuiceBerry.tearDown as late as possible
        toTearDown.tearDown();
    }

    @Test
    public void shouldRemoveSomeFurniture() throws Exception {
        testImportAllTags();
        importTag("1");
        verfiyFurnitureIsLoaded();
        setPeerTestDatabase("photos_smaller.db");
        synchronizePeer();
        synchronizePeer();
        verfiyFurnitureFirstPhaseIsLoaded();
        verifyImagesWereRemoved();
    }

    @Test
    public void testImportFurnitureInThreePhases() throws Exception {
        testImportAllTags();
        setPeerTestDatabase("photos_smaller.db");
        importTag("1");
        verfiyFurnitureFirstPhaseIsLoaded();
        setUpPeer();
        synchronizePeer();
        verfiyFurnitureIsLoaded();
        setPeerTestDatabase("photos_smaller.db");
        synchronizePeer();
        verifyImagesWereRemoved();
        photoInfo.assertPhotosRemoved("6");
        verfiyFurnitureFirstPhaseIsLoaded();
    }

    @Test
    public void getTagTreeSimple() throws Exception {
        setUpPeer();

        TagTreeResult result = fetchTagTree();
        assertTrue(result.getTree().isEmpty());
    }

    @Test
    public void testGetPeerMetaData() throws DispatchException, SQLException {
        setUpPeer();

        PeerMetaDataResult result = dispatch.execute(new GetPeerMetaDataAction());
        assertEquals(28, result.getPhotoCount());
        assertEquals(5, result.getTagCount());
    }

    @Test
    public void testImportGlass() throws Exception {
        setUpPeer();
        testImportAllTags();
        importTag("5");
        photoDao.find("3");
    }

    @Test
    public void testImportFurniture() throws Exception {
        testImportAllTags();
        importTag("1");
        verfiyFurnitureIsLoaded();
    }

    @Test
    public void testImportFurnitureInTwoPhases() throws Exception {
        testImportAllTags();
        setPeerTestDatabase("photos_smaller.db");
        importTag("1");
        verfiyFurnitureFirstPhaseIsLoaded();
        setUpPeer();
        synchronizePeer();
        verfiyFurnitureIsLoaded();
    }

    @Test
    public void testUImportFurniture() throws Exception {
        testImportAllTags();
        setPeerTestDatabase("photos_smaller.db");
        importTag("1");
        verfiyFurnitureFirstPhaseIsLoaded();
        unImportTag("1");
        verifiyFurnitureFirstPhaseWasRemoved();
    }

    @Test
    public void testRemovingOfTags() throws Exception {
        testImportAllTags();
        importTag("2");
        assertComputersIsLoaded();
        importTag("4");
        assertPCLoaded();
        dispatch.execute(new UserDeletesAllAction());
        assertTrue(photoInfo.isEmpty());
        tagInfo.assertTagsRemoved("1", "2", "3", "4", "5");
    }

    @Test
    public void testOverlapWithTagRemoval() throws Exception {
        testImportAllTags();
        importTag("2");
        assertComputersIsLoaded();
        importTag("4");
        assertPCLoaded();
        unImportTag("2");
        assertPCLoaded();
        unImportTag("4");
        photoInfo.assertPhotosRemoved("17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "9", "11");
    }
}
