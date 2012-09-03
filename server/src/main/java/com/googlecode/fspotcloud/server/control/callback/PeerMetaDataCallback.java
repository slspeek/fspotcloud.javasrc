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

package com.googlecode.fspotcloud.server.control.callback;

import com.google.inject.Inject;
import com.googlecode.botdispatch.SerializableAsyncCallback;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.peer.GetPeerUpdateInstructionsAction;
import com.googlecode.fspotcloud.shared.peer.PeerMetaDataResult;
import com.googlecode.fspotcloud.shared.peer.TagData;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;

public class PeerMetaDataCallback implements SerializableAsyncCallback<PeerMetaDataResult> {
    private static final long serialVersionUID = 1851403859917750767L;
    @Inject
    private transient Logger log;
    @Inject
    private transient PeerDatabaseDao defaultPeer;
    @Inject
    private transient TagDao tagManager;
    @Inject
    private transient ControllerDispatchAsync dispatchAsync;

    public PeerMetaDataCallback() {
        super();
    }

    @Override
    public void onSuccess(PeerMetaDataResult result) {
        log.log(Level.INFO, "On success: {0}", result);

        int count = result.getPhotoCount();
        int tagCount = result.getTagCount();
        PeerDatabase p = defaultPeer.get();
        p.setPeerPhotoCount(count);
        p.setTagCount(tagCount);
        defaultPeer.save(p);
        dispatchAsync.execute(new GetPeerUpdateInstructionsAction(getTagData()),
                new PeerUpdateInstructionsCallback());
    }

    @Override
    public void onFailure(Throwable caught) {
        log.log(Level.SEVERE, "Error on peer-component: ", caught);
    }

    private List<TagData> getTagData() {
        List<TagData> result = newArrayList();

        for (Tag tag : tagManager.findAll(1000)) {
            TagData data = new TagData(tag.getId(), tag.getTagName(),
                    tag.getParentId(), tag.getCount());
            result.add(data);
        }

        return result;
    }
}
