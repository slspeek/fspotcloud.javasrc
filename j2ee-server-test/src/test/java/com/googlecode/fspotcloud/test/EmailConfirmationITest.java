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

import com.google.guiceberry.controllable.InjectionController;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.googlecode.fspotcloud.user.emailconfirmation.SecretGenerator;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

public class EmailConfirmationITest {

    public static final String CONTROLLED_INJECT = "ControlledInject";
    public static final String RMS_FSF_ORG = "rms@example.com";
    public static final String CREDENTIALS = "ihp";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    DashboardPage dashboardPage;
    @Inject
    private LoginPage loginPage;
    @Inject
    private SignUpPage signUpPage;
    @Inject
    private EmailConfirmationPage emailConfirmationPage;
    @com.google.inject.Inject
    InjectionController<SecretGenerator> secretGeneratorInjectionController;


    @Test
    public void testEmailConfirmation() throws Exception {
        secretGeneratorInjectionController.setOverride(new SecretGenerator() {
            @Override
            public String getSecret(String user) {
                return CONTROLLED_INJECT;
            }
        });
        signUpPage.open();
        signUpPage.fillForm(RMS_FSF_ORG, CREDENTIALS);
        signUpPage.signUp();
        emailConfirmationPage.open(RMS_FSF_ORG, CONTROLLED_INJECT).success();
        loginPage.open();
        loginPage.fillForm(RMS_FSF_ORG, CREDENTIALS);
        loginPage.login();
    }
}
