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

package com.googlecode.fspotcloud.shared.main;

import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.*;

public class TagNodeTest {
    TagNode node;
    TagNode root;

    TagNodeTestFactory factory = new TagNodeTestFactory();

    @Before
    public void setUp() throws Exception {
        node = new TagNode();
        node.setTagName("Friends");
        node.setDescription("Friends and family");
        node.setId("1");

        PhotoInfo man = new PhotoInfo("2", "Human", new Date());
        PhotoInfoStore store = new PhotoInfoStore(ImmutableSortedSet.of(man));
        node.setCachedPhotoList(store);
        root = new TagNode("TopNode");
    }

    @Test
    public void testNumericalPathToRoot() throws Exception {
        TagNode simpleTree = factory.getRootWithOneChild();
        TagNode child = simpleTree.getChildren().get(0);

        assertEquals(new Integer(0), (child.pathTo(simpleTree)).get(0));

    }

    @Test
    public void testNumericalPathToRootComplex() throws Exception {
        TagNode simpleTree = factory.getRootWithThreeChildren();
        TagNode child0 = simpleTree.getChildren().get(0);

        assertEquals(new Integer(0), (child0.pathTo(simpleTree)).get(0));

        TagNode child2 = simpleTree.getChildren().get(2);

        assertEquals(new Integer(2), (child2.pathTo(simpleTree)).get(0));

    }

    @Test
    public void testNumericalPathToRootLengthTwo() throws Exception {
        TagNode simpleTree = factory.getRootWithThreeChildren();
        TagNode child2 = simpleTree.getChildren().get(2);
        TagNode extra = new TagNode("5", "3");
        extra.setParent(child2);
        child2.addChild(extra);

        assertEquals(new Integer(2), (extra.pathTo(simpleTree)).get(0));
        assertEquals(new Integer(0), (extra.pathTo(simpleTree)).get(1));

    }

    @Test
    public void testNumericalPathToRootInductionBase() throws Exception {
        TagNode simpleTree = factory.getSingleNodeWithOnePicture();
        assertEquals(0, (simpleTree.pathTo(simpleTree)).size());

    }

    @Test
    public void testEqualsObject() {
        TagNode other = new TagNode();
        other.setId("1");
        assertEquals(node, other);
    }

    @Test
    public void testNotEquals() {
        assertFalse(root.equals(""));
        assertFalse(root.equals(null));
    }

    @Test
    public void testAddChild() {
        root.addChild(node);

        TagNode child = root.getChildren().get(0);
        assertEquals(child, node);
    }

    @Test
    public void testToString() {
        root.toString();
        node.toString();
    }

    @Test
    public void testConstructor() {
        TagNode t = new TagNode("1", "0");
        assertEquals("1", t.getId());
        assertEquals("0", t.getParentId());
    }

    @Test
    public void hashTable() {
        Map<TagNode, String> map = Maps.newHashMap();
        map.put(new TagNode("3"), "FOO");
        assertEquals("FOO", map.get(new TagNode("3")));
    }

    @Test
    public void testFind() throws Exception {
        TagNodeTestFactory factory = new TagNodeTestFactory();
        TagNode node = factory.getSingleNodeWithOnePicture();

        TagNode found = TagNode.find(node, "1");
        assertNotNull(found);


    }
}
