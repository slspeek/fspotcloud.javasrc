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

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.TestScoped;
import com.google.guiceberry.TestWrapper;
import com.google.inject.Provides;
import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.inject.Named;


public class SeleniumGuiceBerryEnv extends GuiceBerryModule {
    @Provides
    @TestScoped
    Selenium getSelenium(@Named("baseUrl")
                         String baseUrl) {
        WebDriver driver;
        String userChoice = "fire"; //System.getProperty("fspotcloud.test.webdriver");

        if (userChoice != null) {
            driver = new FirefoxDriver();
        } else {
            driver = new HtmlUnitDriver();
            ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
        }

        return new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Override
    protected void configure() {
        super.configure();
        bind(TestWrapper.class).to(SeleniumTestWrapper.class);
    }
}
