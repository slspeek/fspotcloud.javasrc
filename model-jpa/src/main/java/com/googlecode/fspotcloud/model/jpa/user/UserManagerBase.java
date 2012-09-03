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

package com.googlecode.fspotcloud.model.jpa.user;

import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.simplejpadao.SimpleDAONamedIdImpl;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.google.common.collect.Sets.newHashSet;

public abstract class UserManagerBase<T extends User, U extends T>
        extends SimpleDAONamedIdImpl<User, U, String> implements UserDao {
    private final Logger log = Logger.getLogger(UserManagerBase.class.getName());

    protected void detach(User user) {
        Set<Long> uG = user.getGrantedUserGroups();

        if (uG != null) {
            user.setGrantedUserGroups(newHashSet(uG));
        }
    }

    @Override
    public User tryToLogin(String email, String credentials) {
        List<User> tryToFind = findAllWhere(1,
                "c.id = '" + email + "' AND c.credentials = '" + credentials +
                        "'");

        if (tryToFind.size() > 0) {
            return tryToFind.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User newEntity(String key) {
        return newUser(key);
    }

    protected abstract User newUser(String email);
}
