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

package com.googlecode.fspotcloud.server.model.tag;

import com.google.common.collect.ImmutableList;
import com.googlecode.fspotcloud.shared.main.TagNode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TreeBuilderTest {
    List<TagNode> nodes;

    @Before
    public void setUp() throws Exception {
        TagNode root = new TagNode("1", "0");
        TagNode level1_a = new TagNode("2", "1");
        TagNode level2_b = new TagNode("3", "1");
        level2_b.setImportIssued(true);

        TagNode secondRoot = new TagNode("4", "0");
        secondRoot.setImportIssued(true);
        nodes = ImmutableList.of(root, level1_a, level2_b, secondRoot);
    }

    @Test
    public void testVerySimpleTree() {
        TreeBuilder builder = new TreeBuilder(nodes);
        List<TagNode> trees = builder.getRoots();
        assertEquals(2, trees.size());

        TagNode root = trees.get(0);
        List<TagNode> level_1s = root.getChildren();
        assertEquals(2, level_1s.size());
    }

    @Test
    public void testPublicTreeSimpleTree() {
        TreeBuilder builder = new TreeBuilder(nodes);
        List<TagNode> trees = builder.getPublicRoots();
        assertEquals(2, trees.size());

        TagNode root = trees.get(0);
        List<TagNode> level_1s = root.getChildren();
        assertEquals(0, level_1s.size());
    }
}
