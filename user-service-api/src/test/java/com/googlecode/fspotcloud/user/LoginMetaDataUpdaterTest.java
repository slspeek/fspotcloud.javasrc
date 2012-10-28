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

import com.googlecode.fspotcloud.model.jpa.user.UserEntity;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Date;


@RunWith(JukitoRunner.class)
public class LoginMetaDataUpdaterTest {
    @Inject
    LoginMetaDataUpdater updater;
    @Inject
    private UserDao userDao;
    @Inject
    private Provider<ILoginMetaData> lastLoginTimeProvider;

    @Test
    public void testDoUpdate() throws Exception {
        Date lastTime = new Date(987654321);
        User user = new UserEntity("rms@example.com");
        user.setLastLoginTime(lastTime);
        updater.doUpdate(user, LoginMetaData.Type.GAE_LOGIN);

        //        ILoginMetaData metaData = lastLoginTimeProvider.get();
        //        assertEquals(lastTime, metaData.getLastTime());
        //        assertEquals(LoginMetaData.Type.GAE_LOGIN, metaData.getLoginType());
        //        verify(userDao).save(user);
    }
}
