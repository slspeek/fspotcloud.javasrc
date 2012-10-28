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

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.googlecode.fspotcloud.test;

import com.googlecode.fspotcloud.client.main.view.SignUpPresenterImpl;
import com.thoughtworks.selenium.Selenium;

import javax.inject.Inject;

import static com.googlecode.fspotcloud.test.Sleep.sleepShort;
import static org.junit.Assert.assertEquals;

/**
 * @author steven
 */
public class SignUpPage {
    @Inject
    Selenium selenium;

    public void open() {
        selenium.open("/#SignUpPlace:");
        selenium.waitForPageToLoad("30000");
    }

    public void fillForm(String email, String credentials)
            throws InterruptedException {
        selenium.type("id=gwt-debug-password", credentials);
        selenium.type("id=gwt-debug-password-again", credentials);
        selenium.type("id=gwt-debug-email", email);
    }

    public void signUp() throws InterruptedException {
        selenium.click("gwt-debug-sign-up");
        selenium.waitForPageToLoad("30000");
        sleepShort();
    }

    public void verifySuccess() {
        assertEquals(SignUpPresenterImpl.SIGNED_UP_SUCCESSFULLY, getStatusText());
    }

    private String getStatusText() {
        return selenium.getText("id=gwt-debug-status");
    }

    public void verifyFailure() {
        assertEquals(SignUpPresenterImpl.SIGN_UP_FAILED, getStatusText());
    }

    public void verifyError() {
        assertEquals(SignUpPresenterImpl.AN_ERROR_PROHIBITED_YOUR_SIGN_UP,
                getStatusText());
    }
}
