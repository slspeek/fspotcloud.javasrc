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

package com.googlecode.fspotcloud.server.admin.integration;

import com.google.common.testing.TearDown;
import com.google.guiceberry.testng.TestNgGuiceBerry;
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.fspotcloud.shared.main.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.sql.SQLException;

import static org.testng.AssertJUnit.*;

public class UserGroupLifecycleTest extends PeerServerEnvironment {
    public static final String TAG_ID_3 = "3";
    public static final String RMS_EXAMPLE_COM = "rms@example.com";
    private TearDown toTearDown;
    @Inject
    Fixture fixture;
    @Inject
    private UserGroupDao userGroupDao;
    @Inject
    private UserDao userDao;

    @BeforeMethod
    public void setUp(Method m) throws SQLException {
        // Make this the call to TestNgGuiceBerry.setUp as early as possible
        toTearDown = TestNgGuiceBerry.setUp(this, m,
                NoAuthPlaceHolderIntegrationModule.class);

        userDao.deleteBulk(100);
        assertTrue(userDao.isEmpty());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        // Make this the call to TestNgGuiceBerry.tearDown as late as possible
        toTearDown.tearDown();
    }

    @Test
    public void testCreationAndTagApproval() throws Exception {
        fixture.setUpFixture();

        GetUserGroupResult r = dispatch.execute(new NewUserGroupAction());
        Long id = r.getInfo().getId();
        dispatch.execute(new ApproveTagAction(TAG_ID_3, id));

        Tag tag3 = tagDao.find(TAG_ID_3);
        assertTrue(tag3.getApprovedUserGroups().contains(id));

        UserGroup userGroup = userGroupDao.find(id);
        assertTrue(userGroup.getApprovedTagIds().contains(TAG_ID_3));

        dispatch.execute(new DeleteUserGroupAction(id));

        tag3 = tagDao.find(TAG_ID_3);
        assertFalse(tag3.getApprovedUserGroups().contains(id));
        assertNull(userGroupDao.find(id));
    }

    @Test
    public void testCreationAndUserAdding() throws Exception {
        fixture.setUpFixture();

        SignUpAction action = new SignUpAction(RMS_EXAMPLE_COM, "ihp", "rms");
        SignUpResult result = dispatch.execute(action);
        assertTrue(result.getSuccess());

        GetUserGroupResult r = dispatch.execute(new NewUserGroupAction());
        Long id = r.getInfo().getId();
        dispatch.execute(new GrantUserAction(RMS_EXAMPLE_COM, id));

        User rms = userDao.find(RMS_EXAMPLE_COM);
        assertTrue(rms.getGrantedUserGroups().contains(id));

        UserGroup userGroup = userGroupDao.find(id);
        assertTrue(userGroup.getGrantedUsers().contains(RMS_EXAMPLE_COM));

        dispatch.execute(new DeleteUserGroupAction(id));

        rms = userDao.find(RMS_EXAMPLE_COM);
        assertFalse(rms.getGrantedUserGroups().contains(id));
        assertNull(userGroupDao.find(id));
    }
}
