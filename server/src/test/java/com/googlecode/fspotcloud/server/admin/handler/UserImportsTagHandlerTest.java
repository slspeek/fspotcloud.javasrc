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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.server.control.callback.TagUpdateInstructionsCallback;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.dashboard.UserImportsTagAction;
import com.googlecode.fspotcloud.shared.peer.GetTagUpdateInstructionsAction;
import com.googlecode.fspotcloud.user.IAdminPermission;

@RunWith(JukitoRunner.class)
public class UserImportsTagHandlerTest {
	private final String TAG_ID = "1";
	@Inject
	UserImportsTagHandler handler;
	UserImportsTagAction action = new UserImportsTagAction(TAG_ID);
	Tag tagOne;
	@Inject
	private PeerDatabaseDao peerDatabaseDao;

	@Before
	public void setup() {
		tagOne = new TagEntity();
		tagOne.setId(TAG_ID);
	}

	@Test
	public void testNormalExecute(TagDao tagManager,
			ControllerDispatchAsync dispatchAsync,
			ArgumentCaptor<GetTagUpdateInstructionsAction> actionCaptor,
			ArgumentCaptor<TagUpdateInstructionsCallback> callbackCaptor)
			throws Exception {
		when(tagManager.find(TAG_ID)).thenReturn(tagOne);

		handler.execute(action, null);
		verify(peerDatabaseDao).resetCachedTagTrees();
		verify(dispatchAsync).execute(actionCaptor.capture(),
				callbackCaptor.capture());

		GetTagUpdateInstructionsAction action = actionCaptor.getValue();
		assertTrue(action.getPhotosOnServer().isEmpty());

		assertEquals(TAG_ID, action.getTagId());
	}

	//@Test(expected = SecurityException.class)
	public void testUnAuthorizedExecute(IAdminPermission adminPermission)
			throws Exception {
		doThrow(new SecurityException()).when(adminPermission)
				.checkAdminPermission();

		handler.execute(action, null);
		fail();
	}
}
