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

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;


public class StopListener extends Thread {
    private final Logger log = Logger.getLogger(StopListener.class.getName());
    final int stopPort;
    private ServerSocket server;

    @Inject
    public StopListener(@Named("stop port")
                        int port) {
        this.stopPort = port;
    }

    public void run() {
        try {
            server = new ServerSocket(stopPort);
            server.accept();
        } catch (IOException e) {
            log.warning("Could not listen on port " + stopPort);
            log.warning("Aborting on request");
            System.exit(-1);
        }

        System.exit(0);
    }
}
