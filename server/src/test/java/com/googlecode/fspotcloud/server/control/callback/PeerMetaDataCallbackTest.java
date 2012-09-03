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
package com.googlecode.fspotcloud.server.control.callback;

import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseEntity;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.peer.GetPeerUpdateInstructionsAction;
import com.googlecode.fspotcloud.shared.peer.PeerMetaDataResult;
import com.googlecode.fspotcloud.shared.peer.TagData;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class PeerMetaDataCallbackTest {
    public static final String TAG_ID = "1";
    @Inject
    PeerMetaDataCallback callback;

    @Test
    public void testNormalExecute(PeerDatabaseDao defaultPeer,
                                  ControllerDispatchAsync dispatchAsync, TagDao tagManager,
                                  ArgumentCaptor<GetPeerUpdateInstructionsAction> actionCaptor,
                                  ArgumentCaptor<PeerUpdateInstructionsCallback> callbackCaptor)
            throws Exception {
        PeerDatabase peer = new PeerDatabaseEntity();
        List<Tag> tagList = newArrayList();
        Tag tag = new TagEntity();
        tag.setId(TAG_ID);
        tagList.add(tag);
        when(tagManager.findAll(1000)).thenReturn(tagList);
        when(defaultPeer.get()).thenReturn(peer);

        PeerMetaDataResult result = new PeerMetaDataResult(10, 20);
        callback.onSuccess(result);

        verify(dispatchAsync)
                .execute(actionCaptor.capture(), callbackCaptor.capture());

        GetPeerUpdateInstructionsAction action = actionCaptor.getValue();
        TagData data = action.getTagsOnServer().get(0);
        assertEquals(TAG_ID, data.getTagId());
        assertEquals(0, data.getCount());
        assertEquals(10, peer.getTagCount());
        assertEquals(20, peer.getPeerPhotoCount());
    }
}
