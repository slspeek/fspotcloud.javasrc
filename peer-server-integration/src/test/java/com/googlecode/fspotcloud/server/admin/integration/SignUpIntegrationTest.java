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
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.shared.main.SignUpAction;
import com.googlecode.fspotcloud.shared.main.SignUpResult;
import net.customware.gwt.dispatch.server.Dispatch;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class SignUpIntegrationTest {
    static final Logger log = Logger.getLogger(SignUpIntegrationTest.class.getName());
    public static final String RMS_FSF_ORG1 = "rms@example.com";
    public static final String RMS_FSF_ORG = RMS_FSF_ORG1;
    private TearDown toTearDown;
    @Inject
    UserDao userDao;
    @Inject
    Dispatch dispatch;

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
    public void signUp() throws Exception {
        SignUpAction action = new SignUpAction(RMS_FSF_ORG1, "ihp", "rms");
        SignUpResult result = dispatch.execute(action);
        assertTrue(result.getSuccess());
    }

    @Test
    public void signUpTwice() throws Exception {
        SignUpAction action = new SignUpAction(RMS_FSF_ORG1, "ihp", "rms");
        SignUpResult result = dispatch.execute(action);
        assertTrue(result.getSuccess());
        action = new SignUpAction(RMS_FSF_ORG1, "ihp", "rms");
        result = dispatch.execute(action);
        assertFalse(result.getSuccess());
    }
}
