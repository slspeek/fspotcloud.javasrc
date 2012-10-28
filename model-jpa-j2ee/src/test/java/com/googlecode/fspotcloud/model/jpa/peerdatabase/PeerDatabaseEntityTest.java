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
package com.googlecode.fspotcloud.model.jpa.peerdatabase;

import com.google.common.collect.Lists;
import com.googlecode.fspotcloud.shared.main.TagNode;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class PeerDatabaseEntityTest {
    @Test
    public void testGetCachedTagTree() {
        System.out.println("getCachedTagTree");

        PeerDatabaseEntity instance = new PeerDatabaseEntity();
        List expResult = null;
        List result = instance.getCachedTagTree();
        assertEquals(expResult, result);

        List<TagNode> tree = Lists.newArrayList(new TagNode("1"));
        instance.setCachedTagTree(tree);

        List<TagNode> rTree = instance.getCachedTagTree();
        TagNode rNode = rTree.get(0);
        assertEquals("1", rNode.getId());

        instance.setCachedTagTree(null);
        assertNull(instance.getCachedTagTree());
    }
}
