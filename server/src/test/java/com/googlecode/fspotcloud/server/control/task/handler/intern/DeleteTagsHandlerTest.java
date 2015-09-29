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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllTagsAction;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;

@RunWith(JukitoRunner.class)
public class DeleteTagsHandlerTest {
	DeleteTagsHandler target;
	@Inject
	TaskQueueDispatch dispatchAsync;
	@Inject
	TagDao tagManager;
	@Inject
	PeerDatabaseDao peerDatabaseDao;
	@Inject
	ArgumentCaptor<DeleteAllTagsAction> newAction;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		target = new DeleteTagsHandler(dispatchAsync, tagManager,
				peerDatabaseDao);
		System.out.println(tagManager);
	}

	@Test
	public void testRecursionStop() throws DispatchException {
		when(tagManager.isEmpty()).thenReturn(true);
		target.execute(new DeleteAllTagsAction(), null);
		verifyNoMoreInteractions(dispatchAsync);
		verify(peerDatabaseDao).resetCachedTagTrees();
	}

	@Test
	public void testRecursion() throws DispatchException {
		when(tagManager.isEmpty()).thenReturn(false);
		target.execute(new DeleteAllTagsAction(), null);
		verify(dispatchAsync).execute(newAction.capture());
		assertNotNull(newAction.getValue());
	}
}
