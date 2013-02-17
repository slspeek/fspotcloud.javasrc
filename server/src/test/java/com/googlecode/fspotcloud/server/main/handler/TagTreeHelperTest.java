package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.shared.main.TagNode;
import org.junit.Test;

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

        TagTreeHelper helper = new TagTreeHelper(root, newHashSet(ROOT));
        TagNode result = helper.getSubTree();
        assertEquals(1, result.getChildren().size());
        TagNode root1 = result.getChildren().get(0);
        assertTrue(root1.getChildren().isEmpty());
        assertEquals(ROOT, root1.getId());
    }

    @Test
    public void testChildOnly() throws Exception {
        TagNode superRoot = new TagNode();
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, ROOT);
        root.addChild(child);
        child.setParent(root);
        superRoot.addChild(root);

        TagTreeHelper helper = new TagTreeHelper(superRoot, newHashSet(CHILD));
        TagNode result = helper.getSubTree();
        assertEquals(1, result.getChildren().size());
        TagNode root1 = result.getChildren().get(0);
        assertEquals(CHILD, root1.getId());
        assertTrue(root1.getChildren().isEmpty());

    }

    @Test
    public void testGetSubTree3() throws Exception {
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, ROOT);
        root.addChild(child);
        child.setParent(root);

        TagTreeHelper helper = new TagTreeHelper(root, newHashSet(CHILD, ROOT));
        TagNode result = helper.getSubTree();
        assertEquals(1, result.getChildren().size());
        TagNode root1 = result.getChildren().get(0);
        assertEquals(ROOT, root1.getId());
        TagNode child1 = root1.getChildren().get(0);
        assertEquals(CHILD, child1.getId());
    }

    @Test
    public void testTwoRoots() throws Exception {
        TagNode superRoot = new TagNode();
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, "0");
        superRoot.addChild(root);
        superRoot.addChild(child);
        TagTreeHelper helper = new TagTreeHelper(superRoot, newHashSet(CHILD, ROOT));
        TagNode result = helper.getSubTree();
        assertEquals(2, result.getChildren().size());
        TagNode root1 = result.getChildren().get(0);
        assertEquals(CHILD, root1.getId());
        TagNode child1 = result.getChildren().get(1);
        assertEquals(ROOT, child1.getId());
    }

    @Test
    public void testLeftTwoRoots() throws Exception {
        TagNode superRoot = new TagNode();
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, "0");
        superRoot.addChild(root);
        superRoot.addChild(child);
        TagTreeHelper helper = new TagTreeHelper(superRoot, newHashSet(ROOT));
        TagNode result = helper.getSubTree();
        assertEquals(1, result.getChildren().size());
        TagNode root1 = result.getChildren().get(0);
        assertEquals(ROOT, root1.getId());
    }

    @Test
    public void testRightTwoRoots() throws Exception {
        TagNode superRoot = new TagNode();
        TagNode root = new TagNode(ROOT, "0");
        TagNode child = new TagNode(CHILD, "0");
        superRoot.addChild(root);
        superRoot.addChild(child);
        TagTreeHelper helper = new TagTreeHelper(superRoot, newHashSet(CHILD));
        TagNode result = helper.getSubTree();
        assertEquals(1, result.getChildren().size());
        TagNode root1 = result.getChildren().get(0);
        assertEquals(CHILD, root1.getId());
    }

    @Test
    public void testRogueSubsetRoots() throws Exception {
        TagNode root = new TagNode(ROOT, "0");


        TagTreeHelper helper = new TagTreeHelper(root, newHashSet(CHILD));
        TagNode result = helper.getSubTree();
        assertEquals(0, result.getChildren().size());

    }
}
