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

import com.googlecode.botdispatch.model.api.Commands;
import com.googlecode.fspotcloud.shared.dashboard.UserDeletesAllCommandsAction;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Provider;

import static org.mockito.Mockito.*;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class UserDeletesAllCommandsHandlerTest {
    @Mock
    Commands commandManager;
    @Mock
    UserService userService;
    UserDeletesAllCommandsHandler handler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Provider<UserService> userServiceProvider = new Provider<UserService>() {
            @Override
            public UserService get() {
                return userService;
            }
        };

        handler = new UserDeletesAllCommandsHandler(commandManager,
                userServiceProvider);
    }

    /**
     * Test of execute method, of class UserDeletesAllCommandsHandler.
     *
     * @throws Exception DOCUMENT ME!
     */
    @Test(expected = SecurityException.class)
    public void testExecuteNotBeingAdmin() throws Exception {
        when(userService.isUserAdmin()).thenReturn(Boolean.FALSE);
        handler.execute(new UserDeletesAllCommandsAction(), null);
        verifyNoMoreInteractions(commandManager, userService);
    }

    @Test
    public void execute() throws DispatchException {
        when(userService.isUserAdmin()).thenReturn(Boolean.TRUE);
        handler.execute(new UserDeletesAllCommandsAction(), null);
        verify(commandManager).deleteAll();
        verifyNoMoreInteractions(commandManager);
        verify(userService).isUserAdmin();
    }

    @Test(expected = ActionException.class)
    public void commandManagerFails() throws DispatchException {
        when(userService.isUserAdmin()).thenReturn(Boolean.TRUE);
        doThrow(new RuntimeException()).when(commandManager).deleteAll();
        handler.execute(new UserDeletesAllCommandsAction(), null);
    }
}
