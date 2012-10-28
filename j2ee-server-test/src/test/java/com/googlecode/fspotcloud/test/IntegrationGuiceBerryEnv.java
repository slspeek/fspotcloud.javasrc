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

package com.googlecode.fspotcloud.test;

import com.google.guiceberry.GuiceBerryEnvMain;
import com.google.inject.name.Names;

import java.io.IOException;
import java.net.URISyntaxException;


public class IntegrationGuiceBerryEnv extends SeleniumGuiceBerryEnv {
    public static final int PORT = 8000;

    @Override
    protected void configure() {
        super.configure();
        bind(GuiceBerryEnvMain.class).to(ServerStarter.class);
        bind(String.class).annotatedWith(Names.named("baseUrl"))
                .toInstance("http://localhost:8000");
        bind(ILogin.class).to(RegularLoginBot.class);
    }

    private static final class ServerStarter implements GuiceBerryEnvMain {
        private FscServer server;

        public void run() {
            // Starting a server should never be done in a @Provides method
            try {
                server = new FscServer(PORT); // (or inside Provider's get).
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            server.start();
        }
    }
}
