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

import javax.inject.Inject;


public class RegularLoginBot implements ILogin {
    public static final String RMS_FSF_ORG = "rms@example.com";
    public static final String CREDENTIALS = "ihp";
    @Inject
    LoginPage loginPage;
    @Inject
    SignUpPage signUpPage;

    @Override
    public void login() throws Exception {
        signUpPage.open();
        signUpPage.fillForm(RMS_FSF_ORG, CREDENTIALS);
        signUpPage.signUp();
        loginPage.open();
        loginPage.fillForm(RMS_FSF_ORG, CREDENTIALS);
        loginPage.login();
    }
}
