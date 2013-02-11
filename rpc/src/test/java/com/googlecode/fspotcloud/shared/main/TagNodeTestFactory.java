package com.googlecode.fspotcloud.shared.main;

import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;

public class TagNodeTestFactory {

    private final Date date = new Date(0);

    public TagNode getSingleNodeWithOnePicture() {
        PhotoInfo info = new PhotoInfo("42", "Me", date, 2);
        TagNode node = new TagNode("1", "0");
        node.setTagName("Self portraits");
        node.setDescription("Images of myself");
        node.setCachedPhotoList(new PhotoInfoStore(newArrayList(info)));
        return node;
    }

    public TagNode getRootWithOneChild() {
        TagNode root = new TagNode("0");
        TagNode child = new TagNode("1", "0");
        root.addChild(child);
        child.setParent(root);
        return root;
    }

    public TagNode getRootWithThreeChildren() {
        TagNode root = new TagNode("0");
        TagNode child = new TagNode("1", "0");
        TagNode child2 = new TagNode("2", "0");
        TagNode child3 = new TagNode("3", "0");
        root.addChild(child);
        root.addChild(child2);
        root.addChild(child3);
        child.setParent(root);
        child2.setParent(root);
        child3.setParent(root);

        return root;
    }
}
