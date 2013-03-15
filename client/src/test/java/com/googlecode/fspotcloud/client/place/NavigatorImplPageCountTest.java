package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagNodeTestFactory;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class NavigatorImplPageCountTest {


    private static final String TAG_ID = "1";
    @Inject
    private  DataManager dataManager;
    @Inject
    private NavigatorImpl navigator;
    @Inject
    private TagNodeTestFactory tagNodeTestFactory;
    @Inject
    private ArgumentCaptor<AsyncCallback<TagNode>> captor;
    @Inject
    private AsyncCallback<Integer> integerAsyncCallback;

    @Test
    public void testPageCount() throws Exception {

        navigator.getPageCountAsync(TAG_ID, 1,  integerAsyncCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);
        verify(integerAsyncCallback).onSuccess(1);
    }

    @Test
    public void testPageCountWithRounding() throws Exception {

        navigator.getPageCountAsync(TAG_ID, 2, integerAsyncCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);
        verify(integerAsyncCallback).onSuccess(1);
    }

    @Test
    public void testPageCountError() throws Exception {

        navigator.getPageCountAsync(TAG_ID, 1, integerAsyncCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        final Exception caught = new Exception();
        callback.onFailure(caught);
        verify(integerAsyncCallback).onFailure(caught);
    }

}
