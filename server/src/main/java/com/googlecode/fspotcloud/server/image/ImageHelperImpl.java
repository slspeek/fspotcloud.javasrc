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

package com.googlecode.fspotcloud.server.image;

import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.BlobService;

import javax.inject.Inject;


public class ImageHelperImpl implements ImageHelper {
    public static final String IMAGE_JPEG = "image/jpeg";
    @Inject
    BlobService blobService;

    @Override
    public byte[] getImage(Photo photo, Type type) {
        if (photo == null) {
            return null;
        }
        String key = keyForType(type, photo);
        byte[] image = null;
        if (key != null) {
            image = blobService.fetchData(new BlobKey(key));
        }
        return image;
    }

    @Override
    public void saveImage(Photo photo, Type type, byte[] imageData) {
        BlobKey blobKey = blobService.save(IMAGE_JPEG, imageData);
        setKeyForType(type, photo, blobKey);
    }

    private void setKeyForType(Type type, Photo photo, BlobKey key) {
        final String keyString = key.getKeyString();
        switch (type) {
            case FULLSIZE:
                photo.setFullsizeImageBlobKey(keyString);
                break;

            case NORMAL:
                photo.setImageBlobKey(keyString);
                break;

            case THUMB:
                photo.setThumbBlobKey(keyString);
                break;

            default:
                throw new IllegalStateException("Unknown image type");
        }
    }

    private String keyForType(Type type, Photo photo) {
        String result;

        switch (type) {
            case FULLSIZE:
                result = photo.getFullsizeImageBlobKey();
                break;

            case NORMAL:
                result = photo.getImageBlobKey();
                break;

            case THUMB:
                result = photo.getThumbBlobKey();
                break;

            default:
                throw new IllegalStateException("Unknown image type");
        }

        return result;
    }
}
