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

package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.model.jpa.user.UserEntity;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.SignUpAction;
import com.googlecode.fspotcloud.shared.main.SignUpResult;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class SignUpHandlerTest {
    public static final String RMS_FSF_ORG = "rms@example.com";
    @Inject
    SignUpHandler handler;
    User user;

    @Before
    public void setUp(UserDao userDao) throws Exception {
        user = new UserEntity(RMS_FSF_ORG);
        when(userDao.findOrNew(RMS_FSF_ORG)).thenReturn(user);
    }

    @Test
    public void testExecute(UserDao userDao) throws Exception {
        SignUpAction action = new SignUpAction(RMS_FSF_ORG, "np", "rms");
        SignUpResult result = handler.execute(action, null);
        assertTrue(result.getSuccess());
    }

    @Test
    public void userExists(UserDao userDao) throws Exception {
        user.setRegistered(true);

        SignUpAction action = new SignUpAction(RMS_FSF_ORG, "np", "rms");
        SignUpResult result = handler.execute(action, null);
        assertFalse(result.getSuccess());
    }
}
