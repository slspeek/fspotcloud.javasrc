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
import com.googlecode.fspotcloud.server.main.PropertiesLoader;

import java.util.Properties;


public class J2eeGuiceServletConfig extends GuiceServletContextListener {
    Properties p = (new PropertiesLoader("properties.properties")).loadProperties();

    @Override
    protected Injector getInjector() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        int maxTicks = Integer.valueOf(p.getProperty(
                "fspotcloud.max.data.ticks",
                "100"));
        String adminEmail = p.getProperty("fspotcloud.admin.email");
        String botSecret = p.getProperty("fspotcloud.bot.secret");
        String smtpServer = p.getProperty("fspotcloud.smtp.server");
        Injector i = Guice.createInjector(new J2eeTotalModule(maxTicks,
                botSecret, adminEmail, smtpServer));

        return i;
    }
}
