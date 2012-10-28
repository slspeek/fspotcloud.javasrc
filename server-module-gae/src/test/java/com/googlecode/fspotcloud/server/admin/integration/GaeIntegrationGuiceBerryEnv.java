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

package com.googlecode.fspotcloud.server.admin.integration;

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.TestScoped;
import com.google.guiceberry.TestWrapper;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.googlecode.botdispatch.controller.inject.LocalControllerModule;
import com.googlecode.fspotcloud.server.inject.GaeTotalModule;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.user.inject.ServerAddress;
import com.googlecode.fspotcloud.user.openid.OpenIdUserModule;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;

public class GaeIntegrationGuiceBerryEnv extends GuiceBerryModule {
    @Override
    protected void configure() {
        super.configure();
        System.setProperty("photo.dir.original", "//home/steven/Photos");
        System.setProperty("photo.dir.override",
                "" + System.getProperty("user.dir") +
                        "/../peer/src/test/resources/Photos");

        Module firstOverride = Modules.override(new GaeTotalModule(3, "",
                "rms@example.com"))
                .with(new OpenIdUserModule(
                        "rms@example.com"), new LocalControllerModule());
        Module secondOverride = Modules.override(firstOverride)
                .with(new ModuleOverrides());
        install(secondOverride);
        bind(TestWrapper.class).to(GaeLocalDatastoreTestWrapper.class);
    }

    private static class ModuleOverrides extends AbstractModule {
        @Override
        public void configure() {
            bind(IMail.class).toInstance(mock(IMail.class));
            bind(HttpSession.class).to(FakeHttpServletSession.class)
                    .in(TestScoped.class);
            bind(String.class).annotatedWith(ServerAddress.class)
                    .toInstance("http://localhost");
        }
    }
}
