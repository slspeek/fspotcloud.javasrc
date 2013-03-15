package com.googlecode.fspotcloud.client.data;

import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LatestTagHelper {

    public TagNode getLatestNode(TagNode fullTree) {
        Date latestDate = new Date(0);
        TagNode latestNode = null;
        IndexingUtil util = new IndexingUtil();
        Map<String, TagNode> tagNodeIndex = new HashMap<String, TagNode>();
        util.rebuildTagNodeIndex(tagNodeIndex, fullTree);
        for (String tagId : tagNodeIndex.keySet()) {
            TagNode node = tagNodeIndex.get(tagId);
            PhotoInfoStore store = node.getCachedPhotoList();
            if ((store != null) && !store.isEmpty()) {
                PhotoInfo info = store.last();
                Date aPhotoDate = info.getDate();

                if (aPhotoDate.after(latestDate)) {
                    latestDate = aPhotoDate;
                    latestNode = node;
                }
            }
        }
        return latestNode;
    }

}
