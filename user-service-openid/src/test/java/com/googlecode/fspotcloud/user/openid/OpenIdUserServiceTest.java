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

import com.google.inject.Inject;
import com.googlecode.fspotcloud.user.ILoginMetaData;
import com.googlecode.fspotcloud.user.ISessionEmail;
import com.googlecode.fspotcloud.user.inject.ServerAddress;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class OpenIdUserServiceTest {
    public static final String FOO_BAR_COM = "foo@bar.com";
    public static final String SLSPEEK_GMAIL_COM = "slspeek@gmail.com";
    @Inject
    OpenIdUserService instance;
    @Inject
    ILoginMetaData metaData;

    @After
    public void tearDown() {
    }

    /**
     * Test of createLoginURL method, of class OpenIdUserService.
     */
    @Test
    public void testCreateLoginURL() {
        String destinationURL = "dest";
        String expResult = "index.jsp?dest=http://localhost:8080/context/post-login";
        String result = instance.getThirdPartyLoginURL();
        assertEquals(expResult, result);
    }

    /**
     * Test of createLogoutURL method, of class OpenIdUserService.
     */
    @Test
    public void testCreateLogoutURL() {
        String destinationURL = "dest";
        String expResult = "index.jsp?logout=true&dest=http://localhost:8080/context/FSpotCloud.html";
        String result = instance.getThirdPartyLogoutURL();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTagId method, of class OpenIdUserService.
     *
     * @param session DOCUMENT ME!
     */
    @Test
    public void testGetEmailNull(ISessionEmail sessionEmail) {
        when(metaData.getEmail()).thenReturn(null);

        String expResult = null;
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetEmailFoo() {
        when(metaData.getEmail()).thenReturn(FOO_BAR_COM);

        String expResult = FOO_BAR_COM;
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of isUserLoggedIn method, of class OpenIdUserService.
     */
    @Test
    public void testIsUserLoggedIn() {
        when(metaData.getEmail()).thenReturn(FOO_BAR_COM);

        boolean expResult = true;
        boolean result = instance.isUserLoggedIn();
        assertEquals(expResult, result);
    }

    /**
     * Test of isUserAdmin method, of class OpenIdUserService.
     */
    @Test
    public void testIsNotUserAdmin() {
        System.out.println("isUserAdmin");

        boolean expResult = false;
        boolean result = instance.isUserAdmin();
        assertEquals(expResult, result);
    }

    /**
     * Test of isUserAdmin method, of class OpenIdUserService.
     *
     * @param session DOCUMENT ME!
     */
    @Test
    public void testIsUserAdmin() {
        when(metaData.getEmail()).thenReturn(SLSPEEK_GMAIL_COM);

        boolean expResult = true;
        boolean result = instance.isUserAdmin();
        assertEquals(expResult, result);
    }

    public static class Module extends JukitoModule {
        protected void configureTest() {
            bind(String.class).annotatedWith(AdminEmail.class)
                    .toInstance(SLSPEEK_GMAIL_COM);
            bind(String.class).annotatedWith(ServerAddress.class)
                    .toInstance("http://localhost:8080/context");
        }
    }
}
