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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.fspotcloud.model.jpa.gae.peerdatabase;

import com.googlecode.fspotcloud.shared.main.TagNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class PeerDatabaseEntityTest {
	public PeerDatabaseEntityTest() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testResetCachedTagTree() {
		TagNode cachedTagTree = new TagNode();
		PeerDatabaseEntity instance = new PeerDatabaseEntity();
		instance.setCachedTagTree(cachedTagTree);
		instance.setCachedTagTree(null);
		assertNull(instance.getCachedTagTree());
	}
}
