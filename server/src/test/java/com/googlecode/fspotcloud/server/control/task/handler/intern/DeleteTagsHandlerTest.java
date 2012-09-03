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

import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllTagsAction;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class DeleteTagsHandlerTest {
    DeleteTagsHandler target;
    @Mock
    TaskQueueDispatch dispatchAsync;
    @Mock
    TagDao tagManager;
    @Captor
    ArgumentCaptor<DeleteAllTagsAction> newAction;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        target = new DeleteTagsHandler(dispatchAsync, tagManager);
        System.out.println(tagManager);
    }

    @Test
    public void testRecursionStop() throws DispatchException {
        when(tagManager.isEmpty()).thenReturn(true);
        target.execute(new DeleteAllTagsAction(), null);
        verifyNoMoreInteractions(dispatchAsync);
    }

    @Test
    public void testRecursion() throws DispatchException {
        when(tagManager.isEmpty()).thenReturn(false);
        target.execute(new DeleteAllTagsAction(), null);
        verify(dispatchAsync).execute(newAction.capture());
        assertNotNull(newAction.getValue());
    }
}
