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
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import static com.googlecode.fspotcloud.server.util.DigestTool.hash;
import static com.googlecode.fspotcloud.test.Sleep.sleepShort;

public class EmailConfirmationITest {

	public static final String SECRET = "ControlledInject";
	@Rule
	public GuiceBerryRule guiceBerry = new GuiceBerryRule(
			EmptyGuiceBerryEnv.class);
	@Inject
	private EmailConfirmationPage emailConfirmationPage;
	@Inject
	private UserDao userDao;

	@Test
	public void testEmailConfirmation() throws Exception {
		User rms = userDao.findOrNew(ILogin.RMS_FSF_ORG);
		rms.setCredentials(hash(ILogin.RMS_FSF_ORG, ILogin.RMS_CRED));
		rms.setRegistered(true);
		rms.setEmailVerificationSecret(SECRET);
		rms.setEnabled(false);
		userDao.save(rms);
		emailConfirmationPage.open(ILogin.RMS_FSF_ORG, SECRET);
		emailConfirmationPage.open(ILogin.RMS_FSF_ORG, SECRET);
		sleepShort();
		emailConfirmationPage.success();
	}
}
