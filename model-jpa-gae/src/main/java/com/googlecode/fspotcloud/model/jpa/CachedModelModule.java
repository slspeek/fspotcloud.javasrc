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

package com.googlecode.fspotcloud.model.jpa;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.googlecode.fspotcloud.model.jpa.gae.GaeCacheProvider;
import com.googlecode.fspotcloud.model.jpa.gae.peerdatabase.CachedPeerDatabaseManager;
import com.googlecode.fspotcloud.model.jpa.gae.peerdatabase.PeerDatabaseEntity;
import com.googlecode.fspotcloud.model.jpa.gae.peerdatabase.PeerDatabaseManager;
import com.googlecode.fspotcloud.model.jpa.gae.photo.CachedPhotoManager;
import com.googlecode.fspotcloud.model.jpa.gae.photo.PhotoEntity;
import com.googlecode.fspotcloud.model.jpa.gae.photo.PhotoManager;
import com.googlecode.fspotcloud.model.jpa.gae.tag.CachedTagManager;
import com.googlecode.fspotcloud.model.jpa.gae.tag.TagEntity;
import com.googlecode.fspotcloud.model.jpa.gae.tag.TagManager;
import com.googlecode.fspotcloud.model.jpa.gae.user.UserManager;
import com.googlecode.fspotcloud.model.jpa.gae.usergroup.UserGroupManager;
import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseManagerBase;
import com.googlecode.fspotcloud.model.jpa.photo.PhotoManagerBase;
import com.googlecode.fspotcloud.model.jpa.tag.TagManagerBase;
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.simpleblobstore.gae.GaeSimpleBlobstoreModule;
import com.googlecode.simplejpadao.EntityModule;
import net.sf.jsr107cache.Cache;


public class CachedModelModule extends AbstractModule {
    private final int maxDelete;
    private final String persistenceUnit;

    public CachedModelModule(int maxDelete, String persistenceUnit) {
        this.maxDelete = maxDelete;
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    protected void configure() {
        bind(new TypeLiteral<PhotoManagerBase<Photo, PhotoEntity>>() {
        }).to(PhotoManager.class);
        bind(new TypeLiteral<TagManagerBase<Tag, TagEntity>>() {
        }).to(TagManager.class);
        bind(new TypeLiteral<PeerDatabaseManagerBase<PeerDatabase, PeerDatabaseEntity>>() {
        }).to(PeerDatabaseManager.class);

        bind(PhotoDao.class).to(CachedPhotoManager.class).in(Singleton.class);
        bind(PeerDatabaseDao.class).to(CachedPeerDatabaseManager.class)
                .in(Singleton.class);
        bind(TagDao.class).to(CachedTagManager.class).in(Singleton.class);
        bind(UserDao.class).to(UserManager.class).in(Singleton.class);
        bind(UserGroupDao.class).to(UserGroupManager.class).in(Singleton.class);
        bind(Integer.class).annotatedWith(Names.named("maxDelete"))
                .toInstance(maxDelete);
        bind(Cache.class).toProvider(GaeCacheProvider.class); //.in(Singleton.class);
        install(new GaeSimpleBlobstoreModule());
        install(new EntityModule(persistenceUnit));
    }
}
