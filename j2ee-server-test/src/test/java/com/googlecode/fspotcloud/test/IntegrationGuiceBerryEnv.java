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

import com.google.guiceberry.*;
import com.google.guiceberry.controllable.IcMaster;
import com.google.guiceberry.controllable.StaticMapInjectionController;
import com.google.guiceberry.controllable.TestIdServerModule;
import com.google.inject.*;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.user.emailconfirmation.SecretGenerator;
import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;


public class IntegrationGuiceBerryEnv extends AbstractModule {
    public static final int PORT = 8000;

    private Injector injector;



    @Provides
    @Singleton
    @PortNumber
    int getPortNumber() {
        return 8000;
    }

    @Provides
    @TestScoped
    Selenium getWebDriver(@PortNumber int portNumber, TestId testId) {
        WebDriver driver = new FirefoxDriver();
        final String baseUrl = "http://localhost:" + portNumber;
        driver.get(baseUrl);
        driver.manage().addCookie(new Cookie(TestId.COOKIE_NAME, testId.toString()));
        return new WebDriverBackedSelenium(driver, baseUrl);
    }


    private IcMaster icMaster;

    @Provides
    @Singleton
    FscServer buildServer(@PortNumber int portNumber) {
        FscServer result = null;
        try {
            result = new FscServer(portNumber) {
                @Override
                protected Module getFscModule() {
                    // !!! HERE !!!
                    Module module = icMaster.buildServerModule(
                            new TestIdServerModule(),
                            super.getFscModule());

                    return module;
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void configure() {
        install(new GuiceBerryModule());
        bind(GuiceBerryEnvMain.class).to(ServerStarter.class);
        bind(TestWrapper.class).to(SeleniumTestWrapper.class);
        bind(ILogin.class).to(RegularLoginBot.class);
        bind(UserDao.class).toProvider(UserDaoProvider.class);
        // !!!! HERE !!!!
        icMaster = new IcMaster()
                .thatControls(StaticMapInjectionController.strategy(),
                        Key.get(SecretGenerator.class))
                .thatControls(StaticMapInjectionController.strategy(),
                        Key.get(IMail.class));
        install(icMaster.buildClientModule());
    }


    @Provides
    Resources getResource() {
        return mock(Resources.class);
    }
    private static final class ServerStarter implements GuiceBerryEnvMain {
        @Inject
        private FscServer server;

        public void run() {
            server.start();
        }
    }
}
