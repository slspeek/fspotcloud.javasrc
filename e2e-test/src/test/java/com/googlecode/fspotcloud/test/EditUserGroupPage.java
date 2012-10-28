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

import com.thoughtworks.selenium.Selenium;

import javax.inject.Inject;

import static com.googlecode.fspotcloud.test.Sleep.sleepShort;

/**
 * @author steven
 */
public class EditUserGroupPage {
    @Inject
    Selenium selenium;

    public void open(Long id) {
        selenium.open("/#EditUserGroupPlace:" + id);
        selenium.waitForPageToLoad("30000");
    }

    public void fill(String name, String description)
            throws InterruptedException {
        sleepShort();
        selenium.type("id=gwt-debug-name", name);
        selenium.type("id=gwt-debug-description", description);
    }

    public void save() {
        selenium.click("gwt-debug-save");
    }

    public void togglePublic() {
        selenium.click("id=gwt-debug-public-checkbox-input");
    }
}
