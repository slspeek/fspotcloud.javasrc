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
}
