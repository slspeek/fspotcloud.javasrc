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

import com.google.common.collect.ImmutableList;
import com.googlecode.fspotcloud.model.jpa.peerdatabase.PeerDatabaseEntity;
import com.googlecode.fspotcloud.model.jpa.photo.PhotoEntity;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.server.image.ImageHelper;
import com.googlecode.fspotcloud.server.model.api.*;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.peer.PhotoData;
import com.googlecode.fspotcloud.shared.peer.PhotoDataResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PhotoDataCallbackTest {
    private static final int VERSION = 15;
    private static final byte[] IMAGE_DATA = new byte[]{0, 1};
    private static final byte[] THUMB_DATA = new byte[]{0};
    private static final String DESCRIPTION = "description";
    private static final String PHOTO_ID = "1";
    private static final String TAG_ID = "fooMock";
    PhotoDao photoManager;
    PeerDatabaseDao peerDatabaseDao;
    PeerDatabase peer;
    TagDao tagManager;
    Photo photo1;
    PhotoDataResult result;
    PhotoData data;
    Date date = new Date(10);
    PhotoDataCallback callback;
    ArgumentCaptor<List<Photo>> argumentCaptor = (ArgumentCaptor<List<Photo>>) (Object) ArgumentCaptor.forClass(List.class);
    private ArrayList<PhotoData> dataList;
    private Tag tag1;

    @Before
    public void setUp() throws Exception {
        photoManager = mock(PhotoDao.class);
        tagManager = mock(TagDao.class);
        photo1 = new PhotoEntity();
        tag1 = new TagEntity();
        tag1.setId(TAG_ID);
        photo1.setId(PHOTO_ID);
        data = new PhotoData(PHOTO_ID, DESCRIPTION, date, IMAGE_DATA,
                THUMB_DATA, ImmutableList.of(TAG_ID), VERSION);
        dataList = new ArrayList<PhotoData>();
        dataList.add(data);
        result = new PhotoDataResult(dataList);
        when(photoManager.findOrNew(PHOTO_ID)).thenReturn(photo1);
        when(tagManager.find(TAG_ID)).thenReturn(tag1);
        peer = new PeerDatabaseEntity();
        peerDatabaseDao = mock(PeerDatabaseDao.class);
        when(peerDatabaseDao.get()).thenReturn(peer);
        callback = new PhotoDataCallback(photoManager, mock(ImageHelper.class),
                tagManager, peerDatabaseDao);
    }

    @Test
    public void testSerialize() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(callback);
        out.close();
    }

    @Test
    public void testOnSuccess() {
        peer.setCachedTagTree(new ArrayList<TagNode>());
        callback.onSuccess(result);
        assertEquals(date, photo1.getDate());
        assertEquals(DESCRIPTION, photo1.getDescription());
        verify(photoManager).saveAll(argumentCaptor.capture());
        assertEquals(photo1, argumentCaptor.getValue().get(0));

        //        assertEquals(IMAGE_DATA, photo1.getImage());
        //        assertEquals(THUMB_DATA, photo1.getThumb());
        //        assertTrue(photo1.isThumbLoaded());
        //        assertTrue(photo1.isImageLoaded());
        PhotoInfo info = tag1.getCachedPhotoList().first();
        assertEquals(PHOTO_ID, info.getId());
        assertEquals(VERSION, info.getVersion());
        verify(peerDatabaseDao).resetCachedTagTree();
    }
}
