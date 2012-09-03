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

import static com.googlecode.fspotcloud.test.Sleep.sleepShort;

public class PrivateAccessITest {
    public static final String SLSPEEK_GMAIL_COM = "slspeek@gmail.com";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    Selenium selenium;
    @Inject
    DashboardPage dashboardPage;
    @Inject
    TagApprovalPage tagApprovalPage;
    @Inject
    MyUserGroupsPage myUserGroupsPage;
    @Inject
    EditUserGroupPage editUserGroupPage;
    @Inject
    private ManageUsersPage manageUsersPage;
    @Inject
    private PhotoPage photoPage;
    @Inject
    private LoginPage loginPage;
    @Inject
    private SignUpPage signUpPage;
    @Inject
    private UserAccountPage userAccountPage;
    @Inject
    private EmailConfirmationPage emailConfirmationPage;

    @Test
    public void testAccess() throws Exception {
        dashboardPage.loginAndOpen();
        dashboardPage.manageUsergroups();
        myUserGroupsPage.open();
        myUserGroupsPage.newUserGroup();
        myUserGroupsPage.newUserGroup();
        myUserGroupsPage.newUserGroup();
        myUserGroupsPage.selectFirstRow();
        myUserGroupsPage.editUserGroup();

        editUserGroupPage.fill("GNU Friends",
                "My friends from all over the world");
        editUserGroupPage.save();
        myUserGroupsPage.open();
        myUserGroupsPage.selectFirstRow();
        myUserGroupsPage.manageUserGroup();
        manageUsersPage.newUser(SLSPEEK_GMAIL_COM);
        dashboardPage.open();
        dashboardPage.manageApprovalForTag("1");
        sleepShort();
        tagApprovalPage.selectTopDeniedGroup();
        tagApprovalPage.approveUserGroup();
        photoPage.open();
        photoPage.logout();
        userAccountPage.open();
        signUpPage.open();
        signUpPage.fillForm(SLSPEEK_GMAIL_COM, SLSPEEK_GMAIL_COM);
        signUpPage.signUp();
        emailConfirmationPage.open(SLSPEEK_GMAIL_COM).success();
        photoPage.open();
        loginPage.open();
        loginPage.fillForm(SLSPEEK_GMAIL_COM, SLSPEEK_GMAIL_COM);
        loginPage.login();
        userAccountPage.open();
        photoPage.open();
        photoPage.clickImage(0, 0);
        photoPage.assertPagingLabelSays(1, 9);
        photoPage.logout();
        dashboardPage.loginAndOpen();
        myUserGroupsPage.open();
        myUserGroupsPage.selectFirstRow();
        myUserGroupsPage.editUserGroup();
        editUserGroupPage.togglePublic();
        editUserGroupPage.save();
        photoPage.open();
        photoPage.logout();
        userAccountPage.open();
        photoPage.open();
        sleepShort();
        photoPage.clickImage(0, 0);
        photoPage.assertPagingLabelSays(1, 9);
    }
}
