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

import com.google.inject.Inject;

import java.io.IOException;


/**
 * @author steven
 */
public class PeerRunner {
    private final String endpoint;
    private final String secret;
    private final String peerJar;
    private final String stopPort;

    @Inject
    public PeerRunner() {
        this.endpoint = System.getProperty("endpoint");
        this.secret = System.getProperty("bot.secret");
        this.peerJar = System.getProperty("peer.jar");
        stopPort = System.getProperty("stop.port");
    }

    public void startPeer(String db) throws IOException {
        String[] command = getCommand(db);
        Process peer = Runtime.getRuntime().exec(command);
    }

    public void stopPeer() throws IOException {
        Process peer = Runtime.getRuntime()
                .exec(new String[]{"telnet", "localhost", stopPort});
    }

    private String[] getCommand(String db) {
        String[] cmd = new String[]{
                "screen", "-d", "-m", "java", "-cp", peerJar, "-Ddb=" + db,
                "-Dendpoint=" + endpoint, "-Dbot.secret=" + secret, "-Dpause=2",
                "-Dphoto.dir.original=file:///home/steven/Photos",
                "-Dstop.port=" + stopPort,
                "-Dphoto.dir.override=file://" +
                        System.getProperty("user.dir") +
                        "/../peer/src/test/resources/Photos",
                "com.googlecode.fspotcloud.peer.Main"
        };

        return cmd;
    }
}
