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

package com.googlecode.fspotcloud.server.control.task.actions.intern;

import com.googlecode.fspotcloud.shared.peer.PhotoUpdate;
import com.googlecode.fspotcloud.test.EqualsTest;
import org.apache.commons.lang.SerializationUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class PhotoUpdateActionTest extends EqualsTest<PhotoUpdateAction> {
    PhotoUpdateAction action;

    @Before
    public void setUp() throws Exception {
        PhotoUpdate update = new PhotoUpdate("1");
        List<PhotoUpdate> list = new ArrayList<PhotoUpdate>();
        list.add(update);
        action = new PhotoUpdateAction(list);
    }

    @Test
    public void testGetUpdates() {
        assertEquals(1, action.getWorkLoad().size());
    }

    @Test
    public void testSerialize() {
        SerializationUtils.serialize(action);
    }

    @Override
    protected PhotoUpdateAction getOne() {
        return new PhotoUpdateAction(newArrayList(new PhotoUpdate("1")));
    }

    @Override
    protected PhotoUpdateAction getTheOther() {
        return new PhotoUpdateAction(newArrayList(new PhotoUpdate("1")));
    }

    @Override
    protected PhotoUpdateAction getDifferentOne() {
        return new PhotoUpdateAction(newArrayList(new PhotoUpdate("2")));
    }
}
