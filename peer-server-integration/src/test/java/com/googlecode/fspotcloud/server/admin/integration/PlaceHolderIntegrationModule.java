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
import com.google.inject.AbstractModule;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;
import com.google.inject.util.Modules;
import com.googlecode.fspotcloud.user.openid.OpenIdUserModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class PlaceHolderIntegrationModule extends GuiceBerryModule {
    public static final String RMS_FSF_ORG = "rms@example.com";

    @Override
    public void configure() {
        super.configure();
        install(Modules.override(new CommonIntegrationModule(),
                new OpenIdUserModule(RMS_FSF_ORG)).with(new AbstractModule() {
            @Override
            protected void configure() {
                bind(HttpSession.class).to(FakeHttpServletSession.class)
                        .in(TestScoped.class);
                bind(HttpServletRequest.class).to(FakeHttpRequest.class);
                bindScope(RequestScoped.class, testScope);
                bindScope(SessionScoped.class, testScope);
            }
        }));
    }
}
