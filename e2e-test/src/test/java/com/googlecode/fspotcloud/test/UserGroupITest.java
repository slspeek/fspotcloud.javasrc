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
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;


public class UserGroupITest {
    public static final String LINUS_KERNEL_ORG = "linus@example.com";
    public static final String JEFF_GOOGLE_COM = "jeff@example.com";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    ManageUserGroupsPage manageUserGroupsPage;
    @Inject
    EditUserGroupPage editUserGroupPage;
    @Inject
    ILogin login;
    @Inject
    ManageUsersPage manageUsersPage;

    @Test
    public void createAndEditUserGroup() throws Exception {
        login.login();
        manageUserGroupsPage.open();
        manageUserGroupsPage.newUserGroup();
        manageUserGroupsPage.selectFirstRow();
        manageUserGroupsPage.editUserGroup();

        editUserGroupPage.fill("GNU Friends",
                "My friends from all over the world");
        editUserGroupPage.save();
        manageUserGroupsPage.open();
        manageUserGroupsPage.verifyText("My friends from all over the world");
        manageUserGroupsPage.selectFirstRow();
        manageUserGroupsPage.manageUserGroup();
        manageUsersPage.newUser(JEFF_GOOGLE_COM);
        manageUsersPage.newUser(LINUS_KERNEL_ORG);
        manageUsersPage.fillEmail("");
        manageUsersPage.verifyText(LINUS_KERNEL_ORG);
        manageUsersPage.selectFirstRow();
        manageUsersPage.deleteUser();
        manageUsersPage.verifyTextNotPresent(LINUS_KERNEL_ORG);

        manageUserGroupsPage.deleteUserGroup();
        manageUserGroupsPage.verifyTextNotPresent(
                "My friends from all over the world");
    }
}
