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

import com.google.gwt.i18n.client.DateTimeFormat;
import com.thoughtworks.selenium.Selenium;

import javax.inject.Inject;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author steven
 */
public class UserAccountPage {
    @Inject
    Selenium selenium;

    public void open() {
        selenium.open("/#UserAccountPlace:");
        selenium.waitForPageToLoad("30000");
    }

    public void verifyEmail(String email) {
        assertEquals(selenium.getText("gwt-debug-email"), email);
    }

    public Date getLastLogin() {
        return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_LONG)
                .parse(selenium.getText("gwt-debug-last-login"));
    }
}
