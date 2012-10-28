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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author steven
 */
public class MyUserGroupsPage {
    @Inject
    Selenium selenium;

    public void open() {
        selenium.open("/#MyUserGroupsPlace:");
        selenium.waitForPageToLoad("30000");
    }

    public void newUserGroup() {
        selenium.click("gwt-debug-new-button");
        selenium.waitForPageToLoad("30000");
    }

    public void deleteUserGroup() {
        selenium.click("gwt-debug-delete-button");
        selenium.waitForPageToLoad("30000");
    }

    public void manageUserGroup() {
        selenium.click("gwt-debug-manage-button");
        selenium.waitForPageToLoad("30000");
    }

    public void editUserGroup() {
        selenium.click("gwt-debug-edit-button");
        selenium.waitForPageToLoad("30000");
    }

    public void verifyText(String text) {
        assertTrue(selenium.getBodyText().contains(text));
    }

    public void verifyTextNotPresent(String text) {
        assertFalse(selenium.getBodyText().contains(text));
    }

    public void selectFirstRow() {
        selenium.click("//td/div");
    }
}
