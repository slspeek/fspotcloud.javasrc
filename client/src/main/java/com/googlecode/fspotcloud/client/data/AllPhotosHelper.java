package com.googlecode.fspotcloud.client.data;

import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.SortedSet;

import static com.google.common.collect.Sets.newTreeSet;

public class AllPhotosHelper {

    PhotoInfoStore getAllPhotos(TagNode root) {
        PhotoInfoStore result;
        SortedSet<PhotoInfo> resultSet = newTreeSet();
        treeWalker(root, resultSet);
        result = new PhotoInfoStore(resultSet);
        return result;
    }

    private void treeWalker(TagNode node, SortedSet<PhotoInfo> result) {
        PhotoInfoStore localStore = node.getCachedPhotoList();
        addStore(localStore, result);
        for (TagNode child : node.getChildren()) {
            treeWalker(child, result);
        }
    }

    private void addStore(PhotoInfoStore localStore, SortedSet<PhotoInfo> result) {
        for (int i = 0; i < localStore.size(); i++) {
            result.add(localStore.get(i));
        }
    }
}
