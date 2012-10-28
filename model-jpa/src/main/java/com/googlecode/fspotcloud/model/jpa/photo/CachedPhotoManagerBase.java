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
import com.googlecode.simplejpadao.cacheddao.CachedSimpleDAONamedIdImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public abstract class CachedPhotoManagerBase<T extends Photo, U extends T>
        extends CachedSimpleDAONamedIdImpl<Photo, U, String> implements PhotoDao {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(CachedPhotoManagerBase.class.getName());

    private void detach(Photo photo) {
        List<String> tagList = photo.getTagList();
        photo.setTagList(new ArrayList<String>(tagList));
    }

    @Override
    public Photo find(String key) {
        EntityManager em = entityManagerProvider.get();
        em.getTransaction().begin();

        Photo attachted = em.find(getEntityType(), key);

        if (attachted != null) {
            detach(attachted);
        }

        em.getTransaction().commit();

        return attachted;
    }

    @Override
    public List<Photo> findAll(int max) {
        EntityManager em = entityManagerProvider.get();
        em.getTransaction().begin();

        try {
            Query query = em.createQuery("select c from " +
                    getEntityType().getName() + " AS c");
            query.setMaxResults(max);

            @SuppressWarnings("unchecked")
            List<Photo> rs = (List<Photo>) query.getResultList();
            List<Photo> result = new ArrayList<Photo>();
            result.addAll(rs);

            final List<Photo> all = result;

            for (Photo photo : all) {
                detach(photo);
            }

            em.getTransaction().commit();

            return all;
        } finally {
            em.close();
        }
    }

    protected abstract Photo newPhoto();
}
