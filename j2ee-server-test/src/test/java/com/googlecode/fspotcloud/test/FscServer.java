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

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;
import com.googlecode.fspotcloud.server.inject.J2eeTotalModule;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.webapp.WebAppContext;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class FscServer {
	private final Server server;
	private final Logger log = Logger.getLogger(FscServer.class.getName());

	public FscServer(int port) throws IOException, URISyntaxException {
		server = new Server(port);

		final URL url = new File("build/exploded").getAbsoluteFile().toURI()
				.toURL();
		WebAppContext webApp = new WebAppContext();
		webApp.setContextPath("/");
		webApp.setWar(url.toExternalForm());
		server.addHandler(webApp);
		Context root = new Context(server, "/", Context.SESSIONS);

		root.addFilter(GuiceFilter.class, "/*", 0);
		root.addServlet(DefaultServlet.class, "/");
	}

	protected Module getFscModule() {
		return new J2eeTotalModule(10, System.getProperty("bot.secret"),
				"rms@example.com", "smtp.xs4all.nl");
	}

	public void start() {
		try {
			TestServerGuiceServletConfig.MODULE = getFscModule();
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Injector getInjector() {
		return TestServerGuiceServletConfig.INJECTOR;
	}
}
