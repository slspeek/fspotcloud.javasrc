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

package com.googlecode.fspotcloud.user.openid;

import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.user.ISessionEmail;
import com.googlecode.fspotcloud.user.LoginMetaData;
import com.googlecode.fspotcloud.user.LoginMetaDataUpdater;
import com.googlecode.fspotcloud.user.PostThirdPartyLoginWorker;

import javax.inject.Inject;
import javax.inject.Provider;


public class PostOpenIdLoginWorker implements PostThirdPartyLoginWorker {
    @Inject
    private UserDao userDao;
    @Inject
    private LoginMetaDataUpdater metaDataUpdater;
    @Inject
    private Provider<ISessionEmail> sessionEmail;

    @Override
    public void doWork() {
        String email = sessionEmail.get().getEmail();

        if (email != null) {
            com.googlecode.fspotcloud.server.model.api.User user = userDao.findOrNew(email);
            metaDataUpdater.doUpdate(user, LoginMetaData.Type.OPEN_ID_LOGIN);
        }
    }
}
