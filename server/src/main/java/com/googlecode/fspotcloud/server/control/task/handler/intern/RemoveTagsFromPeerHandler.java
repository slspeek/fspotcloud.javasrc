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

package com.googlecode.fspotcloud.server.control.task.handler.intern;

import com.googlecode.fspotcloud.server.control.task.actions.intern.AbstractBatchAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemovePhotosFromTagAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemoveTagsDeletedFromPeerAction;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.peer.TagRemovedFromPeer;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class RemoveTagsFromPeerHandler extends AbstractBatchActionHandler<RemoveTagsDeletedFromPeerAction, TagRemovedFromPeer> {
    private final TaskQueueDispatch dispatchAsync;
    private TagDao tagManager;

    @Inject
    public RemoveTagsFromPeerHandler(@Named("maxDelete")
                                     int maxDeleteTicks, TaskQueueDispatch dispatchAsync, TagDao tagManager) {
        super(dispatchAsync, maxDeleteTicks);
        this.dispatchAsync = dispatchAsync;
        this.tagManager = tagManager;
    }

    @Override
    public void doWork(AbstractBatchAction<TagRemovedFromPeer> action,
                       Iterator<TagRemovedFromPeer> workLoad) {
        TagRemovedFromPeer tagInfo = workLoad.next();
        Tag tag = tagManager.find(tagInfo.getTagId());
        List<String> idList = newArrayList();

        for (PhotoInfo info : tag.getCachedPhotoList()) {
            idList.add(info.getId());
        }

        dispatchAsync.execute(new RemovePhotosFromTagAction(
                tagInfo.getTagId(),
                idList));
        tagManager.deleteByKey(tagInfo.getTagId());
    }
}
