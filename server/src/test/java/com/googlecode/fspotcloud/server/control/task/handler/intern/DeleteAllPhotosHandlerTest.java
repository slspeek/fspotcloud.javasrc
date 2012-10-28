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

import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllPhotosAction;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.server.ExecutionContext;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class DeleteAllPhotosHandlerTest {
    @Inject
    private DeleteAllPhotosHandler instance;

    @Test
    public void testExecuteWithRecursion(PhotoDao photoDao,
                                         TaskQueueDispatch dispatch) throws Exception {
        DeleteAllPhotosAction action = new DeleteAllPhotosAction();
        ExecutionContext context = null;
        VoidResult expResult = new VoidResult();
        VoidResult result = instance.execute(action, context);
        assertEquals(expResult, result);
        verify(dispatch).execute(new DeleteAllPhotosAction());
        verify(photoDao).deleteBulk(30);
        verify(photoDao).isEmpty();
        verifyNoMoreInteractions(dispatch, photoDao);
    }

    @Test
    public void testExecuteWithoutRecursion(PhotoDao photoDao,
                                            TaskQueueDispatch dispatch) throws Exception {
        when(photoDao.isEmpty()).thenReturn(Boolean.TRUE);

        DeleteAllPhotosAction action = new DeleteAllPhotosAction();
        ExecutionContext context = null;
        VoidResult expResult = new VoidResult();
        VoidResult result = instance.execute(action, context);
        assertEquals(expResult, result);
        verify(photoDao).deleteBulk(30);
        verify(photoDao).isEmpty();
        verifyNoMoreInteractions(dispatch, photoDao);
    }
}
