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

package com.googlecode.fspotcloud.server.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.admin.integration.FakeHttpRequest;
import com.googlecode.fspotcloud.server.admin.integration.FakeHttpServletSession;
import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class J2eeInjectorTest {
    @Test
    public void testInjector() {
        Injector injector = Guice.createInjector(Modules.override(
                new J2eeTotalModule(10, "FOO_BAR", "foo@bar", "")).with(new AbstractModule() {
            @Override
            protected void configure() {
                bind(HttpSession.class).to(FakeHttpServletSession.class);

                bind(HttpServletRequest.class).to(FakeHttpRequest.class);
            }
        }));

        AssertJUnit.assertNotNull(injector);

        PeerDatabaseDao defaultPeer = injector.getInstance(PeerDatabaseDao.class);
        PeerDatabase pd = defaultPeer.get();
        ControllerDispatchAsync controller = injector.getInstance(ControllerDispatchAsync.class);
        AssertJUnit.assertNotNull(controller);
    }
}
