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

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.inject.Inject;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * @author steven
 */
public class UserAccountPage {
    @Inject
    WebDriverBackedSelenium selenium;

    public UserAccountPage open() {
        selenium.open("#UserAccountPlace:");
        selenium.waitForPageToLoad("30000");
        return this;
    }

    public void verifyEmail(String email) {
        assertEquals(email, selenium.getText("gwt-debug-email"));
    }

    public Date getLastLogin() {
        return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_LONG)
                .parse(selenium.getText("gwt-debug-last-login"));
    }

    public void fillForm(String oldPassword, String newPassword) {
        selenium.type("id=gwt-debug-old-password", oldPassword);
        selenium.type("id=gwt-debug-new-password", newPassword);
        selenium.type("id=gwt-debug-new-password-again", newPassword);
    }

    public void save() {
        selenium.click("gwt-debug-do-change-password");
    }

    public void logout() {
        selenium.click("gwt-debug-logout");
    }

}
