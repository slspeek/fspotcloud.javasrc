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
package com.googlecode.fspotcloud.server.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.googlecode.botdispatch.model.api.Commands;
import com.googlecode.botdispatch.model.jpa.gae.command.CommandManager;
import com.googlecode.fspotcloud.model.jpa.CachedModelModule;
import com.googlecode.fspotcloud.user.gae.UserModuleGae;
import com.googlecode.taskqueuedispatch.inject.TaskQueueDispatchModule;
import com.googlecode.taskqueuedispatch.inject.TaskQueueDispatchServletModule;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class GaeTotalModule extends AbstractModule {
    public static final Integer MAX_COMMAND_DELETE = new Integer(300);
    private String botSecret;
    private final int maxTicks;
    private final String fromAddress;
    private String smtpServer;

    public GaeTotalModule(int maxTicks, String botSecret, String fromAddress) {
        this.maxTicks = maxTicks;
        this.botSecret = botSecret;
        this.fromAddress = fromAddress;
    }

    @Override
    protected void configure() {
        install(new ServerTotalModule(maxTicks, botSecret, fromAddress, ""));
        install(new CachedModelModule(maxTicks, "gae"));
        install(new TaskQueueDispatchModule());
        install(new TaskQueueDispatchServletModule());
        install(new UserModuleGae());
        bind(Commands.class).to(CommandManager.class).in(Singleton.class);
        bind(Integer.class).annotatedWith(Names.named("maxCommandDelete"))
                .toInstance(MAX_COMMAND_DELETE);
    }
}
