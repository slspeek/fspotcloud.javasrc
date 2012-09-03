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
package com.googlecode.fspotcloud.server.control.callback;

import com.googlecode.fspotcloud.server.control.task.actions.intern.RemoveTagsDeletedFromPeerAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.TagUpdateAction;
import com.googlecode.fspotcloud.shared.peer.PeerUpdateInstructionsResult;
import com.googlecode.fspotcloud.shared.peer.TagRemovedFromPeer;
import com.googlecode.fspotcloud.shared.peer.TagUpdate;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.shared.Action;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class PeerUpdateInstructionsCallbackTest {
    public static final String TAG_REMOVED_ID = "1";
    public static final String TAG_ID = "2";
    @Inject
    PeerUpdateInstructionsCallback callback;

    @Test
    public void testNormalExecute(TaskQueueDispatch dispatchAsync,
                                  ArgumentCaptor<Action> updateCaptor) throws Exception {
        List<TagRemovedFromPeer> removedTags = newArrayList(new TagRemovedFromPeer(
                TAG_REMOVED_ID));
        List<TagUpdate> tagUpdates = newArrayList(new TagUpdate(TAG_ID));
        PeerUpdateInstructionsResult result = new PeerUpdateInstructionsResult(tagUpdates,
                removedTags);

        callback.onSuccess(result);

        verify(dispatchAsync, times(2)).execute(updateCaptor.capture());

        List<Action> actions = updateCaptor.getAllValues();
        TagUpdateAction update = (TagUpdateAction) actions.get(1);
        RemoveTagsDeletedFromPeerAction remove = (RemoveTagsDeletedFromPeerAction) actions.get(0);
        assertEquals(TAG_REMOVED_ID, remove.getWorkLoad().get(0).getTagId());
        assertEquals(TAG_ID, update.getWorkLoad().get(0).getTagId());
    }
}
