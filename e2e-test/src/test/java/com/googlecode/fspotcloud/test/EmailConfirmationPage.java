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
package com.googlecode.fspotcloud.test;

import com.thoughtworks.selenium.Selenium;

import javax.inject.Inject;

import static junit.framework.Assert.assertTrue;

/**
 * @author steven
 */
public class EmailConfirmationPage {
    @Inject
    Selenium selenium;

    public EmailConfirmationPage open(String email) {
        return open(email, "thiswillgetharder");
    }

    public EmailConfirmationPage open(String email, String secret) {
        selenium.open("/#EmailConfirmationPlace:" + email + ":" + secret);
        selenium.waitForPageToLoad("30000");
        return this;
    }

    public void success() {
        String body = selenium.getBodyText();
        assertTrue(body.contains("Success"));
    }
}
