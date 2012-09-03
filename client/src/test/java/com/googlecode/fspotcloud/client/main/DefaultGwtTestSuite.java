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

package com.googlecode.fspotcloud.client.main;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.googlecode.fspotcloud.client.main.event.GwtTestEventModule;
import com.googlecode.fspotcloud.client.main.ui.GwtTestImageViewImpl;
import junit.framework.Test;
import junit.framework.TestCase;


public class DefaultGwtTestSuite extends TestCase {
    public static Test suite() {
        GWTTestSuite suite = new GWTTestSuite("All Gwt Tests");
        suite.addTestSuite(GwtTestImageViewImpl.class);
        //suite.addTestSuite(GwtTestButtonPanelPresenterProvider.class);
        suite.addTestSuite(GwtTestEventModule.class);

        return suite;
    }
}
