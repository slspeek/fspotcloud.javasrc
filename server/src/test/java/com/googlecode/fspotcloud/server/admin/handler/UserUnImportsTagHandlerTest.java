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
package com.googlecode.fspotcloud.server.admin.handler;

import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseEntity;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemovePhotosFromTagAction;
import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.dashboard.UserUnImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.user.IAdminPermission;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class UserUnImportsTagHandlerTest {
    @Inject
    private UserUnImportsTagHandler handler;
    private final String TAG_ID = "1";
    private final UserUnImportsTagAction action = new UserUnImportsTagAction(TAG_ID);

    @Test
    public void testNormalExecute(TagDao tagManager,
                                  TaskQueueDispatch dispatchAsync, PeerDatabaseDao peerDatabaseDao,
                                  ArgumentCaptor<RemovePhotosFromTagAction> actionCaptor)
            throws Exception {
        Tag tagOne = new TagEntity();
        tagOne.setId(TAG_ID);
        when(tagManager.find(TAG_ID)).thenReturn(tagOne);

        VoidResult result = handler.execute(action, null);
        verify(dispatchAsync).execute(actionCaptor.capture());

        RemovePhotosFromTagAction deleteAction = actionCaptor.getValue();
        assertEquals(TAG_ID, deleteAction.getTagId());
        verify(tagManager).find(TAG_ID);
        verifyNoMoreInteractions(tagManager, peerDatabaseDao, dispatchAsync);
    }

    @Test
    public void testNormalExecuteUnImportNeeded(TagDao tagManager,
                                                TaskQueueDispatch dispatchAsync, PeerDatabaseDao peerDatabaseDao,
                                                ArgumentCaptor<RemovePhotosFromTagAction> actionCaptor)
            throws Exception {
        Tag tagOne = new TagEntity();
        tagOne.setId(TAG_ID);
        tagOne.setImportIssued(true);
        when(tagManager.find(TAG_ID)).thenReturn(tagOne);

        VoidResult result = handler.execute(action, null);

        verify(dispatchAsync).execute(actionCaptor.capture());

        RemovePhotosFromTagAction deleteAction = actionCaptor.getValue();
        assertEquals(TAG_ID, deleteAction.getTagId());
        verify(tagManager).find(TAG_ID);
        verify(tagManager).save(tagOne);
        verifyNoMoreInteractions(tagManager, peerDatabaseDao, dispatchAsync);
    }

    @Test
    public void testNormalExecuteTreeCacheNeedsClearing(TagDao tagManager,
                                                        TaskQueueDispatch dispatchAsync, PeerDatabaseDao peerDatabaseDao,
                                                        ArgumentCaptor<RemovePhotosFromTagAction> actionCaptor)
            throws Exception {
        PeerDatabase peer = new PeerDatabaseEntity();
        List<TagNode> emptyList = newArrayList();
        peer.setCachedTagTree(emptyList);

        Tag tagOne = new TagEntity();
        tagOne.setId(TAG_ID);
        tagOne.setImportIssued(true);
        when(tagManager.find(TAG_ID)).thenReturn(tagOne);

        VoidResult result = handler.execute(action, null);

        verify(dispatchAsync).execute(actionCaptor.capture());

        RemovePhotosFromTagAction deleteAction = actionCaptor.getValue();
        assertEquals(TAG_ID, deleteAction.getTagId());
        verify(tagManager).find(TAG_ID);
        verify(tagManager).save(tagOne);
        verifyNoMoreInteractions(tagManager, peerDatabaseDao, dispatchAsync);
    }

    @Test(expected = SecurityException.class)
    public void testUnAuthorizedExecute(IAdminPermission adminPermission)
            throws Exception {
        doThrow(new SecurityException()).when(adminPermission)
                .checkAdminPermission();

        VoidResult result = handler.execute(action, null);
    }
}
