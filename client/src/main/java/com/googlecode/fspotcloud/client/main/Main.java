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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.googlecode.fspotcloud.client.main.gin.AppGinjector;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Main implements EntryPoint {
    private final Logger log = Logger.getLogger(Main.class.getName());
    private final AppGinjector injector = GWT.create(AppGinjector.class);

    @Override
    public void onModuleLoad() {
        log.info("F-SpotCloud loading");

        try {
            MVPSetup setup = injector.getMVPSetup();
            log.info("gin fininshed the constuction of the application graph");
            setup.setup();
        } catch (Throwable e) {
            log.log(Level.SEVERE, "Uncaught exception in MVP setup", e);
        }
    }
}
