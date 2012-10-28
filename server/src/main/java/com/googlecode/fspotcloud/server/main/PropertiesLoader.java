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

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.googlecode.fspotcloud.server.main;

import com.googlecode.fspotcloud.server.inject.PropertiesFile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class PropertiesLoader {

    private final String propertiesFileName;

    @Inject
    public PropertiesLoader(@PropertiesFile String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }

    public Properties loadProperties() {
        try {
            Properties p = new Properties();
            ClassLoader l = getClass().getClassLoader();

            //ClassLoader l = Thread.currentThread().getContextClassLoader();
            //ClassLoader l = ClassLoader.getSystemClassLoader();
            final InputStream resourceAsStream = l.getResourceAsStream(
                    propertiesFileName);

            if (resourceAsStream == null) {
                throw new IOException(propertiesFileName + " not found");
            }

            p.load(resourceAsStream);
            Logger.getLogger(PropertiesLoader.class.getName())
                    .info("Properties successfully loaded.");
            return p;
        } catch (IOException ex) {
            Logger.getLogger(PropertiesLoader.class.getName())
                    .log(Level.SEVERE, null, ex);

            return null;
        }
    }
}
