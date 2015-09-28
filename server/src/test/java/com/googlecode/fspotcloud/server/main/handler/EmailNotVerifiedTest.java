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
import com.googlecode.fspotcloud.shared.main.UpdateUserAction;
import com.googlecode.fspotcloud.shared.main.UpdateUserResult;
import com.googlecode.fspotcloud.user.UserService;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class EmailNotVerifiedTest {
	public static final String RMS_FSF_ORG = "rms@example.com";
	public static final String NEW_PASSWORD = "new password";
	public static final String OLD_PASSWORD = "old password";
	@Inject
	UpdateUserHandler handler;
	User user;

	@Before
	public void setUp(UserDao userDao, UserService userService)
			throws Exception {
		user = new UserEntity(RMS_FSF_ORG);
		user.setCredentials(OLD_PASSWORD);
		user.setRegistered(true);
		when(userDao.find(RMS_FSF_ORG)).thenReturn(user);
		when(userService.isUserLoggedIn()).thenReturn(true);
		when(userService.getEmail()).thenReturn(RMS_FSF_ORG);
	}

	@Test(expected = EmailNotVerifiedException.class)
	public void testExecute(UserDao userDao, UserService userService)
			throws Exception {
		UpdateUserAction action = new UpdateUserAction(NEW_PASSWORD,
				OLD_PASSWORD);
		UpdateUserResult result = handler.execute(action, null);
	}
}
