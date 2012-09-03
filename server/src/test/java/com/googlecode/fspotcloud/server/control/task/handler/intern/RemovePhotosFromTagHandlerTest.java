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

package com.googlecode.fspotcloud.server.control.task.handler.intern;

import com.google.common.collect.ImmutableList;
import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseEntity;
import com.googlecode.fspotcloud.model.jpa.photo.PhotoEntity;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemovePhotosFromTagAction;
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RemovePhotosFromTagHandlerTest {
    private static final String ID_B = "B";
    private static final String ID_A = "A";
    private static final String TAG_ID = "1";
    int MAX_DELETE_TICKS = 1;
    @Mock
    TaskQueueDispatch dispatchAsync;
    @Mock
    PhotoDao photoDao;
    @Mock
    TagDao tagManager;
    @Mock
    PeerDatabaseDao peers;
    Tag tag;
    Tag tag3;
    @Captor
    ArgumentCaptor<RemovePhotosFromTagAction> nextCallCaptor;
    PhotoInfo photoInfoA;
    PhotoInfo photoInfoB;
    List<String> idList;
    Photo photoA;
    Photo photoB;
    List<String> tagIdListA = ImmutableList.of("1");
    List<String> tagIdListB = ImmutableList.of("1", "3");
    PeerDatabase peer = new PeerDatabaseEntity();
    RemovePhotosFromTagHandler handler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new RemovePhotosFromTagHandler(MAX_DELETE_TICKS,
                dispatchAsync, photoDao, tagManager, peers);
        tag3 = new TagEntity();
        tag3.setId("3");
        tag3.setImportIssued(true);
        tag = new TagEntity();
        tag.setId(TAG_ID);
        photoA = new PhotoEntity();
        photoA.setTagList(tagIdListA);
        photoB = new PhotoEntity();
        photoB.setTagList(tagIdListB);
        photoInfoA = new PhotoInfo(ID_A, "", new Date(10));
        photoInfoB = new PhotoInfo(ID_B, "", new Date(100000));
        idList = newArrayList(ID_A, ID_B);

        TreeSet<PhotoInfo> cached = new TreeSet<PhotoInfo>();
        cached.add(photoInfoA);
        cached.add(photoInfoB);
        tag.setCachedPhotoList(cached);
        when(tagManager.find(TAG_ID)).thenReturn(tag);
        when(tagManager.find("3")).thenReturn(tag3);
        when(photoDao.find(ID_A)).thenReturn(photoA);
        when(photoDao.find(ID_B)).thenReturn(photoB);
        when(peers.get()).thenReturn(peer);
    }

    /**
     * First gets deleted, the second is needed in another tag. The
     * max_delete = 1 so this takes 'recursion' over asyncDispatch.
     *
     * @throws DispatchException
     */
    @Test
    public void testExecute() throws DispatchException {
        assertEquals(2, tag.getCachedPhotoList().size());

        handler.execute(new RemovePhotosFromTagAction(TAG_ID, idList), null);
        verify(photoDao).delete(photoA);
        verify(photoDao).find(ID_A);
        assertEquals(1, tag.getCachedPhotoList().size());
        verifyNoMoreInteractions(photoDao);
        verify(dispatchAsync).execute(nextCallCaptor.capture());
        assertEquals(TAG_ID, nextCallCaptor.getValue().getTagId());
        handler.execute(new RemovePhotosFromTagAction(TAG_ID, idList), null);
        verifyNoMoreInteractions(dispatchAsync);
        verify(photoDao).find(ID_B);
        verifyNoMoreInteractions(photoDao);
        assertEquals(1, tag.getCachedPhotoList().size());
    }
}
