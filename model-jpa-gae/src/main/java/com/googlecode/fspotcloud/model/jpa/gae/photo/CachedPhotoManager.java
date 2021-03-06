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

package com.googlecode.fspotcloud.model.jpa.gae.photo;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.model.jpa.photo.CachedPhotoManagerBase;
import com.googlecode.fspotcloud.model.jpa.photo.PhotoManagerBase;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.simplejpadao.AbstractDAO;


public class CachedPhotoManager extends CachedPhotoManagerBase<Photo, PhotoEntity>
        implements PhotoDao {
    @Inject
    PhotoManagerBase<Photo, PhotoEntity> delegate;

    @Override
    protected Photo newPhoto() {
        return new PhotoEntity();
    }

    @Override
    public Class<PhotoEntity> getEntityType() {
        return PhotoEntity.class;
    }

    @Override
    public AbstractDAO<Photo, String> getDelegate() {
        return delegate;
    }
}
