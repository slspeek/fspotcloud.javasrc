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

package com.googlecode.fspotcloud.client.main.help;

import com.google.gwt.resources.client.ImageResource;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.ui.Resources.Style;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.UserActionImpl;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;


public class HelpContentGeneratorTest extends TestCase {
    private HelpContentGenerator gen;
    private Mockery context;
    private Style style;
    private Resources resources;
    private ImageResource icon;

    @Override
    protected void setUp() throws Exception {
        context = new Mockery();
        style = context.mock(Resources.Style.class);
        resources = context.mock(Resources.class);
        icon = context.mock(ImageResource.class);

        super.setUp();
    }

    public void testOne() {
        context.checking(new Expectations() {

            {
                exactly(1).of(icon).getURL();
                will(returnValue("bar"));
                exactly(1).of(resources).style();
                will(returnValue(style));
                exactly(3).of(style).helpKey();
                will(returnValue("fsc-help-key"));
                exactly(1).of(style).helpDescription();
                will(returnValue("fsc-help-description"));
                exactly(1).of(style).helpSeparator();
                will(returnValue("fsc-help-separator"));
            }
        });
        gen = new HelpContentGenerator(resources);

        UserActionImpl s = new UserActionImpl("", "", "Start slideshow",
                new KeyStroke('s'), null, icon, null, null);
        String row = gen.getHelpText(s);
        String desired = "<span class='fsc-help-key'>s</span></td>" +
                "<td><span class='fsc-help-separator'>:</span></td><td><img src='bar' /></td>" +
                "<td><span class='fsc-help-description'>Start slideshow</span>";
        assertEquals(desired, row);
    }
}
