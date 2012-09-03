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
package com.googlecode.fspotcloud.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author steven
 */
public abstract class EqualsTest<T> {
    T one;
    T theOther;
    T differs;
    Object obj = new Object();

    @Before
    public final void setup() {
        one = getOne();
        theOther = getTheOther();
        differs = getDifferentOne();
    }

    protected abstract T getOne();

    protected abstract T getTheOther();

    protected abstract T getDifferentOne();

    @Test
    public void testEquals() {
        assertEquals(one, theOther);
    }

    @Test
    public void shouldNotBeEqual() {
        assertFalse(one.equals(obj));
    }

    @Test
    public void shouldNotEqual() {
        assertFalse(one.equals(differs));
    }

    @Test
    public void shouldNotEqualNull() {
        assertFalse(one.equals(null));
    }

    @Test
    public void hashCodeContract() {
        assertEquals(one.hashCode(), theOther.hashCode());
    }
}
