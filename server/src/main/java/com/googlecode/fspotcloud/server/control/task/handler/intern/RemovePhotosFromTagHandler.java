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
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;


public class RemovePhotosFromTagHandler extends AbstractBatchActionHandler<RemovePhotosFromTagAction, String> {
    private final int MAX_DELETE_TICKS;
    private final TaskQueueDispatch dispatchAsync;
    private final PhotoDao photoDao;
    private final TagDao tagManager;
    private final PeerDatabaseDao peerDatabaseManager;

    @Inject
    public RemovePhotosFromTagHandler(@Named("maxDelete")
                                      int maxDeleteTicks, TaskQueueDispatch dispatchAsync, PhotoDao photoDao,
                                      TagDao tagManager, PeerDatabaseDao peerDatabaseManager) {
        super(dispatchAsync, maxDeleteTicks);
        MAX_DELETE_TICKS = maxDeleteTicks;
        this.dispatchAsync = dispatchAsync;
        this.photoDao = photoDao;
        this.tagManager = tagManager;
        this.peerDatabaseManager = peerDatabaseManager;
    }

    private void checkForDeletion(Tag tag, String deleteTagId, String key) {
        Photo photo = photoDao.find(key);

        if (photo != null) {
            boolean moreImports = false;

            for (String tagId : photo.getTagList()) {
                if (!deleteTagId.equals(tagId)) {
                    Tag tagRelated = tagManager.find(tagId);

                    if (tagRelated != null) {
                        if (tagRelated.isImportIssued()) {
                            moreImports = true;

                            break;
                        }
                    }
                }
            }

            if (!moreImports) {
                photoDao.delete(photo);

                final TreeSet<PhotoInfo> cachedPhotoList = tag.getCachedPhotoList();
                cachedPhotoList.remove(find(tag.getCachedPhotoList(), key));
                tag.setCachedPhotoList(cachedPhotoList);
            }
        }
    }

    private PhotoInfo find(SortedSet<PhotoInfo> set, String id) {
        for (PhotoInfo info : set) {
            if (info.getId().equals(id)) {
                return info;
            }
        }

        return null;
    }

    @Override
    public void doWork(AbstractBatchAction<String> action,
                       Iterator<String> workLoad) {
        Tag tag = tagManager.find(((RemovePhotosFromTagAction) action).getTagId());

        for (int i = 0; (i < MAX_DELETE_TICKS) && workLoad.hasNext(); i++) {
            String photoId = workLoad.next();
            checkForDeletion(tag, tag.getId(), photoId);
        }

        tagManager.save(tag);
        clearTreeCache();
    }

    private void clearTreeCache() {
        peerDatabaseManager.resetCachedTagTree();
    }
}
