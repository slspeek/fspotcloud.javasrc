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

import com.googlecode.fspotcloud.test.EqualsTest;
import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertNotNull;

public class RemovePhotosFromTagActionTest extends EqualsTest<RemovePhotosFromTagAction> {
    @Test
    public void testSerialization() {
        RemovePhotosFromTagAction action = new RemovePhotosFromTagAction("fooMock",
                newArrayList("2", "3"));
        byte[] ser = SerializationUtils.serialize(action);
        RemovePhotosFromTagAction deserialized = (RemovePhotosFromTagAction) SerializationUtils.deserialize(ser);
        assertNotNull(deserialized);
    }

    @Override
    protected RemovePhotosFromTagAction getOne() {
        return new RemovePhotosFromTagAction("1", newArrayList("2", "3"));
    }

    @Override
    protected RemovePhotosFromTagAction getTheOther() {
        return new RemovePhotosFromTagAction("1", newArrayList("2", "3"));
    }

    @Override
    protected RemovePhotosFromTagAction getDifferentOne() {
        return new RemovePhotosFromTagAction("2", newArrayList("2", "3"));
    }
}
