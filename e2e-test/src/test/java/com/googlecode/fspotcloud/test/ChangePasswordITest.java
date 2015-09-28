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

public class ChangePasswordITest {
	public static final String GNU_RULES = "GNU Rules!";
	@Rule
	public GuiceBerryRule guiceBerry = new GuiceBerryRule(
			EmptyGuiceBerryEnv.class);
	@Inject
	private LoginPage loginPage;
	@Inject
	private UserAccountPage userAccountPage;
	@Test
	public void testChangePassword() throws Exception {
		loginPage.open();
		loginPage.fillForm(ILogin.SLS, ILogin.SLS_CRED);
		loginPage.login();
		userAccountPage.verifyEmail(ILogin.SLS);
		userAccountPage.fillForm(ILogin.SLS_CRED, GNU_RULES);
		userAccountPage.save();
		userAccountPage.logout();
		loginPage.open();
		loginPage.fillForm(ILogin.SLS, GNU_RULES);
		loginPage.login();
		userAccountPage.verifyEmail(ILogin.SLS);
	}
}
