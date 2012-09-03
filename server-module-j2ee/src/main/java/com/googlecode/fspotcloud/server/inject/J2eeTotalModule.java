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
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.googlecode.botdispatch.model.api.Commands;
import com.googlecode.botdispatch.model.command.CommandManager;
import com.googlecode.fspotcloud.model.jpa.ModelModule;
import com.googlecode.fspotcloud.user.openid.OpenIdUserModule;
import com.googlecode.taskqueuedispatch.inject.TaskQueueDispatchDirectModule;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class J2eeTotalModule extends AbstractModule {
    public static final Integer MAX_COMMAND_DELETE = new Integer(300);
    private final int maxTicks;
    private final String botSecret;
    private final String adminEmail;
    private String smtpServer;

    public J2eeTotalModule(int maxTicks, String botSecret, String adminEmail,
                           String smtpServer) {
        this.maxTicks = maxTicks;
        this.botSecret = botSecret;
        this.adminEmail = adminEmail;
        this.smtpServer = smtpServer;
    }

    @Override
    protected void configure() {
        install(new ServerTotalModule(maxTicks, botSecret, adminEmail,
                smtpServer));
        install(new ModelModule(maxTicks, "derby"));
        install(new TaskQueueDispatchDirectModule());
        install(new OpenIdUserModule(adminEmail));
        bind(Commands.class).to(CommandManager.class).in(Singleton.class);
        bind(Integer.class).annotatedWith(Names.named("maxCommandDelete"))
                .toInstance(MAX_COMMAND_DELETE);
    }
}
