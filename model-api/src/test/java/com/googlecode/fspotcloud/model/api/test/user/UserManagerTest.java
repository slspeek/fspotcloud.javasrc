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

package com.googlecode.fspotcloud.model.api.test.user;

import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.model.api.test.EmptyGuiceBerryEnv;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class UserManagerTest {
    private final Logger log = Logger.getLogger(UserManagerTest.class.getName());
    public static final String EMAIL = "douglas@yahoo.com";
    public static final String JSLINT = "jslint";
    public static final String IHATEPASSOWRDS = "ihatepassowrds";
    public static final String RMS_FSF_ORG = "rms@example.com";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    private UserDao userManager;

    @Before
    public void assertEmpty() {
        assertTrue(userManager.isEmpty());
    }

    @After
    public void cleanUp() throws Exception {
        userManager.deleteBulk(100);
    }

    @Test
    public void testLogin() throws Exception {
        // userManager.newPeristentEntity();
        User user;

        user = userManager.newEntity(RMS_FSF_ORG);
        user.setCredentials(IHATEPASSOWRDS);
        userManager.save(user);

        user = userManager.newEntity("foo");
        user.setEmail(EMAIL);
        user.setCredentials(JSLINT);
        userManager.save(user);
        user = userManager.newEntity("foo");
        user.setEmail(EMAIL);
        user.setCredentials(JSLINT);
        userManager.save(user);
        assertEquals(2, userManager.count(10));

        user = null;
        user = userManager.tryToLogin(EMAIL, JSLINT);
        assertNotNull(user);

        user = null;
        user = userManager.tryToLogin(RMS_FSF_ORG, IHATEPASSOWRDS);
        assertNotNull(user);
    }
}
