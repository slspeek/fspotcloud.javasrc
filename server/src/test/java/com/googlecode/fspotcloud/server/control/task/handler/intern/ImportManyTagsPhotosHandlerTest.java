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
import com.googlecode.fspotcloud.server.control.task.actions.intern.ImportManyTagsPhotosAction;
import com.googlecode.fspotcloud.shared.dashboard.UserImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.server.ExecutionContext;
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
public class ImportManyTagsPhotosHandlerTest {
    @Inject
    ImportManyTagsPhotosHandler instance;
    ImportManyTagsPhotosAction action = new ImportManyTagsPhotosAction(newArrayList(
            "1",
            "2",
            "3"));
    ImportManyTagsPhotosAction secondAction = new ImportManyTagsPhotosAction(newArrayList(
            "3"));

    /**
     * Test of execute method, of class ImportManyTagsPhotosHandler.
     */
    @Test
    public void testExecute(TaskQueueDispatch dispatchAsync)
            throws Exception {
        System.out.println("execute");

        ExecutionContext context = null;
        VoidResult expResult = new VoidResult();
        VoidResult result = instance.execute(action, context);
        assertEquals(expResult, result);

        verify(dispatchAsync).execute(new UserImportsTagAction("1"));
        verify(dispatchAsync).execute(new UserImportsTagAction("2"));
        verify(dispatchAsync).execute(secondAction);
        verifyNoMoreInteractions(dispatchAsync);
    }

    public static class Module extends JukitoModule {
        protected void configureTest() {
            bind(Integer.class).annotatedWith(Names.named("maxTicks"))
                    .toInstance(new Integer(2));
        }
    }
}
