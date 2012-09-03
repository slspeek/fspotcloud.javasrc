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

package com.googlecode.fspotcloud.client.main.data;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.fspotcloud.client.data.DataManagerImpl;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import junit.framework.TestCase;
import net.customware.gwt.dispatch.client.DispatchAsync;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class DataManagerImplTest extends TestCase {
    private final Logger log = Logger.getLogger(DataManagerImplTest.class.getName());
    private static final String ID = "1";
    private static final String WRONG_ID = "2";
    private DataManagerImpl dataManager;
    AsyncCallback<List<TagNode>> firstCall;
    AsyncCallback<TagNode> secondCall;
    AsyncCallback<List<TagNode>> thirdCall;
    ArgumentCaptor<AsyncCallback<List<TagNode>>> remoteCallCaptor;
    ArgumentCaptor<AsyncCallback<TagTreeResult>> newRemoteCallCaptor;
    TagNode tagNode = new TagNode(ID);
    DispatchAsync dispatchAsync;
    ArgumentCaptor<GetTagTreeAction> actionCapture;

    @Override
    protected void setUp() throws Exception {
        firstCall = mock(AsyncCallback.class);
        secondCall = mock(AsyncCallback.class);
        thirdCall = mock(AsyncCallback.class);
        dispatchAsync = mock(DispatchAsync.class);
        dataManager = new DataManagerImpl(dispatchAsync);
        remoteCallCaptor = (ArgumentCaptor<AsyncCallback<List<TagNode>>>) (Object) ArgumentCaptor.forClass(AsyncCallback.class);
        newRemoteCallCaptor = (ArgumentCaptor<AsyncCallback<TagTreeResult>>) (Object) ArgumentCaptor.forClass(AsyncCallback.class);
        actionCapture = ArgumentCaptor.forClass(GetTagTreeAction.class);
        super.setUp();
    }

    public void testSimpleCall() {
        dataManager.getTagTree(firstCall);

        verify(dispatchAsync)
                .execute(actionCapture.capture(), newRemoteCallCaptor.capture());

        AsyncCallback<TagTreeResult> callback = newRemoteCallCaptor.getValue();
        List<TagNode> result = new ArrayList<TagNode>();
        callback.onSuccess(new TagTreeResult(result));
        verify(firstCall).onSuccess(result);
    }

    public void testTwoCalls() {
        dataManager.getTagTree(firstCall);
        verify(dispatchAsync)
                .execute(actionCapture.capture(), newRemoteCallCaptor.capture());
        dataManager.getTagNode(ID, secondCall);
        verifyNoMoreInteractions(dispatchAsync);

        AsyncCallback<TagTreeResult> callback = newRemoteCallCaptor.getValue();
        List<TagNode> result = new ArrayList<TagNode>();
        result.add(tagNode);
        callback.onSuccess(new TagTreeResult(result));
        verify(firstCall).onSuccess(result);
        verify(secondCall).onSuccess(tagNode);
    }

    public void testThreeCalls() {
        dataManager.getTagTree(firstCall);
        verify(dispatchAsync)
                .execute(actionCapture.capture(), newRemoteCallCaptor.capture());
        dataManager.getTagNode(ID, secondCall);
        verifyNoMoreInteractions(dispatchAsync);
        dataManager.getTagTree(thirdCall);
        verifyNoMoreInteractions(dispatchAsync);

        AsyncCallback<TagTreeResult> callback = newRemoteCallCaptor.getValue();
        List<TagNode> result = new ArrayList<TagNode>();
        result.add(tagNode);
        callback.onSuccess(new TagTreeResult(result));
        verify(firstCall).onSuccess(result);
        verify(secondCall).onSuccess(tagNode);
        verify(thirdCall).onSuccess(result);
    }

    public void testThreeCallsWithWrongId() {
        dataManager.getTagTree(firstCall);
        verify(dispatchAsync)
                .execute(actionCapture.capture(), newRemoteCallCaptor.capture());
        dataManager.getTagNode(WRONG_ID, secondCall);
        verifyNoMoreInteractions(dispatchAsync);
        dataManager.getTagTree(thirdCall);
        verifyNoMoreInteractions(dispatchAsync);

        AsyncCallback<TagTreeResult> callback = newRemoteCallCaptor.getValue();
        List<TagNode> result = new ArrayList<TagNode>();
        result.add(tagNode);
        callback.onSuccess(new TagTreeResult(result));
        verify(firstCall).onSuccess(result);
        verify(secondCall).onSuccess(null);
        verify(thirdCall).onSuccess(result);
    }

    public void testThreeFirstWithWrongId() {
        dataManager.getTagNode(WRONG_ID, secondCall);
        verify(dispatchAsync)
                .execute(actionCapture.capture(), newRemoteCallCaptor.capture());
        dataManager.getTagTree(firstCall);
        verifyNoMoreInteractions(dispatchAsync);
        dataManager.getTagTree(thirdCall);
        verifyNoMoreInteractions(dispatchAsync);

        AsyncCallback<TagTreeResult> callback = newRemoteCallCaptor.getValue();
        List<TagNode> result = new ArrayList<TagNode>();
        result.add(tagNode);
        callback.onSuccess(new TagTreeResult(result));

        verify(firstCall).onSuccess(result);
        verify(secondCall).onSuccess(null);
        verify(thirdCall).onSuccess(result);
    }

    public void testOnceCalledbackNoMoreDelay() {
        dataManager.getTagTree(firstCall);
        verify(dispatchAsync)
                .execute(actionCapture.capture(), newRemoteCallCaptor.capture());

        AsyncCallback<TagTreeResult> callback = newRemoteCallCaptor.getValue();
        List<TagNode> result = new ArrayList<TagNode>();
        result.add(tagNode);
        callback.onSuccess(new TagTreeResult(result));

        verify(firstCall).onSuccess(result);

        dataManager.getTagNode(WRONG_ID, secondCall);
        verify(secondCall).onSuccess(null);
        verifyNoMoreInteractions(dispatchAsync);
        dataManager.getTagTree(thirdCall);
        verify(thirdCall).onSuccess(result);
        verifyNoMoreInteractions(dispatchAsync);
    }
}
