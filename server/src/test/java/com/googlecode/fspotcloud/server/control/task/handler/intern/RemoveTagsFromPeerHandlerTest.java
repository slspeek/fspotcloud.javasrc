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
package com.googlecode.fspotcloud.server.control.task.handler.intern;

import com.google.common.collect.ImmutableList;
import com.google.inject.name.Names;
import com.googlecode.fspotcloud.model.jpa.photo.PhotoEntity;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemovePhotosFromTagAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemoveTagsDeletedFromPeerAction;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.peer.TagRemovedFromPeer;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class RemoveTagsFromPeerHandlerTest {
    private static final String ID_B = "B";
    private static final String ID_A = "A";
    @Inject
    private RemoveTagsFromPeerHandler instance;
    PhotoInfo photoInfoA;
    PhotoInfo photoInfoB;
    Photo photoA;
    Photo photoB;
    List<String> tagIdListA = ImmutableList.of("1");
    List<String> tagIdListB = ImmutableList.of("1", "3");
    Tag tag;
    Tag tag2;

    @Before
    public void setUp() throws Exception {
        tag2 = new TagEntity();
        tag2.setId("2");
        tag2.setImportIssued(true);
        tag = new TagEntity();
        tag.setId("1");
        photoA = new PhotoEntity();
        photoA.setTagList(tagIdListA);
        photoB = new PhotoEntity();
        photoB.setTagList(tagIdListB);
        photoInfoA = new PhotoInfo(ID_A, "", new Date(10));
        photoInfoB = new PhotoInfo(ID_B, "", new Date(100000));

        TreeSet<PhotoInfo> cached = new TreeSet<PhotoInfo>();
        cached.add(photoInfoA);
        cached.add(photoInfoB);
        tag2.setCachedPhotoList(cached);
    }

    @Test
    public void testExecute(TaskQueueDispatch dispatchAsync, TagDao tagManager)
            throws Exception {
        when(tagManager.find("1")).thenReturn(tag);
        when(tagManager.find("2")).thenReturn(tag2);
        System.out.println("execute");

        RemoveTagsDeletedFromPeerAction action = new RemoveTagsDeletedFromPeerAction(newArrayList(
                new TagRemovedFromPeer("2"),
                new TagRemovedFromPeer("1")));
        VoidResult expResult = new VoidResult();
        VoidResult result = instance.execute(action, null);
        assertEquals(expResult, result);
        verify(tagManager).find("2");
        verify(tagManager).deleteByKey("2");
        verify(dispatchAsync)
                .execute(new RemoveTagsDeletedFromPeerAction(newArrayList(
                        new TagRemovedFromPeer("1"))));
        verify(dispatchAsync)
                .execute(new RemovePhotosFromTagAction("2", newArrayList(ID_A, ID_B)));
        verifyNoMoreInteractions(tagManager, dispatchAsync);
    }

    @Test
    public void testExecuteNoRecursion(TaskQueueDispatch dispatchAsync,
                                       TagDao tagManager) throws Exception {
        when(tagManager.find("1")).thenReturn(tag);
        when(tagManager.find("2")).thenReturn(tag2);
        System.out.println("execute");

        RemoveTagsDeletedFromPeerAction action = new RemoveTagsDeletedFromPeerAction(newArrayList(
                new TagRemovedFromPeer("2")));
        VoidResult expResult = new VoidResult();
        VoidResult result = instance.execute(action, null);
        assertEquals(expResult, result);
        verify(tagManager).find("2");
        verify(tagManager).deleteByKey("2");
        verify(dispatchAsync)
                .execute(new RemovePhotosFromTagAction("2", newArrayList(ID_A, ID_B)));
        verifyNoMoreInteractions(tagManager, dispatchAsync);
    }

    public static class Module extends JukitoModule {
        protected void configureTest() {
            bind(Integer.class).annotatedWith(Names.named("maxDelete"))
                    .toInstance(new Integer(1));
        }
    }
}
