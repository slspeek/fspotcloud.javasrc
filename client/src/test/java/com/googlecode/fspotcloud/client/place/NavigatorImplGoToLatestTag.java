package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagNodeTestFactory;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(JukitoRunner.class)
public class NavigatorImplGoToLatestTag {


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
    private IPlaceController placeController;


    @Test
    public void testGoToLatestTag() throws Exception {
        navigator.goToLatestTag();
        verify(dataManager).getTagTree(captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);

        verify(placeController).goTo(new BasePlace("1", TagNodeTestFactory.FIRST_PICTURE_INFO.getId(), 0, 0, false));

    }

    @Test
    public void testGoToLatestTagFailure() throws Exception {
        navigator.goToLatestTag();
        verify(dataManager).getTagTree(captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onFailure(new RuntimeException("Foo"));

        verifyZeroInteractions(placeController);

    }

    @Test
    public void testGoToLatestTagNoPublicTags() throws Exception {
        navigator.goToLatestTag();
        verify(dataManager).getTagTree(captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();

        callback.onSuccess(new TagNode());



    }

}
