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
import junit.framework.Assert;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Properties;

import static org.junit.Assert.assertEquals;


/**
 * PropertiesLoader success unit test
 *
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class PropertiesLoaderSuccessTest {
    public static class Module extends JukitoModule {
        protected void configureTest() {

            bind(String.class).annotatedWith(PropertiesFile.class).toInstance("properties.properties");

        }
    }

    @Inject
    private PropertiesLoader loader;

    /**
     * Verifies that pl preserves system properties
     */
    @Test
    public void testLoadProperties() {
        Properties properties = loader.loadProperties();
        Assert.assertNotNull(System.getProperty("user.home"));
        assertEquals("testable", properties.get("test.property"));
    }

}
