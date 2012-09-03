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
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.AuthenticationAction;
import com.googlecode.fspotcloud.shared.main.AuthenticationResult;
import com.googlecode.fspotcloud.shared.main.SignUpAction;
import com.googlecode.fspotcloud.shared.main.SignUpResult;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.Dispatch;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class AuthenticationIntegrationTest {
    static final Logger log = Logger.getLogger(AuthenticationIntegrationTest.class.getName());
    public static final String RMS_FSF_ORG = "rms@example.com";
    private TearDown toTearDown;
    @Inject
    UserDao userDao;
    @Inject
    Dispatch dispatch;
    @Inject
    UserService userService;

    @BeforeMethod
    public void setUp(Method m) throws SQLException {
        // Make this the call to TestNgGuiceBerry.setUp as early as possible
        toTearDown = TestNgGuiceBerry.setUp(this, m,
                PlaceHolderIntegrationModule.class);

        userDao.deleteBulk(100);
        assertTrue(userDao.isEmpty());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        // Make this the call to TestNgGuiceBerry.tearDown as late as possible
        toTearDown.tearDown();
    }

    @Test
    public void signUpAndLogin() throws Exception {
        SignUpAction action = new SignUpAction(RMS_FSF_ORG, "ihp", "rms");
        SignUpResult result = dispatch.execute(action);
        assertTrue(result.getSuccess());

        //Bypassing email-confirmation
        User rms = userDao.find(RMS_FSF_ORG);
        rms.setEnabled(true);
        userDao.save(rms);

        AuthenticationAction authenticationAction = new AuthenticationAction(RMS_FSF_ORG,
                "ihp");
        AuthenticationResult authenticationResult = dispatch.execute(authenticationAction);
        assertTrue(authenticationResult.getSuccess());
        assertEquals(RMS_FSF_ORG, userService.getEmail());
        Assert.assertTrue(userService.isUserAdmin());
    }
}
