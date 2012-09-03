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

import com.googlecode.botdispatch.SerializableAsyncCallback;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.control.task.actions.intern.PhotoUpdateAction;
import com.googlecode.fspotcloud.shared.peer.GetPhotoDataAction;
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoDataResult;
import com.googlecode.fspotcloud.shared.peer.PhotoUpdate;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PhotoUpdateHandlerTest {
    private static final int MAX_PHOTO_TICKS = 3;
    PhotoUpdateHandler handler;
    PhotoUpdateAction action;
    @Mock
    ControllerDispatchAsync controllerAsync;
    @Mock
    TaskQueueDispatch recursive;
    @Captor
    ArgumentCaptor<GetPhotoDataAction> captorAction;
    @Captor
    ArgumentCaptor<SerializableAsyncCallback<PhotoDataResult>> captorCallback;
    @Captor
    ArgumentCaptor<PhotoUpdateAction> recursiveActionCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new PhotoUpdateHandler(1, 1, new ImageSpecs(1, 1, 1, 1),
                controllerAsync, recursive);

        PhotoUpdate update = new PhotoUpdate("1");
        List<PhotoUpdate> list = new ArrayList<PhotoUpdate>();
        list.add(update);
        action = new PhotoUpdateAction(list);
    }

    @Test
    public void testExecuteRecursive() throws DispatchException {
        PhotoUpdate update = new PhotoUpdate("1");
        update = new PhotoUpdate("1");

        List<PhotoUpdate> list = new ArrayList<PhotoUpdate>();
        list.add(update);
        update = new PhotoUpdate("2");
        list.add(update);
        action = new PhotoUpdateAction(list);
        handler.execute(action, null);
        verify(recursive).execute(recursiveActionCaptor.capture());
        verify(controllerAsync)
                .execute(captorAction.capture(), captorCallback.capture());

        GetPhotoDataAction request = captorAction.getValue();
        assertEquals(1, request.getImageKeys().size());
        assertEquals("1", request.getImageKeys().get(0));

        PhotoUpdateAction nextAction = recursiveActionCaptor.getValue();
        assertEquals(1, nextAction.getWorkLoad().size());
        update = nextAction.getWorkLoad().get(0);
        assertEquals("2", update.getPhotoId());
    }

    @Test
    public void testExecute() throws DispatchException {
        handler = new PhotoUpdateHandler(2, MAX_PHOTO_TICKS,
                new ImageSpecs(1, 1, 1, 1), controllerAsync, recursive);

        PhotoUpdate update = new PhotoUpdate("1");
        update = new PhotoUpdate("1");

        List<PhotoUpdate> list = new ArrayList<PhotoUpdate>();
        list.add(update);
        update = new PhotoUpdate("2");
        list.add(update);
        update = new PhotoUpdate("3");
        list.add(update);
        update = new PhotoUpdate("4");
        list.add(update);
        update = new PhotoUpdate("5");
        list.add(update);
        update = new PhotoUpdate("6");
        list.add(update);
        update = new PhotoUpdate("7");
        list.add(update);
        update = new PhotoUpdate("8");
        list.add(update);
        action = new PhotoUpdateAction(list);
        handler.execute(action, null);
        verify(recursive).execute(recursiveActionCaptor.capture());
        verify(controllerAsync, times(2))
                .execute(captorAction.capture(), captorCallback.capture());

        List<GetPhotoDataAction> actionList = captorAction.getAllValues();
        assertEquals(2, actionList.size());
        assertEquals(MAX_PHOTO_TICKS, actionList.get(0).getImageKeys().size());
        assertEquals("1", actionList.get(0).getImageKeys().get(0));
        assertEquals("2", actionList.get(0).getImageKeys().get(1));
        assertEquals("3", actionList.get(0).getImageKeys().get(2));
        assertEquals("4", actionList.get(1).getImageKeys().get(0));
        assertEquals("5", actionList.get(1).getImageKeys().get(1));
        assertEquals("6", actionList.get(1).getImageKeys().get(2));

        PhotoUpdateAction nextAction = recursiveActionCaptor.getValue();
        assertEquals(2, nextAction.getWorkLoad().size());
        update = nextAction.getWorkLoad().get(0);
        assertEquals("7", update.getPhotoId());
        update = nextAction.getWorkLoad().get(1);
        assertEquals("8", update.getPhotoId());
    }

    @Test
    public void testExecute5() throws DispatchException {
        handler = new PhotoUpdateHandler(2, MAX_PHOTO_TICKS,
                new ImageSpecs(1, 1, 1, 1), controllerAsync, recursive);

        PhotoUpdate update = new PhotoUpdate("1");
        update = new PhotoUpdate("1");

        List<PhotoUpdate> list = new ArrayList<PhotoUpdate>();
        list.add(update);
        update = new PhotoUpdate("2");
        list.add(update);
        update = new PhotoUpdate("3");
        list.add(update);
        update = new PhotoUpdate("4");
        list.add(update);
        update = new PhotoUpdate("5");
        list.add(update);
        action = new PhotoUpdateAction(list);
        handler.execute(action, null);
        verifyNoMoreInteractions(recursive);
        verify(controllerAsync, times(2))
                .execute(captorAction.capture(), captorCallback.capture());

        List<GetPhotoDataAction> request = captorAction.getAllValues();
        assertEquals(MAX_PHOTO_TICKS, request.get(0).getImageKeys().size());
        assertEquals("1", request.get(0).getImageKeys().get(0));
        assertEquals("2", request.get(0).getImageKeys().get(1));
        assertEquals("3", request.get(0).getImageKeys().get(2));
        assertEquals("4", request.get(1).getImageKeys().get(0));
        assertEquals("5", request.get(1).getImageKeys().get(1));
    }
}
