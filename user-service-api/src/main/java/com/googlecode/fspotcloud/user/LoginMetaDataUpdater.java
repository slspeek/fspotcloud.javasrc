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

package com.googlecode.fspotcloud.user;

import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.HashSet;
import java.util.logging.Logger;

import static com.google.common.collect.Sets.newHashSet;

public class LoginMetaDataUpdater implements ILoginMetaDataUpdater {
    @Inject
    Logger log;
    @Inject
    private UserDao userDao;
    @Inject
    private Provider<LoginMetaData> loginMetaDataProvider;

    @Override
    public void doUpdate(User user, LoginMetaData.Type loginType) {
        LoginMetaData loginMetaData = loginMetaDataProvider.get();

        loginMetaData.setLastTime(user.getLastLoginTime());
        loginMetaData.setEmail(user.getEmail());
        loginMetaData.setLoginType(loginType);
        loginMetaData.setGrantedUserGroups(newHashSet(
                user.getGrantedUserGroups()));
        user.touchLastLoginTime();
        userDao.save(user);
        log.info("doUpdate on:" + loginMetaData);
    }

    @Override
    public void clear() {
        LoginMetaData loginMetaData = loginMetaDataProvider.get();
        loginMetaData.setLastTime(null);
        loginMetaData.setLoginType(null);
        loginMetaData.setEmail(null);

        HashSet<Long> userGroups = newHashSet();
        loginMetaData.setGrantedUserGroups(userGroups);
        log.info("clear on:" + loginMetaData);
    }
}
