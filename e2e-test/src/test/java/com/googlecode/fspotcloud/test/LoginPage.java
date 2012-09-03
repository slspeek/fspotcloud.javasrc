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

import com.googlecode.fspotcloud.client.main.view.LoginPresenterImpl;
import com.thoughtworks.selenium.Selenium;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * @author steven
 */
public class LoginPage {
    @Inject
    Selenium selenium;

    public void open() {
        selenium.open("/#LoginPlace:");
        selenium.waitForPageToLoad("30000");
    }

    public void fillForm(String email, String credentials)
            throws InterruptedException {
        selenium.type("id=gwt-debug-password", credentials);
        selenium.type("id=gwt-debug-username", email);
    }

    public void login() throws InterruptedException {
        selenium.click("gwt-debug-login");
        selenium.waitForPageToLoad("30000");
    }

    public void clickGoogleLogin() throws InterruptedException {
        selenium.click("gwt-debug-google-login");
        selenium.waitForPageToLoad("30000");
    }

    private String getStatusText() {
        return selenium.getText("gwt-debug-status");
    }

    public void verifyFailure() {
        assertEquals(LoginPresenterImpl.NOT_A_VALID_USERNAME_AND_PASSWORD_COMBINATION,
                getStatusText());
    }

    public void verifyError() {
        assertEquals(LoginPresenterImpl.AN_ERROR_OCCURRED_MAKING_THE_AUTHENTICATION_REQUEST,
                getStatusText());
    }
}
