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


public class SimpleSignUpITest {
    public static final String RMS_FSF_ORG = "rms@example.com";
    public static final String CREDENTIALS = "ihp";
    public static final String MOOG_BB_ORG = "moog@bb.org";
    public static final String NSA = "nsa";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    SignUpPage signUpPage;
    @Inject
    LoginPage loginPage;
    @Inject
    UserAccountPage userAccountPage;
    @Inject
    PhotoPage photoPage;
    @Inject
    EmailConfirmationPage emailConfirmationPage;


    @Test
    public void signUp() throws Exception {
        signUpPage.open();
        signUpPage.fillForm(RMS_FSF_ORG, CREDENTIALS);
        signUpPage.signUp();
        signUpPage.verifySuccess();
        emailConfirmationPage.open(RMS_FSF_ORG).success();
        signUpPage.open();
        signUpPage.fillForm(RMS_FSF_ORG, CREDENTIALS);
        signUpPage.signUp();
        signUpPage.verifyFailure();
        signUpPage.open();
        signUpPage.fillForm(MOOG_BB_ORG, NSA);
        signUpPage.signUp();
        signUpPage.verifySuccess();
        emailConfirmationPage.open(MOOG_BB_ORG).success();

        loginPage.open();
        loginPage.fillForm("", "");
        loginPage.login();
        loginPage.verifyFailure();

        loginPage.open();
        loginPage.fillForm(MOOG_BB_ORG, NSA);
        loginPage.login();

        loginPage.open();
        loginPage.fillForm(RMS_FSF_ORG, CREDENTIALS);
        loginPage.login();

        userAccountPage.open();
        userAccountPage.verifyEmail(RMS_FSF_ORG);

        photoPage.open();
        photoPage.logout();

        userAccountPage.open();
        userAccountPage.verifyEmail("");
    }
}
