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

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.googlecode.fspotcloud.peer.CopyDatabase;
import com.googlecode.fspotcloud.peer.ImageData;
import com.googlecode.fspotcloud.peer.db.Backend;
import com.googlecode.fspotcloud.peer.db.FSpotBackend;
import com.googlecode.fspotcloud.peer.db.ShotwellBackend;
import com.googlecode.simpleblobstore.client.BlobstoreClient;

public class PeerModule extends AbstractModule {
	private final String db;
	private final String workDir;
	private final int stopPort;
	private final boolean shotwell;

	public PeerModule(String db, String workDir, int stopPort) {
		this(db, workDir, stopPort, false);
	}

	public PeerModule(String db, String workDir, int stopPort, boolean shotwell) {
		this.db = db;
		this.workDir = workDir;
		this.stopPort = stopPort;
		this.shotwell = shotwell;
	}

	@Override
	protected void configure() {
		if (shotwell) {
			bind(Backend.class).to(ShotwellBackend.class).in(Singleton.class);
		} else {
			bind(Backend.class).to(FSpotBackend.class).in(Singleton.class);
		}
		bind(ImageData.class);
		bind(String.class).annotatedWith(Names.named("JDBC URL"))
				.toProvider(CopyDatabase.class).in(Singleton.class);
		bind(String.class).annotatedWith(Names.named("DatabasePath"))
				.toInstance(db);
		bind(String.class).annotatedWith(Names.named("WorkDir")).toInstance(
				workDir);
		bind(Integer.class).annotatedWith(Names.named("stop port")).toInstance(
				Integer.valueOf(stopPort));
		bind(BlobstoreClient.class).toInstance(new BlobstoreClient(System.getProperty("endpoint") + System.getProperty("bot.secret") + "/"));
	}
}
