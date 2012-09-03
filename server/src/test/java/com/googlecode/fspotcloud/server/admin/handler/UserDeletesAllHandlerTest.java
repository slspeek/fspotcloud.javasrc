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

import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllPhotosAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllTagsAction;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.shared.dashboard.UserDeletesAllAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.user.IAdminPermission;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class UserDeletesAllHandlerTest {
    @Inject
    UserDeletesAllHandler handler;

    @Test
    public void testNormalExecute(TaskQueueDispatch dispatchAsync,
                                  IAdminPermission IAdminPermission, PeerDatabaseDao peerDatabaseManager)
            throws Exception {
        UserDeletesAllAction action = new UserDeletesAllAction();
        VoidResult result = handler.execute(action, null);

        verify(peerDatabaseManager).deleteBulk(1);
        verify(dispatchAsync).execute(new DeleteAllTagsAction());
        verify(dispatchAsync).execute(new DeleteAllPhotosAction());
    }

    @Test(expected = SecurityException.class)
    public void testUnAuthorizedExecute(TaskQueueDispatch dispatchAsync,
                                        IAdminPermission iAdminPermission, PeerDatabaseDao peerDatabaseManager)
            throws Exception {
        doThrow(new SecurityException()).when(iAdminPermission)
                .checkAdminPermission();

        UserDeletesAllAction action = new UserDeletesAllAction();
        VoidResult result = handler.execute(action, null);
        fail();
    }
}
