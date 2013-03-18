package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagNodeTestFactory;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class NavigatorImplGetPageAsync {


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
    private AsyncCallback<List<PhotoInfo>> photoInfoListCallback;
    @Inject
    private ArgumentCaptor<List<PhotoInfo>> listCaptor;

    @Inject private ArgumentCaptor<Throwable> throwableArgumentCaptor;



    @Test
    public void testGetPage() throws Exception {
        navigator.getPageAsync(TAG_ID, "42", 1, photoInfoListCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);
        verify(photoInfoListCallback).onSuccess(listCaptor.capture());
        List<PhotoInfo> list = listCaptor.getValue();
        Assert.assertEquals(TagNodeTestFactory.FIRST_PICTURE_INFO, list.get(0));
    }

    @Test
    public void testGetPageNotFound() throws Exception {
        navigator.getPageAsync(TAG_ID, "41", 1, photoInfoListCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);
        verify(photoInfoListCallback).onFailure(throwableArgumentCaptor.capture());
        Throwable t = throwableArgumentCaptor.getValue();
        String mesg = t.getMessage();
        assertTrue(mesg.contains("41"));

    }

    @Test
    public void testGetPageFailure() throws Exception {
        navigator.getPageAsync(TAG_ID, "42", 1, photoInfoListCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();

        final RuntimeException caught = new RuntimeException();
        callback.onFailure(caught);
        verify(photoInfoListCallback).onFailure(caught);

    }


}
