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
import com.googlecode.fspotcloud.server.image.ImageHelper;
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.fspotcloud.shared.peer.PhotoDataResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;


public class PhotoDataCallback implements SerializableAsyncCallback<PhotoDataResult> {
    private static final long serialVersionUID = 246810426240427570L;
    @Inject
    private transient PhotoDao photoManager;
    @Inject
    private transient ImageHelper imageHelper;
    @Inject
    private transient TagDao tagManager;
    @Inject
    private transient PeerDatabaseDao peerDatabaseDao;

    public PhotoDataCallback(PhotoDao photoManager, ImageHelper imageHelper,
                             TagDao tagManager, PeerDatabaseDao peerDatabaseDao) {
        super();
        this.photoManager = photoManager;
        this.imageHelper = imageHelper;
        this.tagManager = tagManager;
        this.peerDatabaseDao = peerDatabaseDao;
    }

    @Override
    public void onFailure(Throwable caught) {
    }

    @Override
    public void onSuccess(PhotoDataResult result) {
        List<Photo> photoList = new ArrayList<Photo>();

        for (PhotoData photoData : result.getPhotoDataList()) {
            Photo p = recievePhoto(photoData);
            photoList.add(p);
        }

        photoManager.saveAll(photoList);
        clearTreeCache();
    }

    private void clearTreeCache() {
        peerDatabaseDao.resetCachedTagTree();
    }

    private Photo recievePhoto(PhotoData photoData) {
        String keyName = photoData.getPhotoId();
        String desc = photoData.getDescription();
        Date date = photoData.getDate();
        List<String> tags = photoData.getTagList();
        byte[] imageData = photoData.getImageData();
        byte[] thumbData = photoData.getThumbData();

        Photo photo = photoManager.findOrNew(keyName);
        photo.setDescription(desc);
        photo.setDate(date);
        photo.setTagList(tags);

        imageHelper.saveImage(photo, ImageHelper.Type.NORMAL, imageData);
        imageHelper.saveImage(photo, ImageHelper.Type.THUMB, imageData);

        for (String tagId : tags) {
            Tag tag = tagManager.find(tagId);
            PhotoInfo updatedInfo = new PhotoInfo(photo.getId(),
                    photo.getDescription(), photo.getDate(),
                    photoData.getVersion());
            TreeSet<PhotoInfo> cachedPhotoList = tag.getCachedPhotoList();
            cachedPhotoList.remove(updatedInfo);
            cachedPhotoList.add(updatedInfo);
            tag.setCachedPhotoList(cachedPhotoList);
            tagManager.save(tag);
        }

        return photo;
    }
}
