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

package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.TreeSelectionHandlerInterface;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Logger;


public class TreeSelectionHandler implements TreeSelectionHandlerInterface {
    private final Logger log = Logger.getLogger(TreeSelectionHandler.class.getName());
    private SingleSelectionModel<TagNode> selectionModel;
    private final Navigator navigator;

    @Inject
    public TreeSelectionHandler(Navigator navigator) {
        this.navigator = navigator;
    }

    public void setSelectionModel(SingleSelectionModel<TagNode> selectionModel) {
        this.selectionModel = selectionModel;
        selectionModel.addSelectionChangeHandler(this);
    }

    @Override
    public void onSelectionChange(SelectionChangeEvent event) {
        log.info("Selection event from tree" + selectionModel);

        TagNode node = selectionModel.getSelectedObject();

        if (node != null) {
            String tagId = node.getId();
            goToPhoto(tagId, node.getCachedPhotoList());
        }
    }

    private void goToPhoto(String otherTagId, PhotoInfoStore store) {
        navigator.goToTag(otherTagId, store);
    }
}
