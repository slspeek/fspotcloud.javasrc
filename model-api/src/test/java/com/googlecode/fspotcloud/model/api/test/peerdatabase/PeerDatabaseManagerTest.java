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

package com.googlecode.fspotcloud.model.api.test.peerdatabase;

import com.google.guiceberry.junit4.GuiceBerryRule;
import com.googlecode.fspotcloud.model.api.test.EmptyGuiceBerryEnv;
import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.shared.main.TagNode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PeerDatabaseManagerTest {
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    private PeerDatabaseDao manager;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void resetCachedTagTree() {
        PeerDatabase defaultPD = manager.get();
        List<TagNode> list = defaultPD.getCachedTagTree();
        assertNull(list);
        list = new ArrayList<TagNode>();
        defaultPD.setCachedTagTree(list);
        manager.save(defaultPD);
        defaultPD = null;
        defaultPD = manager.get();
        list = defaultPD.getCachedTagTree();
        assertNotNull(list);
        defaultPD.setCachedTagTree(null);
        assertNull(defaultPD.getCachedTagTree());
        manager.save(defaultPD);
        defaultPD = null;
        defaultPD = manager.get();
        list = defaultPD.getCachedTagTree();
        assertNull(list);
    }

    @Test
    public void resetCachedTagTree2() {
        manager.resetCachedTagTree();

        PeerDatabase defaultPD = manager.get();
        List<TagNode> list = defaultPD.getCachedTagTree();
        assertNull(list);
        list = new ArrayList<TagNode>();
        defaultPD.setCachedTagTree(list);
        manager.save(defaultPD);
        defaultPD = null;
        defaultPD = manager.get();
        list = defaultPD.getCachedTagTree();
        assertNotNull(list);
        manager.resetCachedTagTree();
        defaultPD = null;
        defaultPD = manager.get();
        list = defaultPD.getCachedTagTree();
        assertNull(list);
    }

    @Test
    public void testGet() {
        PeerDatabase defaultPD = manager.get();
        assertNotNull(defaultPD);

        PeerDatabase secondInstance = manager.get();
        assertNotNull(secondInstance);
    }

    @Test
    public void testSave() {
        PeerDatabase defaultPD = manager.get();
        manager.save(defaultPD);
    }

    @Test
    public void testGetCachedTagTree() {
        PeerDatabase defaultPD = manager.get();
        List<TagNode> list = defaultPD.getCachedTagTree();
        assertNull(list);
    }

    @Test
    public void testDefaultsForThumbDimension() {
        PeerDatabase defaultPD = manager.get();
        String dim = defaultPD.getThumbDimension();
        assertEquals("512x384", dim);
    }

    @Test
    public void testDefaultForGetTagCount() {
        PeerDatabase defaultPD = manager.get();
        int count = defaultPD.getTagCount();
        assertEquals(0, count);
    }
}
