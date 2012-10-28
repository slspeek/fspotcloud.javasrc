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

package com.googlecode.fspotcloud.peer.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.botdispatch.bot.Bot;
import com.googlecode.botdispatch.bot.BotModule;
import junit.framework.TestCase;


public class PeerModuleTest extends TestCase {
    public void testInjector() {
        final String workDir = System.getProperty("user.dir");
        final String db = System.getProperty("db",
                System.getProperty("user.dir") +
                        "/src/test/resources/photos.db");
        final int stopPort = Integer.valueOf(System.getProperty("stop.port",
                "4444"));

        Injector injector = Guice.createInjector(new PeerModule(db, workDir,
                stopPort), new PeerActionsModule(), new BotModule());
        assertNotNull(injector);

        Bot bot = injector.getInstance(Bot.class);
    }
}
