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
import com.google.inject.name.Names;
import com.googlecode.botdispatch.controller.inject.ControllerServletModule;
import com.googlecode.fspotcloud.server.control.task.inject.TaskActionsModule;
import com.googlecode.fspotcloud.server.control.task.inject.TaskModule;
import com.googlecode.fspotcloud.server.image.ImageHelper;
import com.googlecode.fspotcloud.server.image.ImageHelperImpl;
import com.googlecode.fspotcloud.server.mail.FromAddress;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.mail.Mailer;
import com.googlecode.fspotcloud.server.mail.SMTPServer;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class ServerTotalModule extends AbstractModule {
    private final int maxTicks;
    private String botSecret;
    private String fromAddress;
    private String smtpServer;

    public ServerTotalModule(int maxTicks, String botSecret,
                             String fromAddress, String smtpServer) {
        this.maxTicks = maxTicks;
        this.botSecret = botSecret;
        this.fromAddress = fromAddress;
        this.smtpServer = smtpServer;
    }

    @Override
    protected void configure() {
        install(new AdminActionsModule());
        bind(Integer.class).annotatedWith(Names.named("maxTicks"))
                .toInstance(new Integer(maxTicks));
        bind(String.class).annotatedWith(FromAddress.class)
                .toInstance(fromAddress);
        bind(String.class).annotatedWith(SMTPServer.class).toInstance(smtpServer);

        bind(IMail.class).to(Mailer.class);
        bind(ImageHelper.class).to(ImageHelperImpl.class);
        install(new ServerServletModule());
        install(new ControllerServletModule(botSecret));
        install(new ServerControllerModule());
        install(new TaskActionsModule());
        install(new TaskModule());
        install(new MainActionModule());
    }
}
