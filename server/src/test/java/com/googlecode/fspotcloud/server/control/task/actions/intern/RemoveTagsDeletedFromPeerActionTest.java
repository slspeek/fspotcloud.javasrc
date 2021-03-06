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
package com.googlecode.fspotcloud.server.control.task.actions.intern;

import com.googlecode.fspotcloud.shared.peer.TagRemovedFromPeer;
import com.googlecode.fspotcloud.test.EqualsTest;
import org.jukito.JukitoRunner;
import org.junit.runner.RunWith;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class RemoveTagsDeletedFromPeerActionTest extends EqualsTest<RemoveTagsDeletedFromPeerAction> {
    @Override
    protected RemoveTagsDeletedFromPeerAction getOne() {
        return new RemoveTagsDeletedFromPeerAction(newArrayList(
                new TagRemovedFromPeer("1")));
    }

    @Override
    protected RemoveTagsDeletedFromPeerAction getTheOther() {
        return new RemoveTagsDeletedFromPeerAction(newArrayList(
                new TagRemovedFromPeer("1")));
    }

    @Override
    protected RemoveTagsDeletedFromPeerAction getDifferentOne() {
        return new RemoveTagsDeletedFromPeerAction(newArrayList(
                new TagRemovedFromPeer("2")));
    }
}
