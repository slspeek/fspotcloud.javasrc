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

import static com.googlecode.fspotcloud.test.Sleep.sleepShort;
import static org.junit.Assert.assertEquals;

/**
 * @author steven
 */
public class DashboardPage {
    @Inject
    Selenium selenium;
    @Inject
    ILogin login;

    public void open() {
        selenium.open("#DashboardPlace:1");
        selenium.waitForPageToLoad("30000");
    }

    public void loginAndOpen() throws Exception {
        login.login();
        open();
    }

    public void manageUsergroups() {
        selenium.click("gwt-debug-manage-groups-button");
        selenium.waitForPageToLoad("30000");
    }

    public void synchronize() throws InterruptedException {
        selenium.click("gwt-debug-update-button");
        sleepShort(4);

    }

    public void toggleImportForTagId(String id) throws InterruptedException {
        selenium.open("#DashboardPlace:" + id);
        selenium.refresh();
        selenium.waitForPageToLoad("30000");
        selenium.click("gwt-debug-import-tag-button");
        sleepShort(4);
    }

    public void manageApprovalForTag(String id) throws InterruptedException {
        selenium.open("#DashboardPlace:" + id);
        selenium.waitForPageToLoad("30000");
        selenium.refresh();
        selenium.click("gwt-debug-manage-access-button");
    }

    void removeAll() throws InterruptedException {
        selenium.click("gwt-debug-delete-all-tags-button");
        sleepShort();
        selenium.click("gwt-debug-confirm-ok");
        sleepShort();
    }

    void assertPhotoCountOnPeer(int count) {
        assertEquals(count, Integer.parseInt(selenium.getText("gwt-debug-peer-photo-count-label"), 10));
    }


    void assertTagCountOnPeer(int count) {
        assertEquals(count, Integer.parseInt(selenium.getText("gwt-debug-peer-tag-count-label"), 10));
    }

}
