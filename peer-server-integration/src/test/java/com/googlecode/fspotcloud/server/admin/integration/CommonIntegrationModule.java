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

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.util.Modules;
import com.googlecode.botdispatch.controller.inject.LocalControllerModule;
import com.googlecode.fspotcloud.peer.inject.PeerActionsModule;
import com.googlecode.fspotcloud.peer.inject.PeerModule;
import com.googlecode.fspotcloud.server.control.task.inject.TaskActionsModule;
import com.googlecode.fspotcloud.server.control.task.inject.TaskModule;
import com.googlecode.fspotcloud.server.image.ImageHelper;
import com.googlecode.fspotcloud.server.image.ImageHelperImpl;
import com.googlecode.fspotcloud.server.inject.AdminActionsModule;
import com.googlecode.fspotcloud.server.inject.MainActionModule;
import com.googlecode.fspotcloud.server.mail.FromAddress;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.taskqueuedispatch.inject.TaskQueueDispatchDirectModule;

import static org.mockito.Mockito.mock;

public class CommonIntegrationModule extends AbstractModule {
    public static final String SLSPEEK_GMAIL_COM = "slspeek@gmail.com";

    @Override
    public void configure() {
        System.setProperty("photo.dir.original", "//home/steven/Photos");
        System.setProperty("photo.dir.override",
                "" + System.getProperty("user.dir") +
                        "/../peer/src/test/resources/Photos");
        install(new AdminActionsModule());
        bind(Integer.class).annotatedWith(Names.named("maxTicks"))
                .toInstance(new Integer(3));
        bind(String.class).annotatedWith(FromAddress.class)
                .toInstance(SLSPEEK_GMAIL_COM);
        bind(IMail.class).toInstance(mock(IMail.class));
        bind(ImageHelper.class).to(ImageHelperImpl.class);
        install(new TaskActionsModule());
        install(Modules.override(new TaskModule()).with(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ImageSpecs.class)
                        .annotatedWith(Names.named("defaultImageSpecs"))
                        .toInstance(new ImageSpecs(2, 1, 2, 1));
            }
        }));
        install(new MainActionModule());
        install(new TaskQueueDispatchDirectModule());
        install(new LocalControllerModule());
        bind(Integer.class).annotatedWith(Names.named("maxCommandDelete"))
                .toInstance(3);

        final String db = System.getProperty("db",
                System.getProperty("user.dir") +
                        "/../peer/src/test/resources/photos.db");
        install(new PeerModule(db, System.getProperty("user.dir"), 4444));
        install(new PeerActionsModule());
    }
}
