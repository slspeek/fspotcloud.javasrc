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

package com.googlecode.fspotcloud.server.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.googlecode.fspotcloud.server.main.PropertiesLoader;
import com.googlecode.fspotcloud.test.ThreeUsersFixture;

import java.util.Properties;
import java.util.logging.Logger;

public class GaeFixtureServletConfig extends GuiceServletContextListener {
	final Properties p = new PropertiesLoader("properties.properties")
			.loadProperties();

	Injector injector;

	public GaeFixtureServletConfig() {
		injector = Guice.createInjector(new GaeTotalModule(100, "", ""),
				new FixtureServletModulde());
		ThreeUsersFixture fixture = new ThreeUsersFixture(injector);
		fixture.run();
		Logger.getAnonymousLogger().info("We are called!");
	}

	@Override
	protected Injector getInjector() {
		System.setProperty("java.util.logging.config.file",
				"logging.properties");

		return injector;
	}

	class FixtureServletModulde extends ServletModule {
		@Override
		protected void configureServlets() {
			serve("/fixture").with(FixtureRunServlet.class);
		}
	}
}
