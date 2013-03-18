package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.main.*;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(JukitoRunner.class)
public class NavigatorImplGoToLatestNoTagsPublicTest {


    private static final String TAG_ID = "1";
    @Inject
    private  DataManager dataManager;
    @Inject
    private NavigatorImpl navigator;
    @Inject
    private ArgumentCaptor<AsyncCallback<TagNode>> captor;

    @Inject
    private IPlaceController placeController;
    @Inject
    private IClientLoginManager clientLoginManager;


    @Inject private ArgumentCaptor<AsyncCallback<UserInfo>> clientCaptor;

    @Test
    public void testGoToLatestTagNoPublicTagsAdmin() throws Exception {
        navigator.goToLatestTag();
        verify(dataManager).getTagTree(captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();

        callback.onSuccess(new TagNode());

        verify(clientLoginManager).getUserInfoAsync(clientCaptor.capture());

        AsyncCallback<UserInfo> userInfoAsyncCallback = clientCaptor.getValue();
        userInfoAsyncCallback.onSuccess(new UserInfo("sls", true, true));
        verify(placeController).goTo(TagPlace.DEFAULT);
    }
    @Test
    public void testGoToLatestTagNoPublicTagsLoggedOn() throws Exception {
        navigator.goToLatestTag();
        verify(dataManager).getTagTree(captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();

        callback.onSuccess(new TagNode());

        verify(clientLoginManager).getUserInfoAsync(clientCaptor.capture());

        AsyncCallback<UserInfo> userInfoAsyncCallback = clientCaptor.getValue();
        userInfoAsyncCallback.onSuccess(new UserInfo("sls", false, true));
        verifyZeroInteractions(placeController);
    }

    @Test
    public void testGoToLatestTagNoPublicTagsNotLoggedOn() throws Exception {
        navigator.goToLatestTag();
        verify(dataManager).getTagTree(captor.capture());
        AsyncCallback<TagNode> callback = captor.getValue();

        callback.onSuccess(new TagNode());

        verify(clientLoginManager).getUserInfoAsync(clientCaptor.capture());

        AsyncCallback<UserInfo> userInfoAsyncCallback = clientCaptor.getValue();
        userInfoAsyncCallback.onSuccess(new UserInfo("sls", false, false));
        verifyZeroInteractions(placeController);
        verify(clientLoginManager).redirectToLogin();
    }
}
