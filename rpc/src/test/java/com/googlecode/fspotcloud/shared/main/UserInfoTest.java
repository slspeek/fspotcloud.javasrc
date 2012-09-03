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

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.googlecode.fspotcloud.shared.main;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class UserInfoTest {
    /**
     * Test of isLoggedIn method, of class UserInfo.
     */
    UserInfo instance = new UserInfo("foo@bar.com", true, true,
            "index.jsp?dest=", "index.jsp?action=logout&dest=",
            new Date(1234567), "GAE_LOGIN");

    @Test
    public void testIsLoggedIn() {
        System.out.println("isLoggedIn");

        boolean expResult = true;
        boolean result = instance.isLoggedIn();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLogoutUrl method, of class UserInfo.
     */
    @Test
    public void testCreateLogoutUrl() {
        System.out.println("getLogoutUrl");

        String dest = "dest";
        String expResult = "index.jsp?action=logout&dest=";
        String result = instance.getLogoutUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLoginUrl method, of class UserInfo.
     */
    @Test
    public void testCreateLoginUrl() {
        System.out.println("getLoginUrl");

        String dest = "dest";
        String expResult = "index.jsp?dest=";
        String result = instance.getLoginUrl();
        assertEquals(expResult, result);
    }
}
