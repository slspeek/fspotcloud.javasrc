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
package com.googlecode.fspotcloud.shared.dashboard;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class UserImportsTagActionTest {
    public static final String TAG_ID = "1";

    @Test
    public void testGetTagId() {
        UserImportsTagAction instance = new UserImportsTagAction(TAG_ID);
        String expResult = TAG_ID;
        String result = instance.getTagId();
        assertEquals(expResult, result);
    }

    @Test
    public void testSeriazability() {
        UserImportsTagAction instance = new UserImportsTagAction(TAG_ID);
        SerializationUtils.serialize(instance);
    }
}
