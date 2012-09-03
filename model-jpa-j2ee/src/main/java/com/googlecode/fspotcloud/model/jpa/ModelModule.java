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
import com.google.inject.name.Names;
import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseManager;
import com.googlecode.fspotcloud.model.jpa.photo.PhotoManager;
import com.googlecode.fspotcloud.model.jpa.tag.TagManager;
import com.googlecode.fspotcloud.model.jpa.user.UserManager;
import com.googlecode.fspotcloud.model.jpa.usergroup.UserGroupManager;
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.simpleblobstore.j2ee.J2eeSimpleBlobstoreModule;
import com.googlecode.simplejpadao.EntityModule;


public class ModelModule extends AbstractModule {
    private int maxDelete;
    private final String persistenceUnit;

    public ModelModule(int maxDelete, String persistenceUnit) {
        this.maxDelete = maxDelete;
        this.persistenceUnit = persistenceUnit;
    }

    @Override
    protected void configure() {
        bind(PhotoDao.class).to(PhotoManager.class).in(Singleton.class);
        bind(PeerDatabaseDao.class).to(PeerDatabaseManager.class)
                .in(Singleton.class);
        bind(TagDao.class).to(TagManager.class).in(Singleton.class);
        bind(UserDao.class).to(UserManager.class).in(Singleton.class);
        bind(UserGroupDao.class).to(UserGroupManager.class).in(Singleton.class);
        bind(Integer.class).annotatedWith(Names.named("maxDelete"))
                .toInstance(maxDelete);
        install(new J2eeSimpleBlobstoreModule());
        install(new EntityModule(persistenceUnit));
    }
}
