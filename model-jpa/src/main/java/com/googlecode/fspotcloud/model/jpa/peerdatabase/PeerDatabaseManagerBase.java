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

package com.googlecode.fspotcloud.model.jpa.peerdatabase;

import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.simplejpadao.SimpleDAONamedIdImpl;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class PeerDatabaseManagerBase<T extends PeerDatabase, U extends T>
        extends SimpleDAONamedIdImpl<PeerDatabase, U, String>
        implements PeerDatabaseDao {
    private static final String DEFAULT_PEER_ID = "1";
    private final Logger log = Logger.getLogger(PeerDatabaseManagerBase.class.getName());

    public T get() {
        T peer;
        peer = getInstance();
        //log.info("get returns: " + peer);
        return peer;
    }

    private T getInstance() {
        EntityManager pm = entityManagerProvider.get();
        pm.getTransaction().begin();

        T peerDatabase;
        peerDatabase = (T) pm.find(getEntityType(), DEFAULT_PEER_ID);

        if (peerDatabase == null) {
            peerDatabase = newInstance();
            peerDatabase.setId(DEFAULT_PEER_ID);
            peerDatabase.setPeerPhotoCount(0);
            peerDatabase.setPhotoCount(0);
            peerDatabase.setTagCount(0);
            peerDatabase.setPeerName("No given name");
            peerDatabase.setPeerLastContact(new Date(0));
            pm.persist(peerDatabase);
        }

        pm.getTransaction().commit();

        return peerDatabase;
    }

    public void touchPeerContact() {
        T dp = get();
        dp.setPeerLastContact(new Date());
        save(dp);
    }

    @Override
    public void resetCachedTagTrees() {
        T dp = get();
        TagNode tree = dp.getCachedTagTree();
        TagNode admin = dp.getCachedAdminTagTree();

        if (tree != null || admin !=null ) {
            dp.setCachedTagTree(null);
            dp.setCachedAdminTagTree(null);
            save(dp);

            log.log(Level.FINEST,  "TagTree RESET (was:" + tree + ")");
        }
    }

    protected abstract T newInstance();
}
