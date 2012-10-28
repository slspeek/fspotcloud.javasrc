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

import com.googlecode.fspotcloud.server.control.task.actions.intern.PhotoUpdateAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemovePhotosFromTagAction;
import com.googlecode.fspotcloud.shared.peer.GetPeerUpdateInstructionsAction;
import com.googlecode.fspotcloud.shared.peer.PhotoRemovedFromTag;
import com.googlecode.fspotcloud.shared.peer.PhotoUpdate;
import com.googlecode.fspotcloud.shared.peer.TagUpdateInstructionsResult;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.shared.Action;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.logging.Logger;

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
public class TagUpdateInstructionsCallbackTest {
    public static final String TAG_ID = "1";
    public static final String PHOTO_UPDATE_ID = "55";
    public static final String PHOTO_DELETE_ID = "5";
    TagUpdateInstructionsCallback callback;

    @Before
    public void createCallback(TaskQueueDispatch dispatchAsync) {
        callback = new TagUpdateInstructionsCallback(TAG_ID, dispatchAsync);
        callback.log = Logger.getAnonymousLogger();
    }

    @Test
    public void testNormalExecute(TaskQueueDispatch dispatchAsync,
                                  ArgumentCaptor<GetPeerUpdateInstructionsAction> actionCaptor,
                                  ArgumentCaptor<PeerUpdateInstructionsCallback> callbackCaptor,
                                  ArgumentCaptor<Action> updateCaptor) throws Exception {
        List<PhotoUpdate> toBoUpdated = newArrayList(new PhotoUpdate(
                PHOTO_UPDATE_ID));
        List<PhotoRemovedFromTag> toBoRemovedFromTag = newArrayList(new PhotoRemovedFromTag(
                PHOTO_DELETE_ID));
        TagUpdateInstructionsResult result = new TagUpdateInstructionsResult(toBoUpdated,
                toBoRemovedFromTag);
        System.out.println("Caal" + callback);
        callback.onSuccess(result);
        verify(dispatchAsync, times(2)).execute(updateCaptor.capture());

        List<Action> actions = updateCaptor.getAllValues();
        PhotoUpdateAction update = (PhotoUpdateAction) actions.get(1);
        RemovePhotosFromTagAction photoRemove = (RemovePhotosFromTagAction) actions.get(0);
        assertEquals(PHOTO_DELETE_ID, photoRemove.getWorkLoad().get(0));
        //assertEquals(PHOTO_DELETE_ID, photoRemove.getToBeDeleted().get(0));
        assertEquals(PHOTO_UPDATE_ID, update.getWorkLoad().get(0).getPhotoId());
    }
}
