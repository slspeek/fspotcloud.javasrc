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

package com.googlecode.fspotcloud.test;

import com.google.guiceberry.junit4.GuiceBerryRule;
import com.thoughtworks.selenium.Selenium;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import java.util.logging.Logger;

import static com.googlecode.fspotcloud.test.Sleep.sleepShort;

public class PrivateAccessITest {
    public static final String SLSPEEK_GMAIL_COM = "slspeek@gmail.com";
    public static final Logger log = Logger.getLogger(PrivateAccessITest.class.getName());
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    Selenium selenium;
    @Inject
    DashboardPage dashboardPage;
    @Inject
    TagApprovalPage tagApprovalPage;
    @Inject
    ManageUserGroupsPage manageUserGroupsPage;
    @Inject
    EditUserGroupPage editUserGroupPage;
    @Inject
    private ManageUsersPage manageUsersPage;
    @Inject
    private PhotoPage photoPage;
    @Inject
    private LoginPage loginPage;
    @Inject
    private UserAccountPage userAccountPage;


    @Test
    public void testAccess() throws Exception {
        dashboardPage.loginAndOpen();
        dashboardPage.manageUsergroups();
        manageUserGroupsPage.open();
        manageUserGroupsPage.newUserGroup();
        manageUserGroupsPage.selectFirstRow();
        manageUserGroupsPage.editUserGroup();

        editUserGroupPage.fill("GNU Friends",
                "My friends from all over the world");

        editUserGroupPage.save();
        manageUserGroupsPage.open();
        manageUserGroupsPage.selectFirstRow();
        manageUserGroupsPage.manageUserGroup();
        manageUsersPage.newUser(ILogin.SLS);
        dashboardPage.manageApprovalForTag("1");
        sleepShort();
        tagApprovalPage.selectTopDeniedGroup();
        tagApprovalPage.approveUserGroup();
        photoPage.open();
        photoPage.logout();
        photoPage.open();
        photoPage.open("#BasePlace:1:7:2:2:false");
        sleepShort();
        //loginPage.open();
        loginPage.fillForm(ILogin.SLS, ILogin.SLS_CRED);
        loginPage.login();
        //photoPage.open();
        photoPage.assertPagingLabelSays(1, 3);
        photoPage.logout();
        dashboardPage.loginAndOpen();
        manageUserGroupsPage.open();
        manageUserGroupsPage.selectFirstRow();
        manageUserGroupsPage.editUserGroup();
        editUserGroupPage.togglePublic();
        editUserGroupPage.save();
        photoPage.open();
        photoPage.logout();
        userAccountPage.open();
        photoPage.open();
        sleepShort();
        log.info("Before click 0,0");
        photoPage.clickImage(0, 0);
        log.info("After click 0,0");
        sleepShort();
        photoPage.assertPagingLabelSays(1, 9);
    }
}
