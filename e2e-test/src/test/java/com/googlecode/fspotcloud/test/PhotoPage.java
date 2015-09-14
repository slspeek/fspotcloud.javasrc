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

import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Assert;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * @author steven
 */
public class PhotoPage {
    @Inject
    WebDriverBackedSelenium selenium;

    public void open(String token) {
        selenium.open("" + token);
        selenium.waitForPageToLoad("30000");
    }

    public void open() {
        open("");
    }

    public void about() {
        selenium.click("gwt-debug-about");
    }

    public void mailFullsize() {
        selenium.click("gwt-debug-mail-fullsize");
    }

    public void showHelp() {
        selenium.click("gwt-debug-help");
    }

    public void hideHelp() {
        selenium.click("gwt-debug-help");
    }

    public void back() {
        selenium.click("gwt-debug-back");
        selenium.waitForPageToLoad("30000");
    }

    public void logout() {
        selenium.click("gwt-debug-logout");
        selenium.waitForPageToLoad("30000");
    }

    public void clickImage(int x, int y) {
        selenium.click("gwt-debug-image-view-" + x + "x" + y);
        selenium.waitForPageToLoad("30000");
    }

    public void assertImageHasId(int x, int y, String id) {
        Assert.assertEquals("image?id=" + id + "&thumb=true",
                selenium.getAttribute("//*[@id=\"gwt-debug-image-view-" + x + "x" +
                        y + "\"]@src"));
    }
    
    public void assertImageIsLoaded(int x, int y) {
//    	WebDriver driver = selenium.
//    	WebElement image = 
    }

    public void assertPagingLabelSays(int pos, int total) {
        String pagingLabelText = selenium.getText("gwt-debug-paging-label");

        final String label = pos + " of " + total;
        if (!pagingLabelText.contains(label)) {
            fail("Expected: " + label + " in: " + pagingLabelText);
        }
    }
}
