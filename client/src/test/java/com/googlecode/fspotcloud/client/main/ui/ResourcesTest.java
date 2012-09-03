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

package com.googlecode.fspotcloud.client.main.ui;

import com.googlecode.fspotcloud.client.view.action.KeyDispatcher;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;

import java.util.logging.Logger;


public class ResourcesTest extends TestCase {
    private final Logger log = Logger.getLogger(KeyDispatcher.class.getName());

    public void testOne() {
        Mockery context = new Mockery();
        final Resources res = context.mock(Resources.class);
        context.checking(new Expectations() {

            {
                oneOf(res).playIcon();
            }
        });
        log.info("" + res.playIcon());
    }
}
