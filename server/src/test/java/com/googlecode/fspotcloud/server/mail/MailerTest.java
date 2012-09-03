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

package com.googlecode.fspotcloud.server.mail;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.logging.Logger;


@RunWith(JukitoRunner.class)
public class MailerTest {
    @Inject
    private Mailer mailer;

    @Test
    public void testSend() throws Exception {
        mailer.send("slspeek@gmail.com", "Hi", "Hi Steven");
        Logger.getAnonymousLogger().info("Mail to steven@localhost send");
    }

    public static class Module extends JukitoModule {
        protected void configureTest() {
            bind(String.class).annotatedWith(FromAddress.class)
                    .toInstance("slspeek@gmail.com");
            bind(String.class).annotatedWith(SMTPServer.class)
                    .toInstance("smtp.xs4all.nl");
        }
    }
}
