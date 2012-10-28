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

package com.googlecode.fspotcloud.server.control.callback;

import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseEntity;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.peer.TagData;
import com.googlecode.fspotcloud.shared.peer.TagDataResult;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TagDataCallbackTest {
    TagDao tagManager;
    Tag tag;
    PeerDatabase peer = new PeerDatabaseEntity();
    PeerDatabaseDao peers;
    TagDataCallback callback;
    final String TAGNAME = "Foo";
    final String TAGID = "FooID";
    TagDataResult incoming;
    TagData row;
    ArgumentCaptor<List<Tag>> argumentCaptor = (ArgumentCaptor<List<Tag>>) (Object) ArgumentCaptor.forClass(List.class);

    @Before
    public void setUp() throws Exception {
        tagManager = mock(TagDao.class);
        peers = mock(PeerDatabaseDao.class);
        when(peers.get()).thenReturn(peer);
        tag = new TagEntity();
        tag.setId(TAGID);
        row = new TagData(TAGID, TAGNAME, null, 10);

        List<TagData> list = new ArrayList<TagData>();
        list.add(row);
        incoming = new TagDataResult(list);
        when(tagManager.findOrNew(TAGID)).thenReturn(tag);
        callback = new TagDataCallback(tagManager, peers);
    }

    @Test
    public void testSerialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(callback);
        out.close();
    }

    @Test
    public void testRecieveTagData() throws DispatchException {
        callback.onSuccess(incoming);
        assertEquals(10, tag.getCount());
        assertEquals(TAGNAME, tag.getTagName());
        assertNull(tag.getParentId());
        verify(peers).resetCachedTagTree();
        verifyNoMoreInteractions(peers);
    }

    @Test
    public void testRecieveTagDataImported() throws DispatchException {
        tag.setImportIssued(true);
        callback.onSuccess(incoming);
        assertEquals(10, tag.getCount());
        assertEquals(TAGNAME, tag.getTagName());
        assertNull(tag.getParentId());
        verify(peers).resetCachedTagTree();
        verifyNoMoreInteractions(peers);
    }
}
