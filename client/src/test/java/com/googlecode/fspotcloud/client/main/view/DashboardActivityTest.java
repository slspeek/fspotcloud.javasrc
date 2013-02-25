package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.view.api.DashboardView;
import com.googlecode.fspotcloud.client.main.view.api.PeerActionsView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.HomePlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JukitoRunner.class)
public class DashboardActivityTest {


    @Inject
    private DashboardActivity dashboardPresenter;
    @Inject
    private DashboardView dashboardView;
    @Inject
    @AdminTreeView
    private TreeView.TreePresenter treePresenter;
    @Inject
    private PeerActionsView.PeerActionsPresenter peerActionsPresenter;
    @Inject
    private TagDetailsActivity tagDetailsActivityFactory;
    @Inject
    private IClientLoginManager clientLoginManager;
    @Inject
    private IPlaceController IPlaceController;

    @Inject
    private ArgumentCaptor<AsyncCallback<UserInfo>> asyncCallbackArgumentCaptor;

    private UserInfo userInfo;

    @Test
    public void testInitAdmin() throws Exception {
        dashboardPresenter.init();
        verify(clientLoginManager).getUserInfoAsync(asyncCallbackArgumentCaptor.capture());
        AsyncCallback<UserInfo> callback = asyncCallbackArgumentCaptor.getValue();
        userInfo = new UserInfo("sls", true, true, "", "", new Date(0), null);
        callback.onSuccess(userInfo);
        verify(peerActionsPresenter).init();
        verify(treePresenter).init();
        verifyNoMoreInteractions(IPlaceController, clientLoginManager, treePresenter, peerActionsPresenter);
    }

    @Test
    public void testLoggedOnNotAdmin() throws Exception {
        dashboardPresenter.init();
        verify(clientLoginManager).getUserInfoAsync(asyncCallbackArgumentCaptor.capture());
        AsyncCallback<UserInfo> callback = asyncCallbackArgumentCaptor.getValue();
        userInfo = new UserInfo("sls", false, true, "", "", new Date(0), null);
        callback.onSuccess(userInfo);
        verify(IPlaceController).goTo(new HomePlace());
        verifyNoMoreInteractions(IPlaceController, clientLoginManager, treePresenter, peerActionsPresenter);
    }

    @Test
    public void testNotLoggedOn() throws Exception {
        dashboardPresenter.init();
        verify(clientLoginManager).getUserInfoAsync(asyncCallbackArgumentCaptor.capture());
        AsyncCallback<UserInfo> callback = asyncCallbackArgumentCaptor.getValue();
        userInfo = new UserInfo("sls", false, false, "", "", new Date(0), null);
        callback.onSuccess(userInfo);
        verify(clientLoginManager).redirectToLogin();
        verifyNoMoreInteractions(IPlaceController, clientLoginManager, treePresenter, peerActionsPresenter);
    }

    @Test
    public void testErrorInClientLoginManager() throws Exception {
        dashboardPresenter.init();
        verify(clientLoginManager).getUserInfoAsync(asyncCallbackArgumentCaptor.capture());
        AsyncCallback<UserInfo> callback = asyncCallbackArgumentCaptor.getValue();
        callback.onFailure(new RuntimeException());
        verify(IPlaceController).goTo(new HomePlace());
        verifyNoMoreInteractions(IPlaceController, clientLoginManager, treePresenter, peerActionsPresenter);
    }

    @Test
    public void testOnStop() throws Exception {
        dashboardPresenter.onStop();
        verify(peerActionsPresenter).stop();
        verifyNoMoreInteractions(peerActionsPresenter, IPlaceController, clientLoginManager, treePresenter);
    }
}
