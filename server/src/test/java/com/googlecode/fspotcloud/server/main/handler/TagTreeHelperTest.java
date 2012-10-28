package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.shared.main.TagNode;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TagTreeHelperTest {

    public static final String ROOT = "ROOT";
    public static final String CHILD = "CHILD";

    @Test
    public void testRootOnly() throws Exception {
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, ROOT);
        root.addChild(child);
        child.setParent(root);

        TagTreeHelper helper = new TagTreeHelper(newArrayList(root), newHashSet(ROOT));
        List<TagNode> result = helper.getSubTree();
        assertEquals(1, result.size());
        TagNode root1 = result.get(0);
        assertTrue(root1.getChildren().isEmpty());
        assertEquals(ROOT, root1.getId());
    }

    @Test
    public void testChildOnly() throws Exception {
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, ROOT);
        root.addChild(child);
        child.setParent(root);

        TagTreeHelper helper = new TagTreeHelper(newArrayList(root), newHashSet(CHILD));
        List<TagNode> result = helper.getSubTree();
        assertEquals(1, result.size());
        TagNode root1 = result.get(0);
        assertEquals(CHILD, root1.getId());
        assertTrue(root1.getChildren().isEmpty());

    }

    @Test
    public void testGetSubTree3() throws Exception {
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, ROOT);
        root.addChild(child);
        child.setParent(root);

        TagTreeHelper helper = new TagTreeHelper(newArrayList(root), newHashSet(CHILD, ROOT));
        List<TagNode> result = helper.getSubTree();
        assertEquals(1, result.size());
        TagNode root1 = result.get(0);
        assertEquals(ROOT, root1.getId());
        TagNode child1 = root1.getChildren().get(0);
        assertEquals(CHILD, child1.getId());
    }

    @Test
    public void testTwoRoots() throws Exception {
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, "0");

        TagTreeHelper helper = new TagTreeHelper(newArrayList(root, child), newHashSet(CHILD, ROOT));
        List<TagNode> result = helper.getSubTree();
        assertEquals(2, result.size());
        TagNode root1 = result.get(0);
        assertEquals(CHILD, root1.getId());
        TagNode child1 = result.get(1);
        assertEquals(ROOT, child1.getId());
    }

    @Test
    public void testLeftTwoRoots() throws Exception {
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, "0");

        TagTreeHelper helper = new TagTreeHelper(newArrayList(root, child), newHashSet(ROOT));
        List<TagNode> result = helper.getSubTree();
        assertEquals(1, result.size());
        TagNode root1 = result.get(0);
        assertEquals(ROOT, root1.getId());
    }

    @Test
    public void testRightTwoRoots() throws Exception {
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, "0");

        TagTreeHelper helper = new TagTreeHelper(newArrayList(root, child), newHashSet(CHILD));
        List<TagNode> result = helper.getSubTree();
        assertEquals(1, result.size());
        TagNode root1 = result.get(0);
        assertEquals(CHILD, root1.getId());
    }
}
