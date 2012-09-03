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

import com.google.inject.name.Names;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.control.callback.TagDataCallback;
import com.googlecode.fspotcloud.server.control.task.actions.intern.TagUpdateAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.peer.GetTagDataAction;
import com.googlecode.fspotcloud.shared.peer.TagUpdate;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class TagUpdateHandlerTest {
    @Inject
    TagUpdateHandler instance;

    @Test
    public void testExecute(ControllerDispatchAsync controllerDispatch,
                            TaskQueueDispatch dispatchAsync) throws Exception {
        TagUpdateAction action = new TagUpdateAction(newArrayList(
                new TagUpdate("2"),
                new TagUpdate("3")));
        VoidResult expResult = new VoidResult();
        VoidResult result = instance.execute(action, null);
        assertEquals(expResult, result);
        verify(controllerDispatch)
                .execute(new GetTagDataAction(newArrayList("2")),
                        new TagDataCallback(null, null));
        verify(dispatchAsync)
                .execute(new TagUpdateAction(newArrayList(new TagUpdate("3"))));
        verifyNoMoreInteractions(controllerDispatch, dispatchAsync);
    }

    @Test
    public void testExecuteNoRecursion(
            ControllerDispatchAsync controllerDispatch,
            TaskQueueDispatch dispatchAsync) throws Exception {
        TagUpdateAction action = new TagUpdateAction(newArrayList(
                new TagUpdate("2")));
        VoidResult expResult = new VoidResult();
        VoidResult result = instance.execute(action, null);
        assertEquals(expResult, result);
        verify(controllerDispatch)
                .execute(new GetTagDataAction(newArrayList("2")),
                        new TagDataCallback(null, null));
        verifyNoMoreInteractions(controllerDispatch, dispatchAsync);
    }

    public static class Module extends JukitoModule {
        protected void configureTest() {
            bind(Integer.class).annotatedWith(Names.named("maxTicks"))
                    .toInstance(new Integer(1));
        }
    }
}
