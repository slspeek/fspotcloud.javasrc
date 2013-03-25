package com.googlecode.fspotcloud.shared.main.test;

import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;

public class TagNodeTestFactory {

    private final static Date date = new Date(100);
    private final static Date laterDate = new Date(100000);
    public final static PhotoInfo FIRST_PICTURE_INFO = new PhotoInfo("42", "Me", date, 2);
    public final static PhotoInfo SECOND_PICTURE_INFO = new PhotoInfo("102", "You", laterDate, 1);

    public TagNode getSingleNodeWithOnePicture() {
        TagNode node = new TagNode("1", "0");
        node.setTagName("Self portraits");
        node.setDescription("Images of myself");
        node.setCachedPhotoList(new PhotoInfoStore(newArrayList(FIRST_PICTURE_INFO)));
        node.setCount(1);
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

    public TagNode getTwoCategories() {
        TagNode root = new TagNode();
        PhotoInfo info = new PhotoInfo("42", "Me", date, 2);
        TagNode node = new TagNode("1", "0");
        node.setTagName("Self portraits");
        node.setDescription("Images of myself");
        node.setCachedPhotoList(new PhotoInfoStore(newArrayList(info)));
        root.addChild(node);
        node.setParent(root);
        node.setCount(1);

        info = new PhotoInfo("43", "Myself", laterDate, 2);
        node = new TagNode("2", "0");
        node.setTagName("Portraits");
        node.setDescription("Portraits");
        node.setCachedPhotoList(new PhotoInfoStore(newArrayList(info)));
        root.addChild(node);
        node.setParent(root);
        node.setCount(1);
        return root;
    }

    public TagNode getSingleNodeWithTwoPictures() {
        TagNode node = new TagNode("1", "0");
        node.setTagName("Self portraits");
        node.setDescription("Images of myself");
        node.setCachedPhotoList(new PhotoInfoStore(newArrayList(FIRST_PICTURE_INFO, SECOND_PICTURE_INFO)));
        node.setCount(2);
        return node;

    }
}
