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
import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.testng.AssertJUnit.*;

public class PeerServerIntegrationHardCasesTest extends PeerServerEnvironment {
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
    public void shouldBeNull() throws SQLException, DispatchException {
        fetchTagTree();
        peers.resetCachedTagTree();

        PeerDatabase peer = peers.get();
        assertNull(peer.getCachedTagTree());
        fetchTagTree();
        peer = peers.get();
        assertNotNull(peer.getCachedTagTree());
        synchronizePeer();
        peer = peers.get();
        assertNull(peer.getCachedTagTree());
    }

    @Test
    public void getTagTreeAfterOneSynchronize() throws Exception {
        TagTreeResult result = fetchTagTree();
        assertTrue(result.getTree().isEmpty());
        Logger.getAnonymousLogger().info("Start");
        synchronizePeer();
        Logger.getAnonymousLogger().info("Fetch tree");
        result = fetchTagTree();
        //As nothing is imported yet
        assertTrue(result.getTree().isEmpty());
        Logger.getAnonymousLogger().info("Import tag 3");
        importTag("3");
        Logger.getAnonymousLogger().info("Fetch tree");
        result = fetchTagTree();

        TagNode mac = result.getTree().get(0);
        assertEquals("Mac", mac.getTagName());

        setPeerTestDatabase("photos_smaller.db");
        Logger.getAnonymousLogger().info("Synchronize again");
        synchronizePeer();
        Logger.getAnonymousLogger().info("Fetch tree last time");
        result = fetchTagTree();
        mac = result.getTree().get(0);
        assertEquals("Macintosh", mac.getTagName());
    }
}
