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

package com.googlecode.fspotcloud.model.jpa.photo;

import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.BlobService;
import com.googlecode.simplejpadao.SimpleDAONamedIdImpl;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class PhotoManagerBase<T extends Photo, U extends T>
        extends SimpleDAONamedIdImpl<Photo, U, String> implements PhotoDao {
    @Inject
    private BlobService blobService;

    @Override
    protected void preDelete(Photo entity) {
        String imageKey = entity.getImageBlobKey();
        String thumbKey = entity.getThumbBlobKey();
        String fullsize = entity.getFullsizeImageBlobKey();
        List<String> keys = newArrayList(imageKey, thumbKey, fullsize);

        for (String key : keys) {
            removeByKey(key);
        }
    }

    private void removeByKey(String keyString) {
        if (keyString != null) {
            BlobKey key = new BlobKey(keyString);
            blobService.delete(key);
        }
    }

    protected void detach(Photo photo) {
        List<String> tagList = photo.getTagList();
        photo.setTagList(new ArrayList<String>(tagList));
    }

    protected abstract Photo newPhoto();
}
