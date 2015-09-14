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

/**
 *
 */
package com.googlecode.fspotcloud.peer.db;

import java.net.URL;
import java.util.logging.Logger;

public class FSpotBackendTest extends BackendTests {
	private final Logger log = Logger.getLogger(FSpotBackendTest.class
			.getName());

	public FSpotBackendTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		URL testDatabase = ClassLoader.getSystemResource("photos.db");
		String path = testDatabase.getPath();
		data = new FSpotBackend("jdbc:sqlite:" + path, null);
	}

}
