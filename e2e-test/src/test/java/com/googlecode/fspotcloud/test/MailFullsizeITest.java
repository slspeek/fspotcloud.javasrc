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

import static com.googlecode.fspotcloud.test.Sleep.sleepShort;

public class MailFullsizeITest {
    private static String SLS = "slspeek@gmail.com";
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    private PeerRunner peerRunner;
    @Inject
    private LoginPage loginPage;
    @Inject
    private PhotoPage photoPage;
    @Inject
    private SignUpPage signUpPage;

    @Test
    public void testMailImageToLeadDeveloper() throws Exception {
        peerRunner.startPeer("../peer/src/test/resources/photos.db");
        loginPage.open();
        loginPage.fillForm(SLS, SLS);
        loginPage.login();
        photoPage.open();
        photoPage.clickImage(0, 0);
        photoPage.mailFullsize();
        sleepShort(20);
        peerRunner.stopPeer();
    }
}
