package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.fspotcloud.shared.main.TagNode;

public class SingleSelectionModelExt extends SingleSelectionModel<TagNode> {

    public void setSelectedQuietly(TagNode node, boolean selected) {
        setEventCancelled(true);
        super.setSelected(node, selected);
    }

}
