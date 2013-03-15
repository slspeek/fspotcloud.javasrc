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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class NavigatorImplPageTest {


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



    @Test
    public void testGetPage() throws Exception {
        navigator.getPageAsync(TAG_ID, 1, 0, photoInfoListCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);
        verify(photoInfoListCallback).onSuccess(listCaptor.capture());
        List<PhotoInfo> list = listCaptor.getValue();
        Assert.assertEquals(TagNodeTestFactory.FIRST_PICTURE_INFO, list.get(0));
    }

    @Test
    public void testGetPageNullNode() throws Exception {
        navigator.getPageAsync(TAG_ID, 1, 0, photoInfoListCallback);
        verify(dataManager).getTagNode(any(String.class), captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();

        callback.onSuccess(null);
        verify(photoInfoListCallback).onSuccess(listCaptor.capture());
        List<PhotoInfo> list = listCaptor.getValue();
        Assert.assertEquals(0, list.size());
    }


}
