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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThreeFaseITest {
    @Rule
    public GuiceBerryRule guiceBerry = new GuiceBerryRule(EmptyGuiceBerryEnv.class);
    @Inject
    Selenium selenium;
    @Inject
    PeerRunner peerRunner;
    @Inject
    DashboardPage dashboardPage;
    @Inject
    PhotoPage photoPage;

    @Test
    public void test3Fase() throws Exception {
        peerRunner.startPeer("../peer/src/test/resources/photos_smaller.db");
        dashboardPage.loginAndOpen();
        dashboardPage.synchronize();
        dashboardPage.toggleImportForTagId("1"); //Furniture
        dashboardPage.toggleImportForTagId("3"); //Mac

        photoPage.open();
        //Assert something
        assertTrue(selenium.isTextPresent("Macintosh"));
        assertTrue(selenium.isTextPresent("Furniture"));

        photoPage.clickImage(0, 0);
        photoPage.assertPagingLabelSays(1, 5);

        peerRunner.stopPeer();
        peerRunner.startPeer("../peer/src/test/resources/photos.db");
        //Most pictures
        dashboardPage.open();
        dashboardPage.synchronize();
        photoPage.open();
        assertTrue(selenium.isTextPresent("Mac"));
        assertFalse(selenium.isTextPresent("Macintosh"));
        assertTrue(selenium.isTextPresent("Furniture"));
        //Assert something
        photoPage.clickImage(0, 0);
        photoPage.assertPagingLabelSays(1, 9);

        peerRunner.stopPeer();
        peerRunner.startPeer("../peer/src/test/resources/photos_smaller.db");
        dashboardPage.open();
        dashboardPage.synchronize();
        photoPage.open();
        //Assert something
        assertTrue(selenium.isTextPresent("Macintosh"));
        assertTrue(selenium.isTextPresent("Furniture"));
        photoPage.clickImage(0, 0);
        photoPage.assertPagingLabelSays(1, 5);
        //Less again
        peerRunner.stopPeer();
    }
}
