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

import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.keyboardaction.SeleniumPerformer;
import com.thoughtworks.selenium.Selenium;

import javax.inject.Inject;

import static junit.framework.Assert.assertTrue;

/**
 * @author steven
 */
public class SendPasswordResetPage {
    @Inject
    private SeleniumPerformer performer;
    @Inject
    private Selenium selenium;
    @Inject
    private UserActions userActions;

    public SendPasswordResetPage open() {
        selenium.open("/#SendPasswordResetPlace:");
        selenium.waitForPageToLoad("30000");
        return this;
    }

    public void submitEmail(String email) {
        selenium.type("id=gwt-debug-email", email);
        performer.performAction(userActions.doRequestPasswordReset);
    }
}