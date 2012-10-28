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
package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseEntity;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.server.model.tag.IUserGroupHelper;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * DOCUMENT ME!
 *
 * @author steven
 */
@RunWith(JukitoRunner.class)
public class GetTagTreeHandlerTest {
    @Inject
    GetTagTreeHandler handler;
    @Inject
    IUserGroupHelper helper;
    private final GetTagTreeAction action = new GetTagTreeAction();

    @Test
    public void testNormalExecuteNoTags(TagDao tagManager, PeerDatabaseDao peers)
            throws Exception {
        when(peers.get()).thenReturn(new PeerDatabaseEntity());

        TagTreeResult result = handler.execute(action, null);
        verify(tagManager).getTags();
        assertTrue(result.getTree().isEmpty());
    }

    @Test
    public void testNormalExecuteOneUnimportedTags(TagDao tagManager,
                                                   PeerDatabaseDao peers) throws Exception {
        when(peers.get()).thenReturn(new PeerDatabaseEntity());

        List<TagNode> list = newArrayList();
        list.add(new TagNode("1"));
        when(tagManager.getTags()).thenReturn(list);

        TagTreeResult result = handler.execute(action, null);
        verify(tagManager).getTags();
        //No imported tags
        assertEquals(0, result.getTree().size());
    }

    @Test
    public void testNormalExecuteOneImportedTags(TagDao tagManager,
                                                 PeerDatabaseDao peers) throws Exception {
        when(peers.get()).thenReturn(new PeerDatabaseEntity());
        when(helper.getVisibleTagIds()).thenReturn(newHashSet("1"));

        List<TagNode> list = newArrayList();
        final TagNode tagNode = new TagNode("1");
        tagNode.setImportIssued(true);
        list.add(tagNode);
        when(tagManager.getTags()).thenReturn(list);

        TagTreeResult result = handler.execute(action, null);
        verify(tagManager).getTags();
        assertEquals(1, result.getTree().size());
    }

    @Test
    public void testNormalExecuteCachehit(TagDao tagManager,
                                          PeerDatabaseDao peers) throws Exception {
        when(helper.getVisibleTagIds()).thenReturn(newHashSet("1"));

        final PeerDatabaseEntity peerDatabaseEntity = new PeerDatabaseEntity();

        List<TagNode> list = newArrayList();
        final TagNode tagNode = new TagNode("1");
        tagNode.setImportIssued(true);
        list.add(tagNode);

        peerDatabaseEntity.setCachedTagTree(newArrayList(tagNode));
        when(peers.get()).thenReturn(peerDatabaseEntity);

        TagTreeResult result = handler.execute(action, null);
        assertEquals(1, result.getTree().size());
        verifyNoMoreInteractions(tagManager);
    }
}
