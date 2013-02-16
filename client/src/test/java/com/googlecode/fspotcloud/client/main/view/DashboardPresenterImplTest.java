package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.gin.AdminTreeView;
import com.googlecode.fspotcloud.client.main.view.api.DashboardView;
import com.googlecode.fspotcloud.client.main.view.api.GlobalActionsView;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.HomePlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JukitoRunner.class)
public class DashboardPresenterImplTest {


    @Inject
    private DashboardPresenterImpl dashboardPresenter;
    @Inject
    private DashboardView dashboardView;
    @Inject
    @AdminTreeView
    private TreeView.TreePresenter treePresenter;
    @Inject
    private GlobalActionsView.GlobalActionsPresenter globalActionsPresenter;
    @Inject
    private TagDetailsActivity tagDetailsActivityFactory;
    @Inject
    private IClientLoginManager clientLoginManager;
    @Inject
    private PlaceGoTo placeGoTo;

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
        verify(globalActionsPresenter).init();
        verify(treePresenter).init();
        verifyNoMoreInteractions(placeGoTo, clientLoginManager, treePresenter, globalActionsPresenter);
    }

    @Test
    public void testLoggedOnNotAdmin() throws Exception {
        dashboardPresenter.init();
        verify(clientLoginManager).getUserInfoAsync(asyncCallbackArgumentCaptor.capture());
        AsyncCallback<UserInfo> callback = asyncCallbackArgumentCaptor.getValue();
        userInfo = new UserInfo("sls", false, true, "", "", new Date(0), null);
        callback.onSuccess(userInfo);
        verify(placeGoTo).goTo(new HomePlace());
        verifyNoMoreInteractions(placeGoTo, clientLoginManager, treePresenter, globalActionsPresenter);
    }

    @Test
    public void testNotLoggedOn() throws Exception {
        dashboardPresenter.init();
        verify(clientLoginManager).getUserInfoAsync(asyncCallbackArgumentCaptor.capture());
        AsyncCallback<UserInfo> callback = asyncCallbackArgumentCaptor.getValue();
        userInfo = new UserInfo("sls", false, false, "", "", new Date(0), null);
        callback.onSuccess(userInfo);
        verify(clientLoginManager).redirectToLogin();
        verifyNoMoreInteractions(placeGoTo, clientLoginManager, treePresenter, globalActionsPresenter);
    }

    @Test
    public void testErrorInClientLoginManager() throws Exception {
        dashboardPresenter.init();
        verify(clientLoginManager).getUserInfoAsync(asyncCallbackArgumentCaptor.capture());
        AsyncCallback<UserInfo> callback = asyncCallbackArgumentCaptor.getValue();
        callback.onFailure(new RuntimeException());
        verify(placeGoTo).goTo(new HomePlace());
        verifyNoMoreInteractions(placeGoTo, clientLoginManager, treePresenter, globalActionsPresenter);
    }

    @Test
    public void testOnStop() throws Exception {
        dashboardPresenter.onStop();
        verify(globalActionsPresenter).stop();
        verifyNoMoreInteractions(globalActionsPresenter, placeGoTo, clientLoginManager, treePresenter);
    }
}
