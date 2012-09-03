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

package com.googlecode.fspotcloud.peer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.botdispatch.bot.Bot;
import com.googlecode.botdispatch.bot.BotModule;
import com.googlecode.fspotcloud.peer.inject.PeerActionsModule;
import com.googlecode.fspotcloud.peer.inject.PeerModule;


public class Main {
    public static void main(String[] args) throws Exception {
        final String workDir = System.getProperty("user.dir");
        final String db = System.getProperty("db");
        final int stopPort = Integer.valueOf(System.getProperty("stop.port",
                "-1"));

        Injector injector = Guice.createInjector(new PeerModule(db, workDir,
                stopPort), new PeerActionsModule(), new BotModule());

        if (stopPort != -1) {
            StopListener stopListener = injector.getInstance(StopListener.class);
            stopListener.start();
        }

        Bot bot = injector.getInstance(Bot.class);
        bot.runForever();
    }
}
